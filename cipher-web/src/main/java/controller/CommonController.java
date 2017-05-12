package controller;

import dao.UserDao;
import framework.web.HTTP;
import framework.security.HttpSessionCsrfTokenRepository;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 使用Controller注解，将按视图解析器顺序解析视图
 * Created by Willow on 1/3/17.
 */
@Controller
public class CommonController {
    @Autowired
    private UserDao userDao;

    //返回指定视图
    @RequestMapping("/index")
    public String index() {
        return "index";
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

    @RequestMapping("/test404")
    public String test404(HTTP http) throws IOException {
        http.getResponse().setStatus(404);
        return "404";
    }

    @RequestMapping("/test500")
    public String test500(HTTP http) throws IOException {
        http.getResponse().setStatus(500);
        return "500";
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
    @RequestMapping("/test/csrf/page")
    public String pageCSRF(HTTP http, ModelMap context) {
        HttpSessionCsrfTokenRepository.setToken(http.getRequest(), context);
        return "csrf_page";
    }

    @RequestMapping("/test/csrf/delete_some_data")
    public String testCSRF() {
        return "csrf";
    }

    //提供重定向至腾讯邮箱的接口
    @RequestMapping("/redirectToEXMail")
    public void redirectToEXMail(HTTP http) throws IOException {
        http.getResponse().sendRedirect("http://exmail.qq.com/login");
    }
}
