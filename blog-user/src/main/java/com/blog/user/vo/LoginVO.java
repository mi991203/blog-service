package com.blog.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 登录结果VO
 */
@Data
@ApiModel(value = "LoginVO", description = "登录结果VO")
public class LoginVO {
    private static final long serialVersionUID = 1L;

    /**
     * 用户信息
     */
    @ApiModelProperty(value = "用户信息")
    private UserVO user;

    /**
     * JWT Token
     */
    @ApiModelProperty(value = "JWT Token")
    private String token;
}
