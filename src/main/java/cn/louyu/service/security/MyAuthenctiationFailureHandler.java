package cn.louyu.service.security;

import cn.louyu.models.ResultMsg;
import cn.louyu.service.security.utils.ResponseTypeUtils;
import cn.louyu.utils.TextUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *登陆失败的处理器
 * */
@Component("myAuthenctiationFailureHandler")
public class MyAuthenctiationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        if(!ResponseTypeUtils.isRespondJson(request)) {
            // 父类的方法 就是 跳转
            super.onAuthenticationFailure(request, response, exception);
            return;
        }

        ResultMsg msg = new ResultMsg(false,"登录验证失败[错误信息："+exception.getMessage()+"]");
        ResponseTypeUtils.respondJsonData(response,JSON.toJSONString(msg));
    }
}
