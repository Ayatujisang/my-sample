package com.licheng.sample.config;

import com.licheng.sample.handler.ErrorLoginHandler;
import com.licheng.sample.handler.LogoutSuccessHandler;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/*
 * @author LiCheng
 * @date  2024-02-15 17:02:37
 *
 * Spring-security配置类
 */
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //登录的请求地址
    private static final String LOGIN_PATH = "/login";

    //登出的请求地址
    private static final String LOGOUT_PATH = "/logout";

    private static final String LOGIN_ERROR_PATH = "/login?error";

    /**
     * 注册浏览器请求拦截策略
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        //ignoring() 过滤目标内容 不拦截此类型的资源 一般是静态资源目录放行
        web.ignoring().antMatchers("/js/**", "/css/**", "/webjars/**",
                "/bootstrap/**", "/images/**", "/health");
    }

    /**
     * 注册http请求拦截策略
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         * addFilterBefore 前拦截 传入 需要前置的拦截器和滞后目标的class
         *  > 例如: addFilterBefore(new LoginFilter("/login"), UsernamePasswordAuthenticationFilter.class)
         */

        //authorizeRequests() 配置http请求为鉴权请求
        http.authorizeRequests()
                //antMatchers() 键入需要请求地址  permitAll()放行
                .antMatchers("/actuator/**", "/oauth/authorize", "/oauth/confirm_access",
                        "/captchaCode", LOGIN_PATH, LOGOUT_PATH, LOGIN_ERROR_PATH)
                .permitAll()
                //anyRequest() 任意请求 authenticated()需要被鉴权
                .anyRequest().authenticated()

                //and() 在and()前配置的条件生效的同时添加其他条件
                .and()

                //formLogin() 配置需要通过表单登入
                .formLogin()

                //loginPage() 登录的地址
                .loginPage(LOGIN_PATH)

                //defaultSuccessUrl()登入成功跳转的路径
//                .defaultSuccessUrl("/home")

                //failureHandler() 登入失败的处理
                .failureHandler(new ErrorLoginHandler(LOGIN_ERROR_PATH))

                //failureUrl() 登录失败后跳转的url
//                .failureUrl("/login?error")

                //successHandler() 登录成功后的处理
//                .successHandler(new AmTokenWriterHandler())

                .permitAll()
                .and()

                //logout() 进行登出配置
                .logout()

                //addLogoutHandler() 登出后的处理
//                .addLogoutHandler(new CustomLogoutHandler()) //logout清Cookie

                //登出的地址配置
                .logoutRequestMatcher(new AntPathRequestMatcher(LOGOUT_PATH))

                //logoutSuccessHandler() 登出的处理
                .logoutSuccessHandler(new LogoutSuccessHandler(
                        "/login", "redirect"))

                .permitAll();


        //关闭csrf
        http.csrf().disable();
        //关闭iframe嵌套
        http.headers().frameOptions().disable();
    }



}
