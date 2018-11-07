package cn.louyu.service.security;

import cn.louyu.config.SystemConfig;
import cn.louyu.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MyPasswordEncode implements PasswordEncoder {
    @Autowired
    private SystemConfig systemConfig;
    @Override
    public String encode(CharSequence rawPassword) {
        return MD5Util.MD5Encode(rawPassword.toString(),systemConfig.getEncoding(),false);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(encode(rawPassword));
    }
}
