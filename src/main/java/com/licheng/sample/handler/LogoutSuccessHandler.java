package com.licheng.sample.handler;

import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

/*
 * @author LiCheng
 * @date  2024-02-15 21:38:21
 *
 * 登出成功的处理
 */
public class LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
    public LogoutSuccessHandler(String defaultTargetUrl, String targetUrlParameter) {
        SimpleUrlLogoutSuccessHandler logoutSuccessHandler = new SimpleUrlLogoutSuccessHandler();
        logoutSuccessHandler.setDefaultTargetUrl(defaultTargetUrl);
        logoutSuccessHandler.setTargetUrlParameter(targetUrlParameter);

    }
}
