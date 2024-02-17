package com.licheng.sample.config;

import com.licheng.sample.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;

/*
 * @author LiCheng
 * @date  2024-02-17 17:54:12
 *
 * Oauth2协议的授权服务器配置
 * 这个类是单独对Oauth2授权相关内容进行配置 与WebSecurityConfigurerAdapter不相同
 * WebSecurityConfigurerAdapter 是对spring Security进行配置
 * 如果有多个应用 配置了AuthorizationServerConfigurerAdapter类的服务会作为认证服务器
 * 多个服务实现单点登录 仅需认证系统配置AuthorizationServerConfigurerAdapter
 * 其他服务仅需配置与WebSecurityConfigurerAdapter
 */
@Configuration
@EnableAuthorizationServer
@SuppressWarnings("all")
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailService;
    @Autowired
    private AppService appService;

    /**
     * @return
     */
    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * 自定义jwtToken转换器
     * <p>
     * 通过openssl 或 keytool 生成JKS证书文件 keytool为java自带 jdk安装后使用终端即可
     * <p>
     * keytool 命令参数:
     * >
     * -alias 产生别名
     * -keystore 指定密钥库的名称(就像数据库一样的证书库，可以有很多个证书，cacerts这个文件是jre自带的，你也可以使用其它文件名字，如果没有这个文件名字，它会创建这样一个)
     * -storepass 指定密钥库的密码
     * -keypass 指定别名条目的密码
     * -list 显示密钥库中的证书信息
     * -v 显示密钥库中的证书详细信息
     * -export 将别名指定的证书导出到文件
     * -file 参数指定导出到文件的文件名
     * -delete 删除密钥库中某条目
     * -import 将已签名数字证书导入密钥库
     * -keypasswd 修改密钥库中指定条目口令
     * -dname 指定证书拥有者信息
     * -keyalg 指定密钥的算法
     * -validity 指定创建的证书有效期多少天
     * -keysize 指定密钥长度
     *
     * <p>
     * > 生成jsk文件: keytool -alias lckeystore -genkeypair -keystore keystore.jks -storepass lcstorepass -keyalg RSA -validity 3650
     * > 导出公钥:    keytool -list -rfc --keystore keystore.jks
     * <p>
     * > openssl genrsa -out jwt.pem 2048 Generating RSA private key, 2048 bit long modulus
     * > openssl x509 -inform pem -pubkey
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        KeyPair keyPair = new KeyStoreKeyFactory(new ClassPathResource("keystore.jks"), "lcstorepass".toCharArray())
                .getKeyPair("lckeystore");
        converter.setKeyPair(keyPair);

        return converter;
    }

    /**
     * clientDetailsService注入，决定clientDeatils信息的处理服务。
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //从数据库中动态获取注册到认证中心的app
        clients.withClientDetails(appService);

        /*clients.inMemory()
                .withClient("app-a")
                .secret(passwordEncoder.encode("app-a-1234"))
                .authorizedGrantTypes("refresh_token", "authorization_code")
                .accessTokenValiditySeconds(3600)
                .scopes("all");*/
    }

    /**
     * 访问端点配置 tokenStroe、tokenEnhancer服务
     *
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.tokenStore(jwtTokenStore())
                .accessTokenConverter(jwtAccessTokenConverter())
                .userDetailsService(userDetailService);
    }


    /**
     * 访问安全配置
     *
     * @param oauthServer
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer.tokenKeyAccess("permitAll()")
                // 获取密钥需要身份认证
                .checkTokenAccess("isAuthenticated()");
    }


}
