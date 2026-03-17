package com.blog.user.entity;

import java.util.Date;

import lombok.Data;

@Data
public class ArticleLike {
    private Long id;
    private Long userId;
    private Long articleId;
    private Date createTime;
    // getters and setters
}
