package framework.util;

import org.junit.Test;

/**
 * Created by Willow on 10/15/17.
 */
public class HttpClientApiTest {
    @Test
    public void download() throws Exception {
        byte[] bytes = HttpClientApi.getNativeClient().download("http://localhost:9002/test/httpClientApiDownload", null);
        LoggerHandler.info("{}",bytes.length);
    }
}