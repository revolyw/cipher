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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Willow on 16/11/13.
 */
// 亲爱的维护者：
// 如果你尝试了对这段程序进行‘优化’，
// 并认识到这种企图是大错特错，请增加
// 下面这个计数器的个数，用来对后来人进行警告：
// 浪费在这里的总时间 = 39h

/**
 * 致终于来到这里的勇敢的人：
 * 你是被上帝选中的人，英勇的、不辞劳苦的、不眠不修的来修改
 * 我们这最棘手的代码的编程骑士。你，我们的救世主，人中之龙，
 * 我要对你说：永远不要放弃，永远不要对自己失望，永远不要逃走，辜负了自己。
 * 永远不要哭啼，永远不要说再见。永远不要说谎来伤害自己。
 */
// Exception up = new Exception("Something is really wrong."); throw up; 
// 一些修改1 - 2002/6/7 增加临时的跟踪登录界面
// 一些修改2 - 2007/5/22 我临时的犯傻
// #define TRUE FALSE //逗一逗调试程序的傻瓜们
// if (/*you*/ $_GET['action']) { //celebrate(恭喜)
// 如果这段代码好用，那它是Paul DiLascia写的。
//如果不好用，我不知道是谁写的。
//写这段代码的时候，只有上帝和我知道它是干嘛的
//现在，只有上帝知道
// 晕了，以后再修改
// 神奇。勿动。
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
