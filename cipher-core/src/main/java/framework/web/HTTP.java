package framework.web;

import framework.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 封装HttpServletRequest、HttpServletResponse
 * Created by Willow on 1/2/17.
 */
public class HTTP {
    private HttpServletRequest req;
    private HttpServletResponse rep;

    public HTTP(HttpServletRequest request, HttpServletResponse response) {
        this.req = request;
        this.rep = response;
    }

    public HttpServletRequest getRequest() {
        return req;
    }

    public void setRequest(HttpServletRequest request) {
        this.req = request;
    }

    public HttpServletResponse getResponse() {
        return rep;
    }

    public void setResponse(HttpServletResponse response) {
        this.rep = response;
    }

    public HttpSession getSession() {
        return this.req.getSession();
    }


    public String getString(String key, String defaultValue) {
        String value = req.getParameter(key);
        if (StringUtil.isEmpty(value))
            return defaultValue;
        return value;
    }

    public int getInt(String key, int defaultValue) {
        String value = req.getParameter(key);
        if (StringUtil.isEmpty(value))
            return defaultValue;
        if (!StringUtil.isNumberic(value))
            return defaultValue;
        return Integer.valueOf(value);
    }
}
