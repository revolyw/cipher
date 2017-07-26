package com.jute.framework.util;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;
/**
 * X.509标准证书认证
 * Created by Willow on 2/16/17.
 */
public class X509TrustManagerImpl implements X509TrustManager {
    /* (non-Javadoc)
    * @see javax.net.ssl.X509TrustManager#checkClientTrusted(java.security.cert.X509Certificate[], java.lang.String)
    */
    public void checkClientTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {
        //do nothing 接受任意客户端证书
    }

    /* (non-Javadoc)
     * @see javax.net.ssl.X509TrustManager#checkServerTrusted(java.security.cert.X509Certificate[], java.lang.String)
     */
    public void checkServerTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {
        //do nothing 接受任意服务端证书
    }

    /* (non-Javadoc)
     * @see javax.net.ssl.X509TrustManager#getAcceptedIssuers()
     */
    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }
}
