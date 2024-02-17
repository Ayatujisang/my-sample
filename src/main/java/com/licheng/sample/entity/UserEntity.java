package com.licheng.sample.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/*
 * @author LiCheng
 * @date  2024-02-15 21:45:07
 *
 * 用户实体
 */
@Data
@TableName("sys_user")
@ToString
public class UserEntity extends BaseEntity implements UserDetails {
    //type = IdType.ASSIGN_ID 标识使用默认的雪花算法进行主键生成
    // > 此算法可以被实现了IdentifierGenerator接口的自定义类重写 重写后此配置相当于使用自定义生成策略
    // > 在com.licheng.sample.config.PrimaryKeyGenerator内 已经实现自定义主键生成
    @TableId(value = "uid", type = IdType.ASSIGN_ID)
    private Long uid;

    @TableField(value = "username", insertStrategy = FieldStrategy.NOT_NULL, updateStrategy = FieldStrategy.NOT_NULL)
    private String username;

    @TableField(value = "nick_name", insertStrategy = FieldStrategy.NOT_NULL, updateStrategy = FieldStrategy.NOT_NULL)
    private String nickName;

    @TableField(value = "phone")
    private String phone;

    @TableField(value = "salt", insertStrategy = FieldStrategy.NOT_NULL)
    private String salt;

    @TableField(value = "password", insertStrategy = FieldStrategy.NOT_NULL, updateStrategy = FieldStrategy.NOT_NULL)
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Lists.newArrayList();
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    //账户永不过期 这里可以根据需要进行配置
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //账号永不被锁 这里可以根据需要进行配置
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //凭证永不过期 这里可以根据需要进行配置
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //是否激活 这里可以根据需要进行配置
    @Override
    public boolean isEnabled() {
        return true;
    }
}
