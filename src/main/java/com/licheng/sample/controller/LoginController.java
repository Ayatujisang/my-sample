package com.licheng.sample.controller;

import com.alibaba.fastjson2.JSONObject;
import com.licheng.sample.entity.UserEntity;
import com.licheng.sample.service.UserService;
import com.licheng.sample.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/*
 * @author LiCheng
 * @date  2024-02-14 17:47:03
 *
 * 登录相关入口
 */
@SuppressWarnings("all")
@RequestMapping("/login")
@RestController
public class LoginController {

/*    @Value("${lc.config.login.captchaKey}")
    private String captchaKey;*/

    @Autowired
    private UserService userService;

    @RequestMapping("/success")
    public JSONObject loginSuccess() {
        List<UserEntity> userEntities = userService.selectAll();
        return ResponseUtil.makeSuccessResponse("登录成功");
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
        for (int i = 0; i < 6; i++) {
            int code = random.nextInt(10);
            sb.append(code);
            graphics.drawString(code + "", p, 30);
            p += 15;
        }
        //生成验证码
        String vcode = sb.toString();

        //这里存session版本
//        request.getSession().setAttribute(captchaKey, vcode);

        //存redis版本 给验证码加了有效期 2分钟有效期
        RedisUtils.setForExpMinute(request.getRequestedSessionId(),vcode, 2);

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

    /**
     * 注册
     *
     * @param user
     * @return
     */
    @PostMapping("/register")
    public JSONObject register(@RequestBody UserEntity user, HttpServletRequest request) {
        //获取用户输入的验证码
        Object captchaCode = request.getParameter("captchaCode");
        if (UtilValidate.isEmpty(captchaCode))
            return ResponseUtil.makeErrorResponse("请输入验证码!");
        //获取缓存的验证码
        String sessionCode = getCaptchaCode(request);
        if (UtilValidate.isEmpty(sessionCode))
            return ResponseUtil.makeErrorResponse("验证码未获取或已过期!");
        if (!String.valueOf(captchaCode).equals(sessionCode))
            return ResponseUtil.makeErrorResponse("验证码错误!");

        String userName = user.getUserName();
        if (UtilValidate.isEmpty(userName))
            return ResponseUtil.makeErrorResponse("用户名不能为空!");
        if (!PatternUtils.checkUserName(userName))
            return ResponseUtil.makeErrorResponse("用户名格式错误,必须以字母开头，长度为5~16位，只允许包含字母、数字或下划线!");
        if (userService.existsByUserName(userName))
            return ResponseUtil.makeErrorResponse("用户名已存在!");


        String password = user.getPassword();
        if (UtilValidate.isEmpty(password))
            return ResponseUtil.makeErrorResponse("密码不能为空!");
        if (!PatternUtils.checkPassword(password))
            return ResponseUtil.makeErrorResponse("密码格式错误,必须包含字母、数字、特称字符，至少8个字符，最多16个字符!");

        String nickName = user.getNickName();
        if (UtilValidate.isEmpty(nickName))
            return ResponseUtil.makeErrorResponse("昵称不能为空!");
        if (userService.existsByNickName(nickName))
            return ResponseUtil.makeErrorResponse("昵称已存在!");

        String phone = user.getPhone();
        if (UtilValidate.isNotEmpty(phone) && userService.existsByPhone(phone))
            return ResponseUtil.makeErrorResponse("该手机号以注册!");

        //生成salt
        user.setSalt(UUID.randomUUID().toString().substring(0, 5));
        //密码加密
        user.setPassword(AESUtils.AESEncode(user.getPassword(), user.getSalt()));

        try {
            userService.createUser(user);
        } catch (Exception e) {
            return ResponseUtil.makeErrorResponse("注册失败!");
        }

        return ResponseUtil.makeSuccessResponse();
    }

    private String getCaptchaCode(HttpServletRequest request) {
        //session版本获取验证码
        /*HttpSession session = request.getSession();
        Object codeObj = session.getAttribute(captchaKey);*/

        //redis版本获取验证码
        Object codeObj = RedisUtils.get(request.getRequestedSessionId());

        return UtilValidate.isEmpty(codeObj) ? null : String.valueOf(codeObj);
    }

}
