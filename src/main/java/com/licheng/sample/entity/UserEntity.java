package com.licheng.sample.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/*
 * @author LiCheng
 * @date  2024-02-15 21:45:07
 *
 * 用户实体
 */
@Data
@TableName("sys_user")
public class UserEntity {
    //type = IdType.ASSIGN_ID 标识使用默认的雪花算法进行主键生成
    // > 此算法可以被实现了IdentifierGenerator接口的自定义类重写 重写后此配置相当于使用自定义生成策略
    // > 在com.licheng.sample.config.PrimaryKeyGenerator内 已经实现自定义主键生成
    @TableId(value = "uid", type = IdType.ASSIGN_ID)
    private Long uid;

    @TableField(value = "user_name", insertStrategy = FieldStrategy.NOT_NULL, updateStrategy = FieldStrategy.NOT_NULL)
    private String userName;

    @TableField(value = "nick_name", insertStrategy = FieldStrategy.NOT_NULL, updateStrategy = FieldStrategy.NOT_NULL)
    private String nickName;

    @TableField(value = "salt", insertStrategy = FieldStrategy.NOT_NULL)
    private String salt;

    @TableField(value = "password", insertStrategy = FieldStrategy.NOT_NULL, updateStrategy = FieldStrategy.NOT_NULL)
    private String password;

    //fill = FieldFill.INSERT_UPDATE 标识此字段可以自动填充
    @TableField(value = "create_time", fill = FieldFill.INSERT_UPDATE)
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8") 强制写入timestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @TableField(value = "create_user_id", fill = FieldFill.INSERT_UPDATE)
    private Long createUserId;

    @TableField(value = "last_update_user_id", fill = FieldFill.INSERT_UPDATE)
    private Long lastUpdateUserId;

}
