package framework.util;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 字符串处理工具
 * Created by Willow on 16/11/17.
 */
public class StringUtil {
    /**
     * 字符串判空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (null == str)
            return true;
        if (0 == str.length())
            return true;
        return false;
    }

    /**
     * md5散列值
     *
     * @param str
     * @return
     */
    public static String md5(String str) {
        //确定计算方法
        MessageDigest md5 = null;
        try {
            //获取MD5消息摘要工具
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            LoggerUtil.error(e.getStackTrace().toString());
        }
        //base64编码器
        Base64 base64 = new Base64();
        //加密后的字符串
        String digestString = null;
        try {
            byte[] originBytes = str.getBytes("utf-8");//原始字节序列
            byte[] digestBytes = md5.digest(originBytes);//据原始字节序列生成消息摘要字节序列
            byte[] base64DigestBytes = base64.encode(digestBytes);//采用base64编码消息摘要字节序列
            digestString = new String(base64DigestBytes);//生成消息摘要字符串
        } catch (UnsupportedEncodingException e) {
            LoggerUtil.error("md5 encryption failed! \n " + e.getStackTrace());
        }
        return digestString;
    }

    public static void main(String[] args) {
        String str = "yw";
        LoggerUtil.println(md5(str));
    }

    public static boolean isNumberic(String string) {
        if (isEmpty(string))
            return false;
        for (int i = 0; i < string.length(); i++) {
            //ascii 0 = 48, 9 = 57
            if (string.charAt(i) < 48 || string.charAt(i) > 57)
                return false;
        }
        return true;
    }
}
