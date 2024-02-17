/*
package com.licheng.sample.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.licheng.sample.entity.AppEntity;
import com.licheng.sample.entity.UserEntity;
import com.licheng.sample.mapper.AppMapper;
import com.licheng.sample.utils.UtilValidate;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

*/
/*
 * @author LiCheng
 * @date  2024-02-18 00:24:19
 *
 * App的service
 * 从数据库读取所有注册到认证中心的APP信息
 *//*

@Service
@SuppressWarnings("all")
public class AppService implements ClientDetailsService, ClientRegistrationService {
    @Autowired
    private AppMapper appMapper;

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

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        AppEntity appEntity = appMapper.selectOne(new QueryWrapper<AppEntity>().lambda()
                .eq(AppEntity::getClientId, clientId));

        if(UtilValidate.isEmpty(appEntity))
            return null;

        BaseClientDetails clientDetails = appToClientDetails(appEntity);

        return clientDetails;
    }

    //这里使用自定义类AppEntity 将ClientDetails作为Bo用 不进行操作
    @Override
    public void addClientDetails(ClientDetails clientDetails) throws ClientAlreadyExistsException {

    }

    //这里使用自定义类AppEntity 将ClientDetails作为Bo用 不进行操作
    @Override
    public void updateClientDetails(ClientDetails clientDetails) throws NoSuchClientException {

    }

    //这里使用自定义类AppEntity 将ClientDetails作为Bo用 不进行操作
    @Override
    public void updateClientSecret(String s, String s1) throws NoSuchClientException {

    }

    //这里使用自定义类AppEntity 将ClientDetails作为Bo用 不进行操作
    @Override
    public void removeClientDetails(String s) throws NoSuchClientException {

    }

    @Override
    public List<ClientDetails> listClientDetails() {
        List<AppEntity> appEntities = appMapper.selectList(null);
        List<ClientDetails> collect = appEntities.stream().map(data -> appToClientDetails(data)).collect(Collectors.toList());
        return collect;
    }

        private static BaseClientDetails appToClientDetails(AppEntity app) {
        BaseClientDetails clientDetails = new BaseClientDetails();
        //拼装clientDetails
        clientDetails.setClientId(app.getClientId());
        clientDetails.setClientSecret(app.getClientSecret());
        //默认开启refresh_token和authorization_code两种授权方式
        clientDetails.setAuthorizedGrantTypes(Lists.newArrayList("refresh_token", "authorization_code"));
        //设置默认token过期时间
        Integer accessTokenExpTime = Integer.getInteger(String.valueOf(TimeUnit.MINUTES.toSeconds(ACCESS_TOKEN_EXP_MINUTES)));
        clientDetails.setAccessTokenValiditySeconds(accessTokenExpTime);
        Integer refreshTokenExpTime = Integer.getInteger(String.valueOf(TimeUnit.MINUTES.toSeconds(REFRESH_TOKEN_EXP_MINUTES)));
        clientDetails.setRefreshTokenValiditySeconds(refreshTokenExpTime);
        //配置作用域 这里默认给所有 不限制
        clientDetails.setScope(Lists.newArrayList("all"));
        return clientDetails;
    }

}
*/
