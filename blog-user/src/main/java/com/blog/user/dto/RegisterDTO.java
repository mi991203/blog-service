package com.blog.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 用户注册DTO
 */
@Data
@ApiModel(description = "用户注册数据传输对象")
public class RegisterDTO {


    /**
     * 用户名（昵称）
     */
    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名（昵称）", required = true, example = "张三")
    private String nickname;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    @ApiModelProperty(value = "手机号", required = true, example = "13800138000")
    private String mobile;


    /**
     * 用户输入的验证码
     */
    @NotBlank(message = "验证码不能为空")
    @ApiModelProperty(value = "验证码", required = true, example = "abcd")
    private String captchaCode;
}
