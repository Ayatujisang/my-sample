package com.licheng.sample.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.licheng.sample.utils.LoginUserUtils;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/*
 * @author LiCheng
 * @date  2024-02-15 23:58:14
 *
 * mybatils-plus db操作时自动注入类属性的处理类
 */
@SuppressWarnings("all")
public class AutoFillHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        // 方式1: setFieldValByName 键入字段名 值 metaObject
//        this.setFieldValByName("createTime", new Date(), metaObject);

        // 方式2: strictInsertFill
        //  > 键入metaObject,字段名,Supplier参数 需要一个有返回值的函数作为参数,上一个参数返回值的class
        this.strictInsertFill(metaObject, "createTime", () -> new Date(), Date.class);
        this.strictInsertFill(metaObject, "updateTime", () -> new Date(), Date.class);
//        this.strictInsertFill(metaObject, "createUserId", () -> LoginUserUtils.getLoginUserId(), Long.class);
//        this.strictInsertFill(metaObject, "lastUpdateUserId", () -> LoginUserUtils.getLoginUserId(), Long.class);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createTime", () -> new Date(), Date.class);
        this.strictInsertFill(metaObject, "updateTime", () -> new Date(), Date.class);
//        this.strictInsertFill(metaObject, "createUserId", () -> LoginUserUtils.getLoginUserId(), Long.class);
        this.strictInsertFill(metaObject, "lastUpdateUserId", () -> LoginUserUtils.getLoginUserId(), Long.class);
    }
}
