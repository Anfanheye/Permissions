package com.system.custom;

import com.common.utils.MD5;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 类描述：
 *     自定义密码组件
 * @ClassName CustomMd5PasswordEncoder
 * @Description TODO
 * @Author Ming
 * @Date 2022/11/3 17:18
 * @Version 1.0
 */
@Component
public class CustomMd5PasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        return MD5.encrypt(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(MD5.encrypt(rawPassword.toString()));
    }
}
