package com.blog.article.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 文章实体类
 */
@Data
@ApiModel(description = "文章实体")
public class Article {
    /**
     * 文章ID
     */
    @ApiModelProperty(value = "文章ID")
    private Integer id;

    /**
     * 文章标题
     */
    @ApiModelProperty(value = "文章标题")
    private String title;

    /**
     * 文章内容
     */
    @ApiModelProperty(value = "文章内容")
    private String content;

    /**
     * 分类ID，以逗号分隔的字符串
     */
    @ApiModelProperty(value = "分类ID", notes = "以逗号分隔的字符串")
    private String categoryId;


    /**
     * 文章封面
     */
    @ApiModelProperty(value = "文章封面")
    private String articleCover;

    /**
     * 阅读数量
     */
    @ApiModelProperty(value = "阅读数量")
    private Integer readCounts;

    /**
     * 评论数量
     */
    @ApiModelProperty(value = "评论数量")
    private Integer commentCounts;

    /**
     * 是否删除 0:未删除 1:已删除
     */
    @ApiModelProperty(value = "是否删除", allowableValues = "0,1")
    private Integer isDelete;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    /**
     * 是否热门 0:否 1:是
     */
    @ApiModelProperty(value = "是否热门", allowableValues = "0,1")
    private Integer isHot;

    /**
     * 是否置顶 0:否 1:是
     */
    @ApiModelProperty(value = "是否置顶", allowableValues = "0,1")
    private Integer isTop;
    /**
     * 点赞数
     */
    @ApiModelProperty(value = "点赞数")
    private Integer likeCount;
    /**
     * 收藏数
     */
    @ApiModelProperty(value = "收藏数")
    private Integer favoriteCount;
}