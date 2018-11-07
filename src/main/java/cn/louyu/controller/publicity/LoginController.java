package cn.louyu.controller.publicity;

import cn.louyu.models.ResultMsg;
import cn.louyu.service.security.utils.ResponseTypeUtils;
import com.alibaba.fastjson.JSON;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

@Controller
public class LoginController {
    @RequestMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(!ResponseTypeUtils.isRespondJson(request)) {
            return "/login";
        }
        Principal principal = request.getUserPrincipal();
        if(principal ==null||principal.getName()==null) {
            ResultMsg msg = new ResultMsg(false, "身份认证失败,请登录后重试");
            ResponseTypeUtils.respondJsonData(response,JSON.toJSONString(msg));
        }
        return null;
    }
}
