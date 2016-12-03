package util;

import model.Cipher;
import org.junit.Test;
import type.EnumInterface;
import type.enumeration.CipherEnum;

import static org.junit.Assert.*;

/**
 * Created by Willow on 12/4/16.
 */
public class EnumUtilTest {
    @Test
    public void valueOf() throws Exception {
        EnumInterface enumInterface = EnumUtil.valueOf(CipherEnum.class, 1);
        assertEquals(1, enumInterface.getValue());
    }
}