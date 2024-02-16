package com.licheng.sample.utils;

import java.util.regex.Pattern;

/*
 * @author LiCheng
 * @date  2024-02-16 18:20:45
 *
 * 常用正则表达式 提供校验
 */
@SuppressWarnings("all")
public class PatternUtils {

    //用户名 母开头，允许5-16字节，允许字母数字下划线
    public static final String USER_NAME = "^[a-zA-Z][a-zA-Z0-9_]{4,15}$";

    /**
     * 校验用户名
     *
     * @param userName
     * @return
     */
    public static boolean checkUserName(String userName) {
        return Pattern.matches(USER_NAME, userName);
    }

    //密码 必须包含字母、数字、特称字符，至少8个字符，最多16个字符
    public static final String PASSWORD = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9])(.{8,16})$";

    /**
     * 校验用户名
     *
     * @param password
     * @return
     */
    public static boolean checkPassword(String password) {
        return Pattern.matches(PASSWORD, password);
    }

    //国内手机号
    public static final String PHONE = "0?(13|14|15|17|18|19)[0-9]{9}";

    /**
     * 校验手机号
     *
     * @param phone
     * @return
     */
    public static boolean checkPhone(String phone) {
        return Pattern.matches(PHONE, phone);
    }

    //中文
    public static final String CHINESE_CHARACTER = "[\u4e00-\u9fa5]";

    /**
     * 校验中文
     *
     * @param chineseCharacter
     * @return
     */
    public static boolean checkChineseCharacter(String chineseCharacter) {
        return Pattern.matches(CHINESE_CHARACTER, chineseCharacter);
    }

    //IP
    public static final String IP = "/^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\" +
            ".){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/";

    /**
     * 校验IP
     *
     * @param ip
     * @return
     */
    public static boolean checkIp(String ip) {
        return Pattern.matches(IP, ip);
    }

    //空白符
    public static final String BLANK = "\\n\\s*\\r";

    /**
     * 校验空白字符
     *
     * @param blank
     * @return
     */
    public static boolean checkBlank(String blank) {
        return Pattern.matches(BLANK, blank);
    }

    //邮件
    public static final String EMAIL = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";

    /**
     * 校验邮件
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        return Pattern.matches(EMAIL, email);
    }

    //URL
    public static final String URL = "[a-zA-z]+://[^\\s]*";

    /**
     * 校验Url
     *
     * @param url
     * @return
     */
    public static boolean checkUrl(String url) {
        return Pattern.matches(URL, url);
    }

    //QQ 以1到9开头 5-10位
    public static final String QQ = "^[1-9][0-9]{4,9}";

    /**
     * 校验QQ号
     *
     * @param qq
     * @return
     */
    public static boolean checkQQ(String qq) {
        return Pattern.matches(QQ, qq);

    }

    //国内邮编
    public static final String POST_CODE = "[1-9]\\d{5}";

    /**
     * 校验邮编
     *
     * @param postCode
     * @return
     */
    public static boolean checkPostCode(String postCode) {
        return Pattern.matches(POST_CODE, postCode);
    }

    //身份证
    public static final String ID_CARD = "\\d{15}(\\d\\d[0-9xX])?";

    /**
     * 校验身份证
     *
     * @param idCard
     * @return
     */
    public static boolean checkIdCard(String idCard) {
        return Pattern.matches(ID_CARD, idCard);
    }

    /**
     * 自定义校验
     *
     * @param pattern
     * @param checkStr
     * @return
     */
    public static boolean check(String pattern, String checkStr) {
        return Pattern.matches(pattern, checkStr);
    }

}
