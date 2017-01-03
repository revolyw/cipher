package security;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * csrf token producer
 * Created by Willow on 12/25/16.
 */
public class CsrfTokenFactory {
    //结合模板引擎生产csrf token
    public static void setToken(HttpServletRequest request, ModelMap context) {
        //尝试拿csrf filter中设置的token
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        if (null != csrfToken) {
            context.put("_csrf", csrfToken);
            context.put("_csrf_header", "X-CSRF-TOKEN");
        }
    }
}
