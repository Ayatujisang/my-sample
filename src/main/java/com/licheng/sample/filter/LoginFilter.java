package com.licheng.sample.filter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 * @author LiCheng
 * @date  2024-02-15 17:22:29
 *
 * 登入拦截器
 */
@SuppressWarnings("all")
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private AntPathRequestMatcher pathMatcher;

    /**
     * 构造器 传入拦截地址
     *
     * @param filterProcessesUrl
     */
    public LoginFilter(String filterProcessesUrl) {
        AntPathRequestMatcher pathMatcher = new AntPathRequestMatcher(filterProcessesUrl, "POST");
        super.setFilterProcessesUrl(filterProcessesUrl);
    }

    /**
     * 验证逻辑
     *
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        return null;
    }
}
