package com.licheng.sample.authorization;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/*
 * @author LiCheng
 * @date  2024-02-17 23:15:59
 *
 * 授权认证器管理
 * 管理一个AuthenticationProvider列表，每个AuthenticationProvider都是一个认证器
 */
@Component
public class MyAuthenticationManager implements AuthenticationManager {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return null;
    }
}
