package util;

import framework.SysConfig;
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

    //日志输出 info信息及产生info信息的类
    public static void info(String message, Class<?> cls) {
        logger.info(getCaller() + " : " + message + " from [ " + cls.getName() + " ]");
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

    //默认输出 异常栈顶（异常位置）
    private static String getCaller() {
        StackTraceElement stack[] = (new Throwable()).getStackTrace();
        StackTraceElement stackTop = stack[stack.length - 1]; //栈顶，异常产生的地方
        return stackTop.getClassName() + "." + stackTop.getMethodName() + "(" + stackTop.getLineNumber() + ")";
    }
}
