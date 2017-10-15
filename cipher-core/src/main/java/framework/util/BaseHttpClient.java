package framework.util;

import java.util.Objects;

/**
 * HttpClient基础功能
 * Created by Willow on 7/28/17.
 */
public abstract class BaseHttpClient implements HttpClientApi {
    protected boolean debug = HttpClientApi.DEFAULT_DEBUG;
    protected boolean paramsUrlEncode = HttpClientApi.DEFAULT_PARAMS_URL_ENCODE;
    protected int timeoutMillis = HttpClientApi.DEFAULT_TIMEOUT_MILLIS;

    @Override
    public HttpClientApi setDebug(Boolean debug) {
        this.debug = Objects.isNull(debug) ? HttpClientApi.DEFAULT_DEBUG : debug;
        return this;
    }

    @Override
    public HttpClientApi setParamsUrlEncode(Boolean paramsUrlEncode) {
        this.paramsUrlEncode = Objects.isNull(paramsUrlEncode) ? HttpClientApi.DEFAULT_PARAMS_URL_ENCODE : paramsUrlEncode;
        return this;
    }

    @Override
    public HttpClientApi setTimeoutMillis(Integer timeoutMillis) {
        this.timeoutMillis = Objects.isNull(timeoutMillis) ? HttpClientApi.DEFAULT_TIMEOUT_MILLIS : timeoutMillis;
        return this;
    }
}
