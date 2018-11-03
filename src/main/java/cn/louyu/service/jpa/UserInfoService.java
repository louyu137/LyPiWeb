package cn.louyu.service.jpa;

import cn.louyu.config.Constant;
import cn.louyu.models.ResultMsg;
import cn.louyu.models.UserInfo;
import cn.louyu.service.jpa.repository.UserInfoRepository;
import cn.louyu.utils.MD5Util;
import cn.louyu.utils.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserInfoService{
    @Autowired
    private UserInfoRepository repository;

    /**
     * 新添加一个用户
     * */
    public ResultMsg<UserInfo> addUser(UserInfo userInfo) {
        ResultMsg<UserInfo> resultMsg=new ResultMsg<>();
        if(userInfo==null){
            resultMsg.Msg="参数错误，实体为空";
            return resultMsg;
        }
        if(TextUtils.isNullOrEmpty(userInfo.getUserName())||TextUtils.isNullOrEmpty(userInfo.getPassword())){
            resultMsg.Msg="参数错误，用户名或密码为空";
            return resultMsg;
        }
        UserInfo user=getUserById(userInfo.getUserName());
        if (user != null) {
            resultMsg.Msg="此用户已存在";
            return resultMsg;
        }
        userInfo.setPassword(MD5Util.MD5Encode(userInfo.getPassword(), Constant.ENCODING, false));
        repository.saveAndFlush(userInfo);
        resultMsg.Success=true;
        resultMsg.Msg="添加用户成功";
        return resultMsg;
    }

    /**
     * 修改密码
     * */
    public ResultMsg<UserInfo> changePwd(UserInfo userInfo){
        ResultMsg<UserInfo> resultMsg=new ResultMsg<>();
        if(userInfo==null){
            resultMsg.Msg="参数错误，实体为空";
            return resultMsg;
        }
        if(TextUtils.isNullOrEmpty(userInfo.getUserName())||TextUtils.isNullOrEmpty(userInfo.getPassword())){
            resultMsg.Msg="参数错误，用户名或密码为空";
            return resultMsg;
        }
        UserInfo user=getUserById(userInfo.getUserName());
        if (user == null) {
            resultMsg.Msg="修改密码失败，此用户不存在";
            return resultMsg;
        }
        String pwdMd5=MD5Util.MD5Encode(userInfo.getPassword(), Constant.ENCODING, false);
        if(!pwdMd5.equals(user.getPassword())){
            repository.saveAndFlush(userInfo);
        }
        resultMsg.Success=true;
        resultMsg.Msg="修改密码成功";
        return resultMsg;
    }

    /**
     * 登录验证
     * */
    public ResultMsg<UserInfo> loginVerify(UserInfo userInfo) {
        ResultMsg<UserInfo> resultMsg = new ResultMsg<>();
        if (userInfo == null) {
            resultMsg.Msg = "参数错误，实体为空";
            return resultMsg;
        }
        if (TextUtils.isNullOrEmpty(userInfo.getUserName()) || TextUtils.isNullOrEmpty(userInfo.getPassword())) {
            resultMsg.Msg = "参数错误，用户名或密码为空";
            return resultMsg;
        }
        UserInfo user = getUserById(userInfo.getUserName());
        if (user == null) {
            resultMsg.Msg = "登录验证失败，用户名或密码错误";
            return resultMsg;
        }
        String pwdMd5 = MD5Util.MD5Encode(userInfo.getPassword(), Constant.ENCODING, false);
        if (!pwdMd5.equals(user.getPassword())) {
            resultMsg.Msg = "登录验证失败，用户名或密码错误";
            return resultMsg;
        }
        resultMsg.Success=true;
        resultMsg.Msg="登录验证成功";
        return resultMsg;
    }

    /**
     * 根据用户名来查询用户
     * */
    public UserInfo getUserById(String userName){
        Optional<UserInfo> optional=repository.findById(userName);
        if(optional.isPresent())
            return optional.get();
        return null;
    }

}
