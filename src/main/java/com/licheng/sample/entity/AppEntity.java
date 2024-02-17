package com.licheng.sample.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/*
 * @author LiCheng
 * @date  2024-02-17 21:20:30
 *
 * App实体 用于单点认证
 */
@Data
@TableName("app")
public class AppEntity extends BaseEntity {

    @TableId(value = "appId", type = IdType.ASSIGN_ID)
    private Long appId;

    @TableField(value = "name", insertStrategy = FieldStrategy.NOT_NULL, updateStrategy = FieldStrategy.NOT_NULL)
    private String name;

    @TableField(value = "client_id", insertStrategy = FieldStrategy.NOT_NULL, updateStrategy = FieldStrategy.NOT_NULL)
    private String clientId;

    @TableField(value = "client_secret", insertStrategy = FieldStrategy.NOT_NULL, updateStrategy = FieldStrategy.NOT_NULL)
    private String clientSecret;
}
