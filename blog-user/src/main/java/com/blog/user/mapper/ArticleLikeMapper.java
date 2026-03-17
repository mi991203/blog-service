package com.blog.user.mapper;

import com.blog.user.entity.ArticleLike;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

@Mapper
public interface ArticleLikeMapper {
    // 点赞
    int insert(ArticleLike articleLike);

    // 取消点赞
    int deleteByUserAndArticle(@Param("userId") Long userId, @Param("articleId") Long articleId);

    // 检查是否已点赞
    ArticleLike selectByUserAndArticle(@Param("userId") Long userId, @Param("articleId") Long articleId);

    // 获取文章点赞数
    int countByArticleId(@Param("articleId") Long articleId);
    
}
