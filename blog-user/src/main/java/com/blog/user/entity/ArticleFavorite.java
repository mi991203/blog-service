package com.blog.user.entity;

import lombok.Data;

import java.util.Date;

// 文章收藏实体类
@Data
public class ArticleFavorite {
    private Long id;
    private Long userId;
    private Long articleId;
    private Date createTime;
}
