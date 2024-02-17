package com.licheng.sample.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.licheng.sample.entity.UserEntity;
import com.licheng.sample.mapper.UserMapper;
import com.licheng.sample.utils.UtilValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
public class UserService implements UserDetailsService {
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
                .eq(UserEntity::getUsername, userName));
        return userEntity;
    }

    public boolean existsByUserName(String userName) {
        return userMapper.exists(new QueryWrapper<UserEntity>().lambda()
                .eq(UserEntity::getUsername, userName));
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = selectByUserName(username);
        if(UtilValidate.isEmpty(userEntity))
            return null;

        return userEntity;
    }
}
