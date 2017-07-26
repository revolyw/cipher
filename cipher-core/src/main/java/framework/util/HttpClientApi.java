package framework.util;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import javax.net.ssl.SSLContext;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * HttpClient接口
 * Created by Willow on 7/25/17.
 */
public interface HttpClientApi {
    // 默认5s超时
    int DEFAULT_TIMEOUT_MILLIS = 5000;

    /**
     * http或https协议的get请求
     *
     * @param url           协议+域名(主机名)+定位路径
     * @param params        get请求的原始参数，请务必不要进行Url编码
     * @param timeoutMillis 超时时间
     * @return 响应字符串
     */
    String get(String url, Map<String, String> params, Integer timeoutMillis);

    /**
     * http或https协议的get请求
     *
     * @param url    协议+域名(主机名)+定位路径
     * @param params get请求的原始参数，请务必不要进行Url编码
     * @return 响应字符串
     */
    String get(String url, Map<String, String> params);

    /**
     * http或https协议的get请求
     * 无参数
     *
     * @param url 协议+域名(主机名)+定位路径
     * @return 响应字符串
     */
    String get(String url);

    /**
     * http或https协议的post请求
     *
     * @param url    协议+域名(主机名)+定位路径
     * @param params post请求的原始参数，请务必不要进行Url编码
     * @return 响应字符串
     */
    String post(String url, Map<String, String> params);

    /**
     * http或https协议的post请求
     * 无参数
     *
     * @param url 协议+域名(主机名)+定位路径
     * @return 响应字符串
     */
    String post(String url);

    /**
     * 不支持https
     *
     * @return
     */
    static HttpClientApi getFluentClient() {
        return new FluentHttpClient();
    }

    /**
     * 支持https
     *
     * @return
     */
    static HttpClientApi getNativeClient() {
        return new NativeHttpClient();
    }

    /**
     * 转换参数
     *
     * @param params
     * @return
     */
    static Optional<URI> getUri(String url, Map<String, String> params) {
        if (null != params) {
            URIBuilder uriBuilder;
            try {
                uriBuilder = new URIBuilder(url);
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    uriBuilder.addParameter(entry.getKey(), entry.getValue());
                }
                //空格被编码成+，下面统一将+转换成空格的编码%20
                URI uri = new URIBuilder(uriBuilder.build().toString().replaceAll("[+]", "%20")).build();
                return Optional.of(uri);
            } catch (URISyntaxException e) {
                LoggerHandler.error(e, "assemble URI error,detail is {}", e.getMessage());
            }
        }
        return Optional.empty();
    }

    /**
     * 转换参数
     *
     * @param params
     * @return
     */
    static Optional<UrlEncodedFormEntity> getUrlEncodedFormEntity(Map<String, String> params) {
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        if (null != params) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            try {
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairs);
                return Optional.of(urlEncodedFormEntity);
            } catch (UnsupportedEncodingException e) {
                LoggerHandler.error(e, "assemble UrlEncodedFormEntity error, the detail is {}", e.getMessage());
            }
        }
        return Optional.empty();
    }

    /**
     * 转换参数
     *
     * @param params
     * @return
     */
    static Optional<List<NameValuePair>> getNameValuePair(Map<String, String> params) {
        if (null != params) {
            Form form = Form.form();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                form.add(entry.getKey(), entry.getValue());
            }
            return Optional.of(form.build());
        }
        return Optional.empty();
    }

    /**
     * 获取支持https访问的httpClient
     *
     * @param url
     * @return
     */
    static CloseableHttpClient create(String url) {
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new X509TrustManagerImpl[]{new X509TrustManagerImpl()}, new java.security.SecureRandom());
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslContext,
                    new String[]{"TLSv1"},
                    null,
                    SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            LoggerHandler.error(e, "assemble CloseableHttpClient with SSLContext error, detail is {}", e.getMessage());
        }
        return HttpClients.createDefault();
    }
}
