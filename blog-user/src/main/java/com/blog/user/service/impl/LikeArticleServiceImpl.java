package com.blog.user.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.blog.user.dto.LikeCountDto;
import com.blog.user.entity.ArticleLike;
import com.blog.user.mapper.ArticleLikeMapper;
import com.blog.user.service.LikeArticleService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LikeArticleServiceImpl implements LikeArticleService {
    @Resource
    private ArticleLikeMapper articleLikeMapper;

    @Override
    public boolean likeArticle(Long userId, Long articleId, int num) {
        LikeCountDto dto = new LikeCountDto();
        dto.setId(articleId);
        // 检查是否已点赞
        ArticleLike existingLike = articleLikeMapper.selectByUserAndArticle(userId, articleId);
        if (existingLike != null) {
            // 已点赞，取消点赞
            // 删除点赞记录
            int deleteResult = articleLikeMapper.deleteByUserAndArticle(userId, articleId);
            if (deleteResult > 0) {
                dto.setLikeCount((long) (num - 1));
                articleServiceClient.handleLikeCount(dto);
            }
            return deleteResult > 0;
        }

        // 添加点赞记录
        ArticleLike articleLike = new ArticleLike();
        articleLike.setUserId(userId);
        articleLike.setArticleId(articleId);
        articleLike.setCreateTime(new Date());

        int result = articleLikeMapper.insert(articleLike);
        log.info("用户{}点赞文章{}", result);
        // 更新文章点赞数
        if (result > 0) {
            dto.setLikeCount((long) (num + 1));
            articleServiceClient.handleLikeCount(dto);
        }

        return result > 0;
    }
}
