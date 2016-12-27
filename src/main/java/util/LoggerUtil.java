package util;

import org.apache.log4j.Logger;

/**
 * 通用日志打印工具
 * Created by Willow on 16/11/17.
 */
public class LoggerUtil {
    //为Util提供的异常栈
    private static Logger logger = Logger.getLogger("sys");

    //日志输出 info信息
    public static void info(String message) {
        logger.info(getCaller() + " : " + message);
    }

    //日志输出 debug信息 及 debug异常栈
    public static void debug(String message, Throwable throwable) {
        logger.debug(getCaller() + " : " + message, throwable);
    }

    //日志输出 error信息
    public static void error(String msg) {
        logger.error(getCaller() + " : " + msg);
    }

    //日志输出 error异常栈
    public static void error(Throwable throwable) {
        logger.error(getCaller(), throwable);
    }

    //日志输出 error信息 及 error异常栈
    public static void error(String msg, Throwable throwable) {
        logger.error(getCaller() + " : " + msg, throwable);
    }

    //控制台输出信息
    public static void println(String message) {
        System.out.println(message);
    }

    //找到
    private static String getCaller() {
        StackTraceElement[] stack = (new Throwable()).getStackTrace();
        if (null == stack || stack.length <= 0)
            return "";
        StackTraceElement target = null;
        for (int i = 0; i < stack.length - 1; i++) {
            // 从运行堆栈中找到目标栈帧（调用日志打印的位置）
            if (null != stack[i] && stack[i].getClassName().equals(LoggerUtil.class.getName())) {
                target = stack[i + 2];
                break;
            }
        }
        return (null == target) ? "[ null ]" : target.getClassName() + "." + target.getMethodName() + "(" + target.getLineNumber() + ")";
    }
}
