package com.blog.user.service.impl;

import com.blog.user.dto.FavoriteCountDto;
import com.blog.user.entity.ArticleFavorite;
import com.blog.user.mapper.ArticleFavoriteMapper;
import com.blog.user.service.ArticleFavoriteService;
import com.blog.user.service.ArticleServiceClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class ArticleFavoriteServiceImpl implements ArticleFavoriteService {
    @Resource
    private ArticleFavoriteMapper articleFavoriteMapper;

    @Resource
    private ArticleServiceClient articleServiceClient;

    /**
     * 收藏取消收藏
     */
    @Transactional
    @Override
    public boolean favoriteArticle(Long userId, Long articleId, int num) {
        FavoriteCountDto dto = new FavoriteCountDto();
        dto.setId(articleId);
        // 检查是否已收藏
        ArticleFavorite articleFavorite = articleFavoriteMapper.selectByUserAndArticle(userId, articleId);
        if (articleFavorite != null) {
            // 已点赞，取消收藏
            // 删除搜藏记录
            int deleteResult = articleFavoriteMapper.deleteByUserAndArticle(userId, articleId);
            if (deleteResult > 0) {
                dto.setFavoriteCount((long) (num - 1));
                articleServiceClient.handleFavoriteCount(dto);
            }
            return deleteResult > 0;
        }
        // 添加点赞记录
        ArticleFavorite favorite = new ArticleFavorite();
        favorite.setUserId(userId);
        favorite.setArticleId(articleId);
        favorite.setCreateTime(new Date());

        int result = articleFavoriteMapper.insert(favorite);

        // 更新文章点赞数
        if (result > 0) {

            dto.setFavoriteCount((long) num + 1);
            articleServiceClient.handleFavoriteCount(dto);
        }

        return result > 0;
    }

    @Override
    public boolean getInteractionStatus(Long userId, Long articleId) {
        return false;
    }
}
