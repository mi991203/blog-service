package com.blog.api.controller;

import com.blog.common.result.Result;
import com.blog.user.dto.VerifyCodeDTO;
import com.blog.user.service.VerifyCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 验证码Controller
 */
@Slf4j
@RestController
@RequestMapping("/api/verify-code")
@Api(tags = "验证码管理接口")
public class VerifyCodeController {

    @Resource
    private VerifyCodeService verifyCodeService;

    /**
     * 获取图形验证码（使用手机号和用户名作为key）
     * @return Base64编码的验证码图片
     */
    @GetMapping("/captcha")
    @ApiOperation(value = "获取图形验证码", notes = "获取Base64编码的图形验证码图片")
    public Result<String> generateCaptchaImage() {

        String imageBase64 = verifyCodeService.generateCaptchaImage();
//        拼接base64 图片
        imageBase64 = "data:image/png;base64," + imageBase64;
        log.info("图形验证码生成成功");
        return Result.success("图形验证码获取成功", imageBase64);
    }



    /**
     * 验证验证码（使用手机号和用户名作为key）
     * @return 是否验证通过
     */
    @PostMapping("/validate")
    @ApiOperation(value = "验证验证码", notes = "验证用户输入的验证码是否正确")
    public Result<Boolean> validateVerifyCode(@RequestBody @ApiParam(value = "验证码信息", required = true) VerifyCodeDTO verifyCodeDTO) {

        String code = verifyCodeDTO.getCode();
        log.info("验证验证码请求，code: {}", code);
        boolean result = verifyCodeService.validateVerifyCode(code);
        log.info("验证码验证完成，result: {}", result);
        return Result.success("验证码验证完成", result);
    }



    /**
     * 删除验证码
     * @param Code 验证码
     * @return 操作结果
     */
    @DeleteMapping("/remove")
    @ApiOperation(value = "删除验证码", notes = "根据验证码内容删除对应的验证码记录")
    public Result<Void> removeVerifyCode(@RequestParam("Code") @ApiParam(value = "验证码", required = true) String Code) {
        verifyCodeService.removeVerifyCode(Code);
        log.info("验证码删除成功: {}", Code);
        return Result.success("验证码删除成功", null);
    }
}