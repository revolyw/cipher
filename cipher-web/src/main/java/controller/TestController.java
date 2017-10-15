package controller;

import framework.util.LoggerHandler;
import framework.web.HTTP;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;

/**
 * 测试接口
 * Created by Willow on 10/15/17.
 */
@RestController
public class TestController {
    @RequestMapping(value = "/test/httpClientApiDownload", method = RequestMethod.GET)
    public void testHttpClientApiDownload(HTTP http) throws IOException {
        int width = 100;
        int height = 100;
        //画布
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //笔
        Graphics2D graphics2D = bufferedImage.createGraphics();
        //绘图过程
        graphics2D.setBackground(Color.WHITE);
        graphics2D.clearRect(0, 0, width, height);
        graphics2D.setPaint(new Color(0, 0, 255));
        graphics2D.fillRect(0, 0, 100, 10);
        graphics2D.setPaint(new Color(253, 2, 0));
        graphics2D.fillRect(0, 10, 100, 10);
        graphics2D.setPaint(Color.red);

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);
            http.getResponse().setHeader("Content-Type", "image/jpeg;charset=UTF-8");
            http.getResponse().setHeader("Content-disposition", "attachment; filename=" + URI.create("test").toASCIIString() + ".jpg");
            http.getResponse().getOutputStream().write(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            LoggerHandler.error(e, "[ write image bytes error]");
        }
    }
}
