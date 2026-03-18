package com.blog.article.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.article.dto.LikeCountDto;
import com.blog.article.entity.Article;
import com.blog.article.mapper.ArticleMapper;
import com.blog.article.service.ArticleService;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Resource
    private ArticleMapper articleMapper;

    @Override
    public int handleLikeCount(LikeCountDto likeCountDto) {
        return articleMapper.handleLikeCount(likeCountDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addArticle(Article article) {
        article.setCreateTime(new Date());
        article.setUpdateTime(new Date());
        // 默认值设置
        if (article.getReadCounts() == null) {
            article.setReadCounts(0);
        }
        if (article.getCommentCounts() == null) {
            article.setCommentCounts(0);
        }
        if (article.getIsDelete() == null) {
            article.setIsDelete(0);
        }
        if (article.getIsHot() == null) {
            article.setIsHot(0);
        }
        if (article.getIsTop() == null) {
            article.setIsTop(0);
        }
        return articleMapper.insert(article) > 0;
    }
}
