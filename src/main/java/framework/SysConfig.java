package framework;
import util.LoggerHanlder;

/**
 * 系统初始化配置
 * Created by Willow on 16/11/17.
 */
public class SysConfig {
    public static boolean DEV = false;
    public static String ONLINE = "http://www.willowspace.cn";
    public static String OUTLINE = "http://www.willowspace.net";

    static {
        DEV = "dev".equals(System.getProperty("environment"));
    }

    public void init() {
        LoggerHanlder.info("this is a messsage from sysconfig");
    }
}
