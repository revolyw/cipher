package type.enumeration;

import type.EnumInterface;

/**
 * 加密类型枚举
 * Created by Willow on 12/3/16.
 */
public enum CipherEnum implements EnumInterface {
    //枚举实例
    NULL(0, "异常值"), M5D(1, "M5D散列算法"), RSA(2, "RSA加密算法");

    //成员变量
    private int value;
    private String description;

    //构造方法
    CipherEnum(int value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public String getName() {
        return this.name();
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
