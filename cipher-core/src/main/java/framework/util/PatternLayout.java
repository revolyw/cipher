package framework.util;

/**
 * 用于过滤掉业务日志中的堆栈信息
 * Created by Willow on 1/6/17.
 */
public class PatternLayout extends org.apache.log4j.PatternLayout {
    @Override
    public boolean ignoresThrowable() {
        return false;
    }
}
