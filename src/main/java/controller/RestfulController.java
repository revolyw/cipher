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
import org.springframework.web.servlet.ModelAndView;
import security.CsrfTokenFactory;
import util.ExcelUtil;
import util.LoggerUtil;
import util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    //返回指定视图
    @RequestMapping("/index")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    //转发的目标
    @RequestMapping("/beForwarded")
    public ModelAndView beForwarded(ModelAndView modelAndView) {
        //接收到转发带来的ModelAndView后添加一些数据，并设置新的视图并返回输出
        modelAndView.getModel().put("isForwarded", true);
        modelAndView.setViewName("index");
        return modelAndView;//实际输出对象
    }

    //请求转发
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

    @RequestMapping("/test404")
    public ModelAndView test404(HttpServletRequest
                                        httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setStatus(404);
        return new ModelAndView("404");
    }

    @RequestMapping("/test500")
    public ModelAndView test500(HttpServletRequest
                                        httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setStatus(500);
        return new ModelAndView("error");
    }

    @RequestMapping("/testAspect")
    public ModelAndView testAspect() throws Exception {
        if (true) {
            int a = 1 / 0;
            throw new Exception();
        }
        return new ModelAndView("500");
    }

    //测试CSRF漏洞的页面
    @RequestMapping("/pageCSRF")
    public ModelAndView pageCSRF(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("CSRFPage");
        CsrfTokenFactory.setToken(request,modelAndView);
        return modelAndView;
    }
    @RequestMapping("/testCSRF")
    public ModelAndView testCSRF(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("CSRF");
        return modelAndView;
    }

    //提供重定向至腾讯邮箱的接口
    @RequestMapping("/redirectToEXMail")
    public void redirectToEXMail(HttpServletResponse response) throws IOException {
        response.sendRedirect("http://exmail.qq.com/login");
    }
}
