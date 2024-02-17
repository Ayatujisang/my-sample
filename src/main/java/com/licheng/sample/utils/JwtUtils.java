package com.licheng.sample.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static com.licheng.sample.utils.JwtUtils.TokenType.ASSESS_TOKEN;
import static com.licheng.sample.utils.JwtUtils.TokenType.REFRESH_TOKEN;

/*
 * @author LiCheng
 * @date  2024-02-17 15:30:44
 *
 * jwt工具类
 */
@SuppressWarnings("all")
public class JwtUtils {

    public static enum TokenType {
        ASSESS_TOKEN, REFRESH_TOKEN
    }

    public JwtUtils() {
    }

    //token的过期时间
    private static long ACCESS_TOKEN_EXP_MINUTES;

    @Value("${lc.config.login.jwt.accessTokenExpMinutes}")
    public void setAccessTokenExpMinutes(long accessTokenExpMinutes) {
        ACCESS_TOKEN_EXP_MINUTES = accessTokenExpMinutes;
    }


    private static long REFRESH_TOKEN_EXP_MINUTES;

    @Value("${lc.config.login.jwt.refreshTokenExpMinutes}")
    public void setRefreshTokenExpMinutes(long refreshTokenExpMinutes) {
        REFRESH_TOKEN_EXP_MINUTES = refreshTokenExpMinutes;
    }


    /* *
     * @Author lsc
     * <p> 校验token是否正确 </p>
     * @Param token
     * @Param username
     * @Param secret
     * @Return boolean
     */
    public static boolean verifyToken(String token, String username, String secret) {
        if (UtilValidate.isEmpty(token) || UtilValidate.isEmpty(username) || UtilValidate.isEmpty(secret)) return false;

        try {
            // 设置加密算法
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).withClaim("user_name", username).build();
            // 效验TOKEN
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }


    /* *
     * @Author lsc
     * <p>生成签名,30min后过期 </p>
     * @Param [username, secret]
     * @Return java.lang.String
     */
    public static String initToken(String username, String secret, TokenType type) {
        Date expTime = null;

        switch (type) {
            case ASSESS_TOKEN:
                expTime = new Date(System.currentTimeMillis() +
                        TimeUnit.MINUTES.toMillis(ACCESS_TOKEN_EXP_MINUTES));
                break;
            case REFRESH_TOKEN:
                expTime = new Date(System.currentTimeMillis() +
                        TimeUnit.MINUTES.toMillis(REFRESH_TOKEN_EXP_MINUTES));
        }
        Algorithm algorithm = Algorithm.HMAC256(secret);
        // 附带username信息
        return JWT.create()
                .withClaim("user_name", username)
                .withClaim("type", type.toString())
                .withExpiresAt(expTime)
                .sign(algorithm);

    }


    /* *
     * @Author lsc
     * <p> 获得用户名 </p>
     * @Param [request]
     * @Return java.lang.String
     */
    public static String getUserNameByHeaderToken(HttpServletRequest request, String jwtKeyName) {
        String token = request.getHeader(jwtKeyName);
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("user_name").asString();
    }

    public static String getUserNameByToken(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("user_name").asString();
    }

    public static HashMap<String, Object> getTokenInfo(String token) {
        HashMap<String, Object> result = new HashMap<>();

        DecodedJWT jwt = null;
        try {
            jwt = JWT.decode(token);
        } catch (NullPointerException e) {
            result.put("success", false);
            result.put("message", "token获取失败,在url的参数列表或header中未检测到token!");
            return result;
        } catch (JWTDecodeException e) {
            result.put("success", false);
            result.put("message", "此token已过期或无效!");
            return result;
        }

        result.put("succes", true);
        result.put("user_name", jwt.getClaim("user_name").asString());
        result.put("expire", jwt.getExpiresAt());
        return result;
    }

    /**
     * 判断token已经过期
     *
     * @param token
     * @return
     */
    public static boolean isTokenExp(String token) {
        DecodedJWT jwt = JWT.decode(token);
        Date expTime = jwt.getExpiresAt();
        //计算时间差
        return Duration.between(Instant.now(), expTime.toInstant()).getSeconds() < 0;
    }

    public static void main(String[] args) {
        Algorithm algorithm = Algorithm.HMAC256("123456");

        String token = JWT.create()
                .withClaim("user_name", "admin")
                .withClaim("type", "1")
                .withExpiresAt(new Date(System.currentTimeMillis() - TimeUnit.SECONDS.toMillis(1)))
                .sign(algorithm);

        System.out.println("token = " + token);
        boolean tokenExp = JwtUtils.isTokenExp(token);

        System.out.println("expiresAt = " + tokenExp);


    }

}
