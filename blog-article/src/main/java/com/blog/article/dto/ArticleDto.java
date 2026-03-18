package com.blog.article.dto;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "文章传输对象")
public class ArticleDto {
    @ApiModelProperty(value = "文章ID")
    private Integer id;

    @ApiModelProperty(value = "文章标题")
    @NotBlank(message = "文章标题不能为空")
    private String title;

    @ApiModelProperty(value = "文章内容")
    @NotBlank(message = "文章内容不能为空")
    private String content;

    @ApiModelProperty(value = "文章分类ID")
    @NotBlank(message = "文章分类ID不能为空")
    private String categoryId;

    @ApiModelProperty(value = "文章封面图")
    private String articleCover;

    @ApiModelProperty(value = "是否热门")
    private Boolean isHot;

    @ApiModelProperty(value = "是否置顶")
    private Integer isTop;
    
    
}
