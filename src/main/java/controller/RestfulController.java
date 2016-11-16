package controller;

import dto.AjaxResult;
import model.Cipher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import util.ExcelUtil;

import java.io.IOException;
import java.util.List;

/**
 * Created by Willow on 16/11/13.
 */
@RestController
public class RestfulController {
    @RequestMapping(value = "/getCipher", method = RequestMethod.GET)
    public Cipher getCipher(@RequestParam(value = "plain", defaultValue = "i'm a plain") String plain) {
        Cipher cipher = new Cipher();
        cipher.setPlain(plain);
        return cipher;
    }

    @RequestMapping("/index")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    /**
     * 测试解析Excel
     * @return
     */
    @RequestMapping("/resolveExcel")
    public AjaxResult doResolveExcel() {
        AjaxResult result = new AjaxResult();
        String path = "/Users/Willow/Documents/test.xls";
        try {
             List<List<String>> contentList = ExcelUtil.readExcelFile(path, 2);
            result.setData(contentList);
        } catch (IOException e) {
            result.setMsg("解析失败");
            result.setSuccess(false);
        }
        return result;
    }
}
