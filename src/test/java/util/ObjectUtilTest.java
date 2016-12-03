package util;

import model.Cipher;
import model.User;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * 一般对象工具 测试用例
 * Created by Willow on 12/4/16.
 */
public class ObjectUtilTest {
    @Test
    public void copy() throws Exception {
        User pojo = new User();
        pojo.setEmail("revolyw@yeah.net");
        pojo.setUserName("yangw");
        pojo.setStatus(1);
        pojo.setCreateTime(new Date());

        User vo = ObjectUtil.copy(pojo);
        assertEquals(pojo.getEmail(), vo.getEmail());
        assertEquals(pojo.getUserName(), vo.getUserName());
        assertEquals(pojo.getStatus(), vo.getStatus());
        assertEquals(pojo.getCreateTime(), vo.getCreateTime());
        LoggerUtil.println(pojo.toString());
        LoggerUtil.println(vo.toString());

        Cipher cipher = new Cipher();
        cipher.setCipher("cipher");
        cipher.setId(11111);
        Cipher newCipher = ObjectUtil.copy(cipher);
        assertEquals(cipher.getCipher(), newCipher.getCipher());
        assertEquals(cipher.getId(), newCipher.getId());
        LoggerUtil.println(cipher.toString());
        LoggerUtil.println(newCipher.toString());
    }

}