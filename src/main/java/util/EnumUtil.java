package util;


import type.enumeration.CipherEnum;
import type.EnumInterface;

import java.lang.reflect.Method;

/**
 * 枚举类型工具
 * Created by Willow on 12/3/16.
 */
public class EnumUtil {
    public static EnumInterface valueOf(Class<? extends EnumInterface> e, int value) {
        try {
            //反射获取枚举类用来获取所有实例的方法对象
            Method values = e.getMethod("values");
            EnumInterface[] enumInterfaces = (EnumInterface[]) values.invoke(e);
            for (EnumInterface enumInterface : enumInterfaces) {
                if (value == enumInterface.getValue())
                    return enumInterface;
            }
        } catch (Exception exception) {
            LoggerUtil.error(exception);
        }
        return null;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        EnumInterface enumInterface = valueOf(CipherEnum.class, 1);
        System.out.println(enumInterface.getValue() + " " + enumInterface.getName() + " " + enumInterface.getDescription());
    }
}
