package framework.util;

import org.apache.http.NameValuePair;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Fluent HttpClient（面门模式）
 * Created by Willow on 7/25/17.
 */
public class FluentHttpClient implements HttpClientApi {

    /**
     * get请求
     *
     * @param url           请求url
     * @param params        参数
     * @param timeoutMillis 超时时间(单位：毫秒)
     * @return
     */
    private String finalGet(String url, Map<String, String> params, Integer timeoutMillis) {
        timeoutMillis = (null == timeoutMillis || timeoutMillis <= 0) ? HttpClientApi.DEFAULT_TIMEOUT_MILLIS : timeoutMillis;
        try {
            //设置超时时间
            Request request = Request.Get(url).socketTimeout(timeoutMillis).connectTimeout(timeoutMillis);
            //设置参数
            Optional<URI> uriOptional = HttpClientApi.getUri(url, params);
            uriOptional.ifPresent(Request::Put);
            //执行响应
            Content content = request.execute().returnContent();
            //获取响应
            return content.asString();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        return null;
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
        try {
            //设置超时时间
            int timeout = 2000;
            Request request = Request.Post(url).socketTimeout(timeout).connectTimeout(timeout);
            //设置参数
            Optional<List<NameValuePair>> nameValuePairListOptional = HttpClientApi.getNameValuePair(params);
            nameValuePairListOptional.ifPresent(request::bodyForm);
            //执行请求
            Content content = request.execute().returnContent();
            //获取响应
            return content.asString();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        return null;
    }

    @Override
    public String post(String url) {
        return post(url, null);
    }
}
