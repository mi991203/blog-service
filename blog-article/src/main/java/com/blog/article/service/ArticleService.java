package com.blog.article.service;

import com.blog.article.dto.LikeCountDto;
import com.blog.article.entity.Article;

public interface ArticleService {
    boolean addArticle(Article article);

    /**
     * 文章点赞数+1
     * @param likeCountDto 
     * @return 是否成功
     */
    int handleLikeCount(LikeCountDto likeCountDto);
}
