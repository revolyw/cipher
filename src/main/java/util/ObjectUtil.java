package util;

import org.apache.commons.beanutils.PropertyUtils;


/**
 * 一般对象 通用工具
 * Created by Willow on 12/4/16.
 */
public class ObjectUtil {
    //泛型深拷贝对象
    public static <T extends Object> T copy(T obj) {
        Class<?> cls = obj.getClass();
        try {
            Object newObj = cls.newInstance();
            PropertyUtils.copyProperties(newObj, obj);
            return (T) newObj;
        } catch (Exception e) {
            LoggerUtil.error("copy vo error!", e);
        }
        return null;
    }
}
