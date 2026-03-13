package com.blog.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/***
 * 用户登录DTO
 */
@Data
@ApiModel(description = "用户登录数据传输对象")
public class LoginDTO {
    private static final long serialVersionUID = 1L;

    // 手机号
    @NotBlank(message = "手机号不可以为空")
    @ApiModelProperty(value = "手机号", required = true, example = "admin")
    private String mobile;

    // 用户输入的验证码
    @NotBlank(message = "验证码不能为空")
    @ApiModelProperty(value = "验证码", required = true, example = "abcd")
    private String captchaCode;
}
