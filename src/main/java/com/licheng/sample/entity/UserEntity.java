package com.licheng.sample.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/*
 * @author LiCheng
 * @date  2024-02-15 21:45:07
 *
 * 用户实体
 */
@Data
@TableName("sys_user")
public class UserEntity extends BaseEntity{
    //type = IdType.ASSIGN_ID 标识使用默认的雪花算法进行主键生成
    // > 此算法可以被实现了IdentifierGenerator接口的自定义类重写 重写后此配置相当于使用自定义生成策略
    // > 在com.licheng.sample.config.PrimaryKeyGenerator内 已经实现自定义主键生成
    @TableId(value = "uid", type = IdType.ASSIGN_ID)
    private Long uid;

    @TableField(value = "user_name", insertStrategy = FieldStrategy.NOT_NULL, updateStrategy = FieldStrategy.NOT_NULL)
    private String userName;

    @TableField(value = "nick_name", insertStrategy = FieldStrategy.NOT_NULL, updateStrategy = FieldStrategy.NOT_NULL)
    private String nickName;

    @TableField(value = "phone")
    private String phone;

    @TableField(value = "salt", insertStrategy = FieldStrategy.NOT_NULL)
    private String salt;

    @TableField(value = "password", insertStrategy = FieldStrategy.NOT_NULL, updateStrategy = FieldStrategy.NOT_NULL)
    private String password;
}
