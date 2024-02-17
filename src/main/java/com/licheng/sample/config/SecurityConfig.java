package com.licheng.sample.config;

import com.licheng.sample.filter.JwtAuthencationTokenFilter;
import com.licheng.sample.handler.ErrorLoginHandler;
import com.licheng.sample.handler.LogoutSuccessHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.List;

/*
 * @author LiCheng
 * @date  2024-02-15 17:02:37
 *
 * 授权模式的配置类
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //登录的请求地址 /**标识放行包含此url的全部资源地址
    private static final String LOGIN_PATH = "/login";

    //登出的请求地址
    private static final String LOGOUT_PATH = "/logout";

    //登录失败次数过多跳转的地址
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
        // 1. 关闭csrf 防止因默认csrf策略导致post请求404
        http.csrf().disable()

                // 2. 标头设置
                .headers()
                //不允许iframe嵌套 防止被其他非本地站点引用嵌套
                .frameOptions().disable()
                //禁用HTTP响应标头进行缓存
                .cacheControl().disable()

                //and() 在and()前配置的条件生效的同时添加其他条件
                .and()

                // 3. 基于jwt认证 将session设置为无状态
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()

                // 4. 配置JWT前拦截 在用户名密码认证服务前
                .addFilterBefore(new JwtAuthencationTokenFilter(), UsernamePasswordAuthenticationFilter.class)

                // 5. authorizeRequests() 配置鉴权请求
                .authorizeRequests()
                //antMatchers() 键入需要请求地址  permitAll()放行
                .antMatchers("/actuator/**", "/oauth/authorize", "/oauth/confirm_access",
                        "/captchaCode", LOGIN_PATH + "/**", LOGOUT_PATH)
                .permitAll()
                //anyRequest() 任意请求 authenticated()需要被鉴权
                .anyRequest().authenticated()
                .and()
                //formLogin() 配置需要通过表单登入
                .formLogin()

                // loginPage() 登录的地址
                //  > 如果配置了此地址 所有非get请求都会默认先去访问设定的目标地址
                //    此配置设定后若该地址无法访问 则会默认所有非get请求404 配置的放行地址也无效
                //    出现这个问题和csrf拦截很像但不是一个问题
//                .loginPage(LOGIN_PATH)

                //defaultSuccessUrl()登入成功跳转的路径
                .defaultSuccessUrl("/login/success")

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
    }


}
