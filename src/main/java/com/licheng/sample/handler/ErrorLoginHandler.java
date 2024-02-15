package com.licheng.sample.handler;

import com.licheng.sample.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 * @author LiCheng
 * @date  2024-02-15 19:58:12
 *
 * 登入失败的处理
 */
@SuppressWarnings("all")
public class ErrorLoginHandler extends SimpleUrlAuthenticationFailureHandler {

    private String defaultFailureUrl;

    //登录失败过期时间
    @Value("${lc.config.login.errorExpMinutes}")
    private int errorExpMinutes = 5;

    //默认连续失败次数
    @Value("${lc.config.login.loginErrorCount}")
    private long loginErrorCount;

    @Value("${lc.config.login.errorKey}")
    private String errorKey;

    @Value("${lc.config.login.stopLoginKey}")
    private String stopLoginKey;

    public ErrorLoginHandler(String defaultFailureUrl) {
        super(defaultFailureUrl);
        this.defaultFailureUrl = defaultFailureUrl;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException, ServletException {
        //在此处进行增强
        String message = authenticationException.getMessage();
        if (null != message && message.indexOf(",") > -1) {
            String[] split = message.split(",");
            String username = split[0];
            authenticationException = new BadCredentialsException(split[1]);

            //将username存入redis
            if (!RedisUtils.hasKey(errorKey + username)) {
                //不存在key
                RedisUtils.setForExpMinute(errorKey + username, 1, errorExpMinutes);
            } else {
                int count = (int) RedisUtils.get(errorKey + username);
                if (count <= loginErrorCount) {
                    //存在key key自增并刷新登入失败过期时间
                    count++;
                    RedisUtils.setForExpMinute(errorKey + username, count, errorExpMinutes);
                } else {
                    //超过五次 error并禁止登陆
                    RedisUtils.setForExpMinute(stopLoginKey + username, true, errorExpMinutes);
                    RedisUtils.delete(errorKey + username);
                    authenticationException = new BadCredentialsException("连续登陆失败次数过多,账号锁定" + errorExpMinutes + "分钟!");
                }
            }

        }

        super.onAuthenticationFailure(request, response, authenticationException);
    }

}
