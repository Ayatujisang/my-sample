package com.licheng.sample.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.licheng.sample.entity.UserEntity;
import com.licheng.sample.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public UserEntity selectByUserName(String userName) {
        UserEntity userEntity = userMapper.selectOne(new QueryWrapper<UserEntity>().lambda()
                .eq(UserEntity::getUserName, userName));
        return userEntity;
    }

    public boolean existsByUserName(String userName) {
        return userMapper.exists(new QueryWrapper<UserEntity>().lambda()
                .eq(UserEntity::getUserName, userName));
    }

    public boolean existsByNickName(String nickName) {
        return userMapper.exists(new QueryWrapper<UserEntity>().lambda()
                .eq(UserEntity::getNickName, nickName));
    }

    public boolean existsByPhone(String phone) {
        return userMapper.exists(new QueryWrapper<UserEntity>().lambda()
                .eq(UserEntity::getPhone, phone));
    }

    public List<UserEntity> selectAll(){
        return userMapper.selectList(null);
    }
}
