package framework.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.Optional;

/**
 * 原生HttpClient封装
 * Created by Willow on 7/25/17.
 */
public class NativeHttpClient implements HttpClientApi {
    /**
     * get请求
     *
     * @param url     请求url
     * @param params  参数
     * @param timeoutMillis 超时时间(单位：毫秒)
     * @return
     */
    private String finalGet(String url, Map<String, String> params, Integer timeoutMillis) {
        timeoutMillis = (null == timeoutMillis || timeoutMillis <= 0) ? HttpClientApi.DEFAULT_TIMEOUT_MILLIS : timeoutMillis;
        CloseableHttpClient httpclient = HttpClientApi.create(url);
        HttpGet httpGet = new HttpGet(url);
        //设置参数
        Optional<URI> uriOptional = HttpClientApi.getUri(url, params);
        uriOptional.ifPresent(httpGet::setURI);
        //设置超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setContentCompressionEnabled(true)
                .setConnectionRequestTimeout(timeoutMillis)
                .setSocketTimeout(timeoutMillis)
                .setConnectTimeout(timeoutMillis)
                .build();//设置请求和传输超时时间
        httpGet.setConfig(requestConfig);
        //执行请求并获取响应
        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        } catch (IOException e) {
            LoggerHandler.error(e, e.getMessage());
        }
        return "";
    }

    @Override
    public String get(String url, Map<String, String> params, Integer timeoutMillis) {
        return finalGet(url, params, timeoutMillis);
    }

    @Override
    public String get(String url, Map<String, String> params) {
        return finalGet(url, params, null);
    }

    @Override
    public String get(String url) {
        return finalGet(url, null, null);
    }

    @Override
    public String post(String url, Map<String, String> params) {
        CloseableHttpClient httpclient = HttpClientApi.create(url);
        HttpPost httpPost = new HttpPost(url);
        //设置参数
        Optional<UrlEncodedFormEntity> urlEncodedFormEntityOptional = HttpClientApi.getUrlEncodedFormEntity(params);
        urlEncodedFormEntityOptional.ifPresent(httpPost::setEntity);
        //设置超时
        int timeout = 2000;
        RequestConfig requestConfig = RequestConfig.custom()
                .setContentCompressionEnabled(true)
                .setConnectionRequestTimeout(timeout)
                .setSocketTimeout(timeout)
                .setConnectTimeout(timeout)
                .build();//设置请求和传输超时时间
        httpPost.setConfig(requestConfig);
        //执行请求并获取响应
        try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        } catch (IOException e) {
            LoggerHandler.error(e);
            return null;
        }
    }

    @Override
    public String post(String url) {
        return post(url, null);
    }
}
