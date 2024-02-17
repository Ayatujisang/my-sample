package com.licheng.sample.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/*
 * @author LiCheng
 * @date  2024-02-17 22:12:37
 *
 * 抽取实体公共属性 统一自动插入
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BaseEntity {
    //fill = FieldFill.INSERT_UPDATE 标识此字段可以自动填充
    @TableField(value = "create_time", fill = FieldFill.INSERT_UPDATE)
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8") 强制写入timestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected Date createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected Date updateTime;

/*    @TableField(value = "create_user_id", fill = FieldFill.INSERT_UPDATE)
    private Long createUserId;*/

    @TableField(value = "last_update_user_id", fill = FieldFill.UPDATE, updateStrategy = FieldStrategy.NOT_NULL)
    protected Long lastUpdateUserId;
}
