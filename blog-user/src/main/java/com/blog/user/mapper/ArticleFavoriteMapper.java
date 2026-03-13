package com.blog.user.mapper;

import com.blog.user.entity.ArticleFavorite;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;
@Mapper
public interface ArticleFavoriteMapper {
    // 收藏
    int insert(ArticleFavorite articleFavorite);

    // 取消收藏
    int deleteByUserAndArticle(@Param("userId") Long userId, @Param("articleId") Long articleId);

    // 检查是否已收藏
    ArticleFavorite selectByUserAndArticle(@Param("userId") Long userId, @Param("articleId") Long articleId);

    // 获取文章收藏数
    int countByArticleId(@Param("articleId") Long articleId);
}
