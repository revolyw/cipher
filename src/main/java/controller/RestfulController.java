package controller;

import model.Cipher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Willow on 16/11/13.
 */
@RestController
public class RestfulController {
    @RequestMapping("/getCipher")
    public Cipher getCipher(@RequestParam(value = "hello word!") String plain) {
        Cipher cipher = new Cipher();
        cipher.setPlain(plain);
        return cipher;
    }
}
