package com.blog.user.service;

public interface ArticleFavoriteService {
    boolean favoriteArticle(Long userId, Long articleId, int num);
    boolean getInteractionStatus(Long userId, Long articleId);
}
