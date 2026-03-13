package com.blog.user.service;

/**
 * 验证码服务接口
 */
public interface VerifyCodeService {
    /**
     * 生成图形验证码（使用手机号和用户名作为key）
     * @return Base64编码的图片字符串
     */
    String generateCaptchaImage();

    /**
     * 验证验证码（使用手机号和用户名作为key）
     * @param code 用户输入的验证码
     * @return 是否验证通过
     */
    boolean validateVerifyCode( String code);

    /**
     * 删除验证码（使用手机号和用户名作为key）
     * @param code 验证码
     */
    void removeVerifyCode(String code);
}
