package com.licheng.sample.controller;

import com.alibaba.fastjson2.JSONObject;
import com.licheng.sample.utils.ResponseUtil;
import com.licheng.sample.utils.UtilValidate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/*
 * @author LiCheng
 * @date  2024-02-14 17:47:03
 *
 * 登录相关入口
 */
@RequestMapping("/login")
@RestController
public class LoginController {

    @Value("${lc.config.login.captchaKey}")
    private String captchaKey;

    @PostMapping("/success")
    public JSONObject loginSuccess() {
        return ResponseUtil.makeSuccessResponse();
    }

    /**
     * 通过验证码图片
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/captchaCode")
    public void writeCaptchaCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 图片宽高
        int width = 120;
        int height = 40;

        // 用java生成一个图片
        // 前两个参数是宽高，第三个参数表示像素点的颜色，用int型组成rgb组成颜色有三位0~255,0~255,0~255
        BufferedImage img = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        // 得到画布
        Graphics graphics = img.getGraphics();
        // 设置使用的颜色 java画布无论是边框还是内容的颜色都是一个颜色 不会分别设置
        // 灰色
        graphics.setColor(Color.gray);
        // 画一个灰色的背景外边留了一像素
        graphics.fillRect(1, 1, width - 2, height - 2);
        // 画一个边框
        graphics.setColor(Color.RED);
        graphics.drawRect(0, 0, width - 1, height - 1);

        // 绘制文字
        // 设置绘制文字的字体 黑体 加粗|斜体 字体大小
        graphics.setFont(new Font("黑体", Font.BOLD | Font.ITALIC, 30));

        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        // 往图片上画
        int p = 15;
        for (int i = 0; i < 4; i++) {
            int code = random.nextInt(10);
            sb.append(code);
            graphics.drawString(code + "", p, 30);
            p += 15;
        }
        //生成验证码
        String vcode = sb.toString();
        request.getSession().setAttribute(captchaKey, vcode);

        // 往图片上画线也就是噪点
        for (int i = 0; i < 10; i++) {
            graphics.setColor(new Color(random.nextInt(256), random.nextInt(256), random
                    .nextInt(256)));
            graphics.drawLine(random.nextInt(width), random.nextInt(height),
                    random.nextInt(width), random.nextInt(height));
        }
        // 把图片交给浏览器
        ImageIO.write(img, "jpg", response.getOutputStream());
    }

    private String getCaptchaCode(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object codeObj = session.getAttribute(captchaKey);
        return UtilValidate.isEmpty(codeObj) ? null : String.valueOf(codeObj);
    }

    private void removeCaptchaCode(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(captchaKey);
    }
}
