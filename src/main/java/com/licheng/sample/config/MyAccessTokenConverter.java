/*
package com.licheng.sample.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.stereotype.Component;

import java.util.Map;

*/
/*
 * @author LiCheng
 * @date  2024-02-17 21:15:50
 *
 * 自定义accessToken转换器
 *//*

@Component
public class MyAccessTokenConverter extends DefaultAccessTokenConverter {

    @Autowired
    private ClientDetailsService clientDetailsService;

    public Map<String, ?> convertAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        @SuppressWarnings("unchecked")
        Map<String, Object> response = (Map<String, Object>) super.convertAccessToken(token, authentication);

        return response;
    }
}
*/
