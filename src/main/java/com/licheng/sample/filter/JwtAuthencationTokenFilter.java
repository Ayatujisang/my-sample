package com.licheng.sample.filter;

import com.google.common.collect.Lists;
import com.licheng.sample.entity.UserEntity;
import com.licheng.sample.service.UserService;
import com.licheng.sample.utils.JwtUtils;
import com.licheng.sample.utils.LoginUserUtils;
import com.licheng.sample.utils.UtilContext;
import com.licheng.sample.utils.UtilValidate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 * @author LiCheng
 * @date  2024-02-17 14:31:13
 *
 * JWT拦截器 进行jwt认证
 * OncePerRequestFilter 每次请求只会过一次拦截
 * 拦截器在容器中加载顺序比springbean快 只能使用静态成员方式注入配置文件中的内容
 */
@Component
public class JwtAuthencationTokenFilter extends OncePerRequestFilter {

    //JWT在请求头中的key
    private static String JWT_ACCESS_KEY;

    @Value("${lc.config.login.jwt.accessKey}")
    public void setJwtAccessKey(String jwtAccessKey) {
        this.JWT_ACCESS_KEY = jwtAccessKey;
    }

    private static String JWT_REFRESH_KEY;

    @Value("${lc.config.login.jwt.refreshKey}")
    public void setJwtRefreshKey(String JWT_REFRESH_KEY) {
        this.JWT_REFRESH_KEY = JWT_REFRESH_KEY;
    }

    //JWT的accessToken前缀 accessToken用于验证身份
    //用户登入后每次请求都会携带accessToken
    private static String JWT_ACCESS_TOKEN_PREFIX;

    @Value("${lc.config.login.jwt.accessTokenPrefix}")
    public void setJwtAccessTokenPrefix(String accessTokenPrefix) {
        this.JWT_ACCESS_TOKEN_PREFIX = accessTokenPrefix;
    }

    //JWT的refreshToken前缀 refreshToken用于刷新accessToken
    // > 用户accessToken失效后 请求会401
    // > 前端会根据根据存在accessToken并返回401 重新发送refreshToken来重新生成一个accessToken
    // > refreshToken只在accessToken失效后才传输
    private static String JWT_REFRESH_TOKEN_PREFIX;

    @Value("${lc.config.login.jwt.refreshTokenPrefix}")
    public void setJwtRereshTokenPrefix(String refreshTokenPrefix) {
        this.JWT_REFRESH_TOKEN_PREFIX = refreshTokenPrefix;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //从请求头中获取jwt的accessToken
        String accessTokenWithPrefix = request.getHeader(JWT_ACCESS_KEY);
        if (UtilValidate.isEmpty(accessTokenWithPrefix) || !accessTokenWithPrefix.startsWith(JWT_ACCESS_TOKEN_PREFIX)) {
            //不存在 直接放行
            filterChain.doFilter(request, response);
            return;
        }


        // 去除字段名称, 获取accessToken
        String accessToken = accessTokenWithPrefix.substring(accessTokenWithPrefix.length());
        // 利用token获取用户名
        String jwtUserName = JwtUtils.getUserNameByToken(accessToken);

        // token存在 验证用户已正常登入
        if (LoginUserUtils.checkUserLogin(jwtUserName)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 自定义数据源获取用户信息
        UserEntity userEntity = UtilContext.getBean("userService", UserService.class).selectByUserName(jwtUserName);
        // 验证token是否有效 验证token用户名和存储的用户名是否一致以及是否在有效期内, 重新设置用户对象
        if (JwtUtils.verifyToken(accessToken, userEntity.getUsername(), userEntity.getPassword())) {
            // 重新将用户信息封装到UsernamePasswordAuthenticationToken
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userEntity, null, Lists.newArrayList());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            // 将信息存入上下文对象
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);
    }

}
