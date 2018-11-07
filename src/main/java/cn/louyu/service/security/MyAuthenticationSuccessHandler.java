package cn.louyu.service.security;

import cn.louyu.models.ResultMsg;
import cn.louyu.service.security.utils.ResponseTypeUtils;
import cn.louyu.utils.TextUtils;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *登陆成功的处理器
 * */
@Component("myAuthenctiationSuccessHandler")
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        if(!ResponseTypeUtils.isRespondJson(request)) {
            // 父类的方法 就是 跳转
            super.onAuthenticationSuccess(request, response, authentication);
            return;
        }

        ResultMsg msg = new ResultMsg(true,"登录成功");
        ResponseTypeUtils.respondJsonData(response,JSON.toJSONString(msg));
    }
}
