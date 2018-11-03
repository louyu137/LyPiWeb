package cn.louyu.controller.api;

import cn.louyu.models.ResultMsg;
import cn.louyu.models.UserInfo;
import cn.louyu.service.jpa.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserApiController {

    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("/addUser")
    public ResultMsg addUser(String username, String password){
        UserInfo userInfo=new UserInfo(username,password);
        return userInfoService.addUser(userInfo);
    }

    @PostMapping("/loginVerify")
    public ResultMsg loginVerify(String username, String password){
        UserInfo userInfo=new UserInfo(username,password);
        return userInfoService.loginVerify(userInfo);
    }
}
