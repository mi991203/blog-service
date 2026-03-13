package com.blog.user.service.impl;

import com.blog.common.utils.CaptchaUtil;
import com.blog.user.service.VerifyCodeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

@Service
public class VerifyCodeServiceImpl implements VerifyCodeService {
    @Resource
    private CaptchaUtil captchaUtil;

    @Override
    public String generateCaptchaImage() {
        // 生成验证码
        String code = captchaUtil.generateCaptchaCode();
        try {
            // 生成验证码图片
            String imageBase64 = captchaUtil.generateCaptchaImage(code);
            // 保存验证码到Redis，有效期1分钟
            captchaUtil.saveVerifyCode(code, code);
            return imageBase64;
        } catch (IOException e) {
            throw new RuntimeException("生成验证码图片失败", e);
        }
    }

    @Override
    public boolean validateVerifyCode(String code) {
        boolean result = captchaUtil.validateVerifyCode(code, code);
        if (result) {
            // 验证成功后删除验证码，防止重复使用
            captchaUtil.removeVerifyCode(code);
        }
        return result;
    }

    @Override
    public void removeVerifyCode(String code) {
        captchaUtil.removeVerifyCode(code);
    }
}
