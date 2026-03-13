package com.blog.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

//用户验证码效验DTO
@Data
@ApiModel(description = "用户验证码效验数据传输对象")
public class VerifyCodeDTO {
    /**
     * 验证码
     */
    @ApiModelProperty(value = "验证码", required = true, example = "abcd")
    private String code;
}
