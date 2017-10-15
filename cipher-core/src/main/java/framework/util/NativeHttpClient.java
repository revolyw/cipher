package framework.util;

import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.io.DefaultHttpResponseParser;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * 原生HttpClient封装
 * Created by Willow on 7/25/17.
 */
public class NativeHttpClient extends BaseHttpClient {
    /**
     * package-private
     */
    NativeHttpClient() {
    }

    /**
     * 获取请求配置
     *
     * @return
     */
    private RequestConfig getRequestConfig() {
        return RequestConfig.custom()
                .setContentCompressionEnabled(true)
                .setConnectionRequestTimeout(super.timeoutMillis)
                .setSocketTimeout(super.timeoutMillis)
                .setConnectTimeout(super.timeoutMillis)
                .build();
    }

    /**
     * debug模式下打印请求参数
     * <p>
     * Optional was designed to provide a limited mechanism for library method return types where there needed to be a clear way to represent "no result".
     * Using a field with type java.util.Optional is also problematic if the class needs to be Serializable, which java.util.Optional is not.
     * 这里破例使用Optional作为参数，该方法只是判断Optional中是否有内容，并在有时调用其内容的toString方法。
     *
     * @param optional
     */
    private <T> void logParamsIfDebug(Optional<T> optional) {
        if (super.debug) {
            optional.ifPresent(params -> LoggerHandler.info("{} final request:{}", this.getClass().getSimpleName(), params.toString()));
        }
    }

    /**
     * 执行http请求
     *
     * @param url
     * @param httpRequest
     * @return
     */
    private CloseableHttpResponse executeHttpRequest(String url, HttpRequestBase httpRequest) throws IOException {
        if (Objects.isNull(httpRequest)) {
            return null;
        }
        CloseableHttpClient httpclient = HttpClientApi.create(url);
        //执行请求并获取响应
        CloseableHttpResponse response = httpclient.execute(httpRequest);
        return response;
    }

    /**
     * get请求
     *
     * @param url    请求url
     * @param params 参数
     * @return
     */
    private <T> String finalGet(String url, Map<String, T> params) {
        if (StringUtil.isEmpty(url)) {
            return "";
        }
        //构建httpGet
        HttpGet httpGet = new HttpGet(url);
        Optional<URI> uriOptional = HttpClientApi.getUri(url, params, super.paramsUrlEncode);
        uriOptional.ifPresent(httpGet::setURI);
        logParamsIfDebug(uriOptional);
        RequestConfig requestConfig = getRequestConfig();
        httpGet.setConfig(requestConfig);
        try (CloseableHttpResponse response = executeHttpRequest(url, httpGet)) {
            return parseResponseAsString(response);
        } catch (IOException e) {
            LoggerHandler.error(e, "[ HttpClientApi finalGet execute request error ]");
        }
        return "";
    }

    /**
     * post请求
     *
     * @param url    请求url
     * @param params 参数
     * @return
     */
    private <T> String finalPost(String url, Map<String, T> params) {
        if (StringUtil.isEmpty(url)) {
            return "";
        }
        HttpPost httpPost = new HttpPost(url);
        Optional<? extends StringEntity> entityOptional = HttpClientApi.convertParamsToEntity(params, super.paramsUrlEncode);
        entityOptional.ifPresent(httpPost::setEntity);
        logParamsIfDebug(entityOptional);
        RequestConfig requestConfig = getRequestConfig();
        httpPost.setConfig(requestConfig);
        try (CloseableHttpResponse response = executeHttpRequest(url, httpPost)) {
            return parseResponseAsString(response);
        } catch (IOException e) {
            LoggerHandler.error(e, "[ HttpClientApi finalPost execute request error ]");
        }
        return "";
    }

    @Override
    public <T> byte[] download(String url, Map<String, T> params) {
        if (StringUtil.isEmpty(url)) {
            return new byte[0];
        }
        //构建httpGet
        HttpGet httpGet = new HttpGet(url);
        Optional<URI> uriOptional = HttpClientApi.getUri(url, params, super.paramsUrlEncode);
        uriOptional.ifPresent(httpGet::setURI);
        logParamsIfDebug(uriOptional);
        RequestConfig requestConfig = getRequestConfig();
        httpGet.setConfig(requestConfig);
        try (CloseableHttpResponse response = executeHttpRequest(url, httpGet)) {
            return parseResponseAsBytes(response);
        } catch (IOException e) {
            LoggerHandler.error(e, "[ HttpClientApi finalPost execute request error ]");
        }
        return new byte[0];
    }

    /**
     * 解析响应为字符串
     *
     * @param response
     * @return
     */
    private String parseResponseAsString(CloseableHttpResponse response) {
        if (Objects.isNull(response) || Objects.isNull(response.getStatusLine())) {
            return "";
        }
        int responseCode = response.getStatusLine().getStatusCode();
        if (responseCode == HttpStatus.SC_NOT_FOUND || responseCode == HttpStatus.SC_INTERNAL_SERVER_ERROR) {
            return "";
        }
        HttpEntity httpEntity = response.getEntity();
        if (Objects.isNull(httpEntity)) {
            return "";
        }
        try {
            return EntityUtils.toString(httpEntity);
        } catch (IOException | ParseException e) {
            LoggerHandler.error(e, "[ HttpClientApi parseResponseAsString error ]");
        }
        return "";
    }

    /**
     * 解析响应为字节
     *
     * @param response
     * @return
     */
    private byte[] parseResponseAsBytes(CloseableHttpResponse response) {
        if (Objects.isNull(response) || Objects.isNull(response.getStatusLine())) {
            return new byte[0];
        }
        int responseCode = response.getStatusLine().getStatusCode();
        if (responseCode == HttpStatus.SC_NOT_FOUND || responseCode == HttpStatus.SC_INTERNAL_SERVER_ERROR) {
            return new byte[0];
        }
        HttpEntity httpEntity = response.getEntity();
        try {
            final ContentType contentType = ContentType.getOrDefault(httpEntity);
            Charset charset = contentType.getCharset();
            byte[] bytes = EntityUtils.toByteArray(httpEntity);
            return bytes;
        } catch (IOException | UnsupportedOperationException e) {
            LoggerHandler.error(e, "[ HttpClientApi httpEntity2Bytes error ]");
        }
        return new byte[0];
    }

    @Override
    public <T> String get(String url, Map<String, T> params) {
        return finalGet(url, params);
    }

    @Override
    public String get(String url) {
        return finalGet(url, null);
    }

    @Override
    public <T> String post(String url, Map<String, T> params) {
        return finalPost(url, params);
    }

    @Override
    public String post(String url) {
        return finalPost(url, null);
    }
}
