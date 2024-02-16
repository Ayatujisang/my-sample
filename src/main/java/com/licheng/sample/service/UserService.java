package com.licheng.sample.service;

import com.licheng.sample.entity.UserEntity;
import com.licheng.sample.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * @author LiCheng
 * @date  2024-02-16 19:29:18
 *
 * 用户service
 */
@SuppressWarnings("all")
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 创建用户
     *
     * @param user
     */
    public void createUser(UserEntity user) {
        userMapper.insert(user);
    }
}
