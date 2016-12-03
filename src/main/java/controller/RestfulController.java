package controller;

import dao.UserDao;
import dto.AjaxResult;
import model.Cipher;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import util.ExcelUtil;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 不要被名字迷惑，这并不是一个彻底的restful的接口控制器
 * Created by Willow on 16/11/13.
 */
//写这段代码的时候，只有上帝和我知道它是干嘛的
//现在，只有上帝知道
@RestController
@Configuration
public class RestfulController {
    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/getCipher", method = RequestMethod.GET)
    public Cipher getCipher(@RequestParam(value = "plain", defaultValue = "i'm a plain") String plain) {
        Cipher cipher = new Cipher();
        cipher.setPlain(plain);
        return cipher;
    }

    /**
     * 返回指定视图
     *
     * @return
     */
    @RequestMapping("/index")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    /**
     * 转发的目标
     *
     * @return
     */
    @RequestMapping("/beForwarded")
    public ModelAndView beForwarded(ModelAndView modelAndView) {
        //接收到转发带来的ModelAndView后添加一些数据，并设置新的视图并返回输出
        modelAndView.getModel().put("isForwarded", true);
        modelAndView.setViewName("index");
        return modelAndView;//实际输出对象
    }

    /**
     * 请求转发
     *
     * @return
     */
    @RequestMapping("/forward")
    public ModelAndView forward() {
        //做一些处理
        User user = userDao.findByField("userName", "willow");
        Map model = new HashMap();
        model.put("user", user);
        //在ModelAndView中添加转发的目标
        ModelAndView modelAndView = new ModelAndView("forward:/beForwarded", model);
        return modelAndView;//转发
    }

    /**
     * 测试解析Excel
     *
     * @return
     */
    @RequestMapping("/resolveExcel")
    public AjaxResult doResolveExcel() {
        AjaxResult result = AjaxResult.newInstance();
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

    /**
     * 测试数据源配置
     *
     * @return
     */
    @RequestMapping("/testDataSource")
    public AjaxResult testDataSource() {
        AjaxResult result = AjaxResult.newInstance();
//        User user = userDao.findOne(1);
        User user = userDao.findByField("userName", "willow");
        result.setData(user);
        return result;
    }
}
