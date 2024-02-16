package com.licheng.sample.filter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
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

    private static final String LOGIN_ERROR_PATH = "/login?error";

    private static final String OAUTH_PATH = "/oauth/authorize";


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
     * 这里重写拦截
     * 登入请求会先经过
     *
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if (req.getServletPath().equals(OAUTH_PATH)) {
            for (String key : req.getParameterMap().keySet()) {
                String[] values = req.getParameterMap().get(key);
                if (!values[0].equals(HtmlUtils.htmlEscape(values[0]))) {
                    throw new RuntimeException("参数" + key + "包含非法参数");
                }
            }
        }



        chain.doFilter(request, response);
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
