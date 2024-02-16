package com.licheng.sample.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.licheng.sample.entity.UserEntity;
import com.licheng.sample.mapper.UserMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/*
 * @author LiCheng
 * @date  2024-02-16 00:20:03
 *
 * 登录用户相关的工具类
 */
@SuppressWarnings("all")
public class LoginUserUtils {

    private static UserMapper userMapper = UtilContext.getBean("userMapper", UserMapper.class);


    private LoginUserUtils() {
    }

    /**
     * 获取当前请求用户的登入id
     *
     * @return
     */
    public static Long getLoginUserId() {
        UserEntity userEntity = getLoginUser();
        return userEntity.getUid();

    }

    public static UserEntity getLoginUser() {
        //从springSecurity的上下文中获取当前请求用户的认证信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal() == null) {
            throw new RuntimeException("获取当前登入用户的账户信息失败!");
        }

        Object principal = authentication.getPrincipal();
        if (UtilValidate.isEmpty(principal)) {
            throw new RuntimeException("获取当前登入用户的账户信息失败!");
        }

        String userName = null;

        try {
            userName = String.valueOf(principal);
        } catch (Exception e) {
            throw new RuntimeException("获取当前登入用户的账户信息结果异常!");
        }

        //QueryWrapper 查询条件构造器 两种用法
        // > 1. lambda() 传入lambda表达式 直接使用对象和获取属性方法
        // > 2. 直接拼接条件 传入的第一个参数是数据库中的列名而非实体类的属性名
        //      userMapper.selectOne(new QueryWrapper<UserEntity>().eq("user_name", userName));
        UserEntity userEntity = userMapper.selectOne(new QueryWrapper<UserEntity>().lambda()
                .eq(UserEntity::getUserName, userName));

        if (UtilValidate.isEmpty(userEntity)) {
            throw new RuntimeException("获取当前登入用户的账户信息失败!");
        }

        return userEntity;
    }

}
