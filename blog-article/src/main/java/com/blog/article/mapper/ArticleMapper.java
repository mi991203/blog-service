package com.blog.article.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.blog.article.dto.LikeCountDto;
import com.blog.article.entity.Article;

@Mapper
public interface ArticleMapper {
    int insert(Article article);

    int handleLikeCount(LikeCountDto likeCountDto);
}
