package framework.util;

import org.junit.Test;
import framework.type.EnumInterface;
import framework.type.enumeration.CipherEnum;

import static junit.framework.TestCase.assertEquals;

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