package framework.util;

import framework.SysConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class LoggerHandler {
    //    private static Logger logger = LoggerFactory.getLogger(LoggerHandler.class);
    private static Logger logger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
    private static Logger stackLogger = LoggerFactory.getLogger("stack");//堆栈logger

    //自定义相对栈帧位置枚举
    public enum POP {
        被调孙子栈帧(-2), 被调孩子栈帧(-1), 父级调用栈帧(1), 祖父级调用栈帧(2), 曾祖父级调用栈帧(3);
        private int value;

        POP(int pop) {
            value = pop;
        }

        public int getValue() {
            return value;
        }
    }


    public static void handle(String errorCode, String message, Throwable e) {
        logger.error(getCaller("handle") + message, e);
    }

    public static void handle(String message, Throwable e) {
        logger.error(getCaller("handle") + message, e);
    }

    public static String getCaller(String currentMethod) {
        int i;
        StackTraceElement stack[] = (new Throwable()).getStackTrace();
        for (i = 0; i < stack.length; i++) {
            StackTraceElement ste = stack[i];
            if (ste.getClassName().equals("org.jute.framework.util.LoggerHandler") && ste.getMethodName().equals(currentMethod)
                    && i < stack.length - 1) {
                String[] className = stack[i + 1].getClassName().split("\\.");
                return className[className.length - 1] + "." + stack[i + 1].getMethodName() + "(" + stack[i + 1].getLineNumber() + ") - ";
            }
        }
        return "[Not found log scene，Track by your self]";
    }

    /**
     * 案发现场信息
     *
     * @param pop 相对于打印日志的位置的pop层栈帧，正为向上追溯调用栈帧，负为向下追寻被调栈帧
     * @return
     */
    private static String getScene(int... pop) {
        int popLevel = (null == pop || pop.length <= 0) ? 0 : pop[0];
        StackTraceElement[] stack = (new Throwable()).getStackTrace();
        if (null == stack || stack.length <= 2) //堆栈至少3层
            return "[Not found log scene，Track by your self] - ";
        StackTraceElement target = stack[2];
        for (int i = 0; i < stack.length - 3; i++) {
            //如果目标栈帧已经超出堆栈，则返回现场信息
            if ((i + 2 + popLevel) > (stack.length - 1) || (i + 2 + popLevel) < 0) {
                target = stack[i + 2];
                break;
            }
            // 从运行堆栈中找到目标栈帧（调用日志打印的位置）
            if (null != stack[i] && stack[i].getClassName().equals(LoggerHandler.class.getName())) {
                target = stack[i + 2 + popLevel];
                break;
            }
        }
        return (null == target) ? "[Not found log scene，Track by your self] - " : target.getClassName() + "." + target.getMethodName() + "(" + target.getLineNumber() + ") - ";
    }

    /**
     * 堆栈及信息打印（私有方法）
     *
     * @param e       堆栈
     * @param message 含有占位符的字符串
     * @param objects 填充占位符的一系列对象
     */
    private static void trace(Throwable e, String message, Object... objects) {
        stackLogger.trace(getScene() + message, objects, e);
    }

    /**
     * error日志
     *
     * @param message 带占位符的字符串
     * @param objects 占位符替换值
     */
    public static void error(String message, Object... objects) {
        logger.error(getScene() + message, objects);
    }

    /**
     * 带定位栈帧位置参数的error日志
     *
     * @param pop     相对于打印日志的位置的pop层栈帧，正为向上追溯调用栈帧，负为向下追寻被调栈帧
     * @param message 带占位符的字符串
     * @param objects 占位符替换值
     */
    public static void error(int pop, String message, Object... objects) {
        logger.error(getScene(pop) + message, objects);
    }

    /**
     * 仅含有堆栈信息的error日志
     *
     * @param e
     */
    public static void error(Throwable e) {
        trace(e, null);
    }

    /**
     * 带字符串及堆栈信息的error日志
     *
     * @param e       堆栈
     * @param message 带占位符的字符串
     * @param objects 占位符替换值
     */
    public static void error(Throwable e, String message, Object... objects) {
        logger.error(getScene() + message, objects);
        trace(e, message, objects);
    }

    /**
     * info日志
     *
     * @param message 带占位符的字符串
     * @param objects 占位符替换值
     */
    public static void info(String message, Object... objects) {
        logger.info(getScene() + message, objects);
    }

    //线下及本地debug日志
    public static void devDebug(String message, Object... objects) {
        if (SysConfig.DEV)
            debug(getScene() + message, objects);
    }

    //线下及本地debug日志
    public static void devDebug(Throwable e, String message, Object... objects) {
        if (SysConfig.DEV)
            debug(e, getScene() + message, objects);
    }

    /**
     * debug日志
     *
     * @param message 带占位符的字符串
     * @param objects 占位符替换值
     */
    public static void debug(String message, Object... objects) {
        logger.debug(getScene() + message, objects);
    }

    /**
     * 带堆栈信息的debug日志
     *
     * @param e       堆栈
     * @param message 带占位符的字符串
     * @param objects 占位符替换值
     */
    public static void debug(Throwable e, String message, Object... objects) {
        debug(getScene() + message, objects, e);
        trace(e, message, objects);
    }

    /**
     * warm日志
     *
     * @param message 带占位符的字符串
     * @param objects 占位符替换值
     */
    public static void warn(String message, Object... objects) {
        logger.warn(getScene() + message, objects);
    }

    public static void main(String[] args) {
        trace(new Exception(), "trace");
        debug(new Exception(), "debug");
        info("info");
        warn("warn");
        error(new Exception(), "error");
    }
}
