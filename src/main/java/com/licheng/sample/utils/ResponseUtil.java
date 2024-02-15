package com.licheng.sample.utils;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import com.sun.istack.NotNull;

import java.util.Collection;

/*
 * @author LiCheng
 * @date  2024-02-14 17:50:40
 *
 * 统一返回类生成工具
 */
public class ResponseUtil {

    public static JSONObject makeSuccessResponse() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", true);

        return jsonObject;
    }

    public static JSONObject makeSuccessResponse(Object data) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", true);
        jsonObject.put("data", data);

        return jsonObject;
    }

    public static <T extends Collection> JSONObject makeSuccessResponse(T data) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", true);
        jsonObject.put("rowsTotal", UtilValidate.isEmpty(data) ? 0 : data.size());
        jsonObject.put("data", UtilValidate.isEmpty(data) ? Lists.newArrayList() : data);

        return jsonObject;
    }

    public static JSONObject makeSuccessResponse(@NotNull IPage page) {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("success", true);
        jsonObject.put("rowsTotal", page.getTotal());
        jsonObject.put("data", page);

        return jsonObject;
    }

    public static JSONObject makeErrorResponse(String errorCode, String errorMessage) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", false);
        jsonObject.put("errorCode", errorCode);
        jsonObject.put("message", errorMessage);

        return jsonObject;
    }

    public static JSONObject makeErrorResponse(String errorMessage) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", false);
        jsonObject.put("errorCode", "500");
        jsonObject.put("message", errorMessage);

        return jsonObject;
    }

}
