package controller;

import dao.UserDao;
import dto.AjaxResult;
import model.Cipher;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import framework.util.ExcelUtil;
import framework.util.LoggerUtil;
import framework.util.StringUtil;

import java.io.IOException;
import java.util.List;

/**
 * RestfulController
 * 将按mvc:message-converters注解中配置的消息转换器解析视图
 * 或自己生成ModelAndView实例
 * <p>
 * Created by Willow on 16/11/13.
 * 写这段代码的时候，只有上帝和我知道它是干嘛的
 * 现在，只有上帝知道
 */

@RestController
@Configuration
public class RestfulController {
    @Autowired
    private UserDao userDao;

    //测试实时修改jvm参数
    @RequestMapping(value = "/putJvmArgument", method = RequestMethod.GET)
    public String putJvmArgument(@RequestParam(value = "argument", defaultValue = "") String argument, @RequestParam(value = "value") String value) {
        if (StringUtil.isEmpty(argument))
            return "argument is null , change failed!";

        String valueOfOldJvm = System.getProperty(argument);
        if (StringUtil.isEmpty(valueOfOldJvm)) {
            System.setProperty(argument, value);
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    String tmpValue;
                    do {
                        tmpValue = System.getProperty(argument);
                        //实时打印jvm参数值
                        LoggerUtil.println(tmpValue);
                        //打印一次 挂起1秒
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            LoggerUtil.error("sleep error", e);
                        }
                    } while (!StringUtil.isEmpty(tmpValue));
                }
            }.start();
            return "argument is unset . this is first time to run,set the " + argument + " argument";
        }
        System.setProperty(argument, value);
        return "change jvm argument " + argument + " value equals " + value + "  success";
    }

    //置顶Method，并绑定接受参数,并以json返回pojo,json格式转换详见配置
    @RequestMapping(value = "/getCipher", method = RequestMethod.GET)
    public Cipher getCipher(@RequestParam(value = "plain", defaultValue = "i'm a plain") String plain) {
        Cipher cipher = new Cipher();
        cipher.setPlain(plain);
        return cipher;
    }

    //测试解析Excel
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

    //测试数据源配置
    @RequestMapping("/testDataSource")
    public AjaxResult testDataSource() {
        AjaxResult result = AjaxResult.newInstance();
//        User user = userDao.findOne(1);
        User user = userDao.findByField("userName", "willow");
        result.setData(user);
        return result;
    }

    //日志测试接口
    @RequestMapping("/getLog")
    public AjaxResult testLog() {
        AjaxResult result = AjaxResult.newInstance();
        LoggerUtil.info("this is a log");
        return result;
    }
}
