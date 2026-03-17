package com.blog.user.service;

public interface LikeArticleService {
    boolean likeArticle(Long userId, Long articleId, int num);
}
