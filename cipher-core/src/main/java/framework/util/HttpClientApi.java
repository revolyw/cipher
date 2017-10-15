package framework.util;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import javax.net.ssl.SSLContext;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * HttpClient接口
 * Created by Willow on 7/25/17.
 */
public interface HttpClientApi {
    // 默认5s超时
    int DEFAULT_TIMEOUT_MILLIS = 5000;

    // 默认debug选项
    boolean DEFAULT_DEBUG = false;

    /**
     * 默认请求参数url编码选项
     * 以下字符将无视该选项，强行进行编码
     * 空格编码为%20
     */
    boolean DEFAULT_PARAMS_URL_ENCODE = true;

    /**
     * 设置debug选项
     *
     * @param debug
     */
    HttpClientApi setDebug(Boolean debug);

    /**
     * 设置参数url编码选项
     *
     * @param paramsUrlEncode
     */
    HttpClientApi setParamsUrlEncode(Boolean paramsUrlEncode);

    /**
     * 设置请求超时时间
     *
     * @param millis 超时时间,单位毫秒
     */
    HttpClientApi setTimeoutMillis(Integer millis);

    /**
     * http或https协议的get请求
     *
     * @param url    协议+域名(主机名)+定位路径
     * @param params get请求的原始参数，请务必不要进行Url编码
     * @return 响应字符串
     */
    <T> String get(String url, Map<String, T> params);

    /**
     * http或https协议的get请求
     * 无参数
     *
     * @param url 协议+域名(主机名)+定位路径
     * @return 响应字符串
     */
    String get(String url);

    /**
     * 方便调用，别无他用
     *
     * @param url
     * @return
     */
    static String doGet(String url) {
        return doGet(url, null);
    }

    /**
     * 方便调用，别无他用
     *
     * @param url
     * @param params
     * @param <T>
     * @return
     */
    static <T> String doGet(String url, Map<String, T> params) {
        return getNativeClient().get(url, params);
    }

    /**
     * http或https协议的post请求
     * 无参数
     *
     * @param url 协议+域名(主机名)+定位路径
     * @return 响应字符串
     */
    String post(String url);

    /**
     * http或https协议的post请求
     *
     * @param url    协议+域名(主机名)+定位路径
     * @param params post请求的原始参数，请务必不要进行Url编码
     * @return 响应字符串
     */
    <T> String post(String url, Map<String, T> params);

    /**
     * 方便调用，别无它用
     *
     * @param url
     * @return
     */
    static String doPost(String url) {
        return doPost(url, null);
    }


    /**
     * 方便调用，别无他用
     *
     * @param url
     * @param params
     * @param <T>
     * @return
     */
    static <T> String doPost(String url, Map<String, T> params) {
        return getNativeClient().post(url, params);
    }

    /**
     * 判断字符串是否是json串
     *
     * @param string
     * @return
     */
    static boolean isValidJsonString(String string) {
        try {
            JSONObject.parse(string);
        } catch (JSONException e) {
            return false;
        }
        return true;
    }

    /**
     * NativeClient
     * 支持https
     *
     * @return
     */
    static HttpClientApi getNativeClient() {
        return new NativeHttpClient();
    }

    /**
     * NativeClient
     * 支持https
     *
     * @return
     */
    static HttpClientApi getNativeClient(boolean debug, boolean paramsUrlEncode, int timeoutMillis) {
        HttpClientApi client = getNativeClient();
        client.setDebug(debug);
        client.setParamsUrlEncode(paramsUrlEncode);
        client.setTimeoutMillis(timeoutMillis <= 0 ? DEFAULT_TIMEOUT_MILLIS : timeoutMillis);
        return client;
    }

    /**
     * 转换参数
     *
     * @param params
     * @return
     */
    static <T> Optional<URI> getUri(String url, Map<String, T> params, boolean paramsUrlEncode) {
        if (!MapUtils.isEmpty(params)) {
            URIBuilder uriBuilder;
            try {
                uriBuilder = new URIBuilder(url);
                for (Map.Entry<String, T> entry : params.entrySet()) {
                    uriBuilder.addParameter(entry.getKey(), Objects.toString(entry.getValue()));
                }
                if (!paramsUrlEncode) {
                    //URL解码
                    try {
                        String decodeString = URLDecoder.decode(uriBuilder.build().toString(), StandardCharsets.UTF_8.name()).replaceAll(" ", "%20");
                        URI uri = new URIBuilder(decodeString).build();
                        return Optional.of(uri);
                    } catch (UnsupportedEncodingException e) {
                        LoggerHandler.error(e, "url decode error cause of {}", e.getMessage());
                    }
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
    static <T> Optional<? extends StringEntity> convertParamsToEntity(Map<String, T> params, Boolean paramsUrlEncode) {
        if (!MapUtils.isEmpty(params)) {
            if (paramsUrlEncode) {
                List<NameValuePair> nameValuePairs = new ArrayList<>();
                for (Map.Entry<String, T> entry : params.entrySet()) {
                    nameValuePairs.add(new BasicNameValuePair(entry.getKey(), Objects.toString(entry.getValue())));
                }
                try {
                    UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairs);
                    return Optional.of(urlEncodedFormEntity);
                } catch (UnsupportedEncodingException e) {
                    LoggerHandler.error(e, "assemble UrlEncodedFormEntity error, the detail is {}", e.getMessage());
                }
            } else {
                List<String> paramList = new LinkedList<>();
                for (Map.Entry<String, T> entry : params.entrySet()) {
                    paramList.add(entry.getKey() + "=" + entry.getValue());
                }
                String paramString = paramList.stream().collect(Collectors.joining("&"));
                StringEntity paramsEntity = new StringEntity(paramString, StandardCharsets.UTF_8.name());
                return Optional.of(paramsEntity);
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
    static <T> Optional<List<NameValuePair>> getNameValuePair(Map<String, T> params) {
        if (null != params) {
            Form form = Form.form();
            for (Map.Entry<String, T> entry : params.entrySet()) {
                form.add(entry.getKey(), Objects.toString(entry.getValue()));
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


    /**
     * 文件下载
     *
     * @param url
     * @param params get请求的原始参数，请务必不要自行Url编码
     * @return 响应字符串
     */
    <T> byte[] download(String url, Map<String, T> params);
}
