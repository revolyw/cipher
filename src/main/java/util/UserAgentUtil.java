package util;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Willow on 16/11/21.
 * UA 解析工具
 */
public class UserAgentUtil {
    /**
     * 获取操作系统信息
     *
     * @param rq
     * @return
     */
    public static String getOs(HttpServletRequest rq) {
        UserAgent ua = UserAgent.parseUserAgentString(rq.getHeader("User-Agent"));
        OperatingSystem os = ua.getOperatingSystem();
        return os.getName();
    }

    /**
     * 获取浏览器名字
     *
     * @param rq
     * @return
     */
    public static String getBrowserName(HttpServletRequest rq) {
        UserAgent ua = UserAgent.parseUserAgentString(rq.getHeader("User-Agent"));
        Browser browser = ua.getBrowser();
        return browser.getName();
    }

    /**
     * 获取浏览器版本
     *
     * @param rq
     * @return
     */
    public static String getBrowserVersion(HttpServletRequest rq) {
        UserAgent ua = UserAgent.parseUserAgentString(rq.getHeader("User-Agent"));
        Version version = ua.getBrowserVersion();
        return version.getVersion();
    }

    /**
     * 获取客户端真实ip（用于设置了反向代理的情况下获取ip）
     *
     * @param rq
     * @return
     */
    public String getRealIP(HttpServletRequest rq) {
        String ip = rq.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = rq.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = rq.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = getRemoteAddr(rq);
        }
        if (ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1")) {
            // 根据网卡取本机配置的IP
            InetAddress inet = null;
            try {
                inet = InetAddress.getLocalHost();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            ip = inet.getHostAddress();
        }

        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ip != null && ip.length() > 15) { // "***.***.***.***".length() = 15
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return ip;
    }

    /**
     * 返回客户端的IP地址
     *
     * @return IP
     */
    public String getRemoteAddr(HttpServletRequest rq) {
        String ip = rq.getHeader("X-Forwarded-For");
        if (ip != null && ip.indexOf(".") > 1) {
            return ip;
        }
        return rq.getRemoteAddr();
    }
}
