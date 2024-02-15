package com.licheng.sample.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/*
 * @author LiCheng
 * @date  2024-02-15 21:45:07
 *
 * 用户实体
 */
@Data
@TableName("sys_user")
public class UserEntity {

    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long uid;

}
