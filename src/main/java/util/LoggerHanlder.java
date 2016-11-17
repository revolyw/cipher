package util;

import org.apache.log4j.Logger;

/**
 * Created by Willow on 16/11/17.
 */
public class LoggerHanlder {
    private static Logger logger = Logger.getLogger("sys");

    public static void handle(String message, Throwable throwable) {
        logger.error(getCaller("handle") + " : " + message , throwable);
    }

    public static void info(String message) {
        logger.info(getCaller("info") + " : " + message);
    }

    public static void debug(String message) {
        logger.debug(getCaller("debug") + " : " + message);
    }

    public static void error(Throwable throwable) {
        logger.error(getCaller("error"), throwable);
    }

    public static void error(String msg) {
        logger.error(getCaller("error") + " : " + msg);
    }
    public static void println(String message){
        System.out.println(message);
    }
    public static String getCaller(String currentMethod) {
        int i;
        StackTraceElement stack[] = (new Throwable()).getStackTrace();
        for (i = 0; i < stack.length; i++) {
            StackTraceElement ste = stack[i];
            if (ste.getClassName().equals("org.jute.util.LoggerHanlder") && ste.getMethodName().equals(currentMethod) && i < stack.length - 1) {
                String[] className = stack[i + 1].getClassName().split("\\.");
                return className[className.length - 1] + "(" + stack[i + 1].getLineNumber() + ")";
            }
        }
        return "null";
    }

    public static void main(String[] args){
        LoggerHanlder.info("this is a log message!!!");
    }
}
