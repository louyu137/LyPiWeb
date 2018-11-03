package cn.louyu.service.security;

import cn.louyu.config.Constant;
import cn.louyu.utils.MD5Util;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MyPasswordEncode implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        return MD5Util.MD5Encode(rawPassword.toString(), Constant.ENCODING,false);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(encode(rawPassword));
    }
}
