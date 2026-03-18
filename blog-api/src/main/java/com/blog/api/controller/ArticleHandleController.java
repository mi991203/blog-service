package com.blog.api.controller;

import com.blog.common.result.Result;
import com.blog.user.dto.LikeDto;
import com.blog.user.service.ArticleFavoriteService;
import com.blog.user.service.LikeArticleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/api/article")
@Api(tags = "文章点赞/收藏/取消点赞")
public class ArticleHandleController {
    @Resource
    private ArticleFavoriteService articleFavoriteService;
    @Resource
    private LikeArticleService likeArticleService;

    /**
     * 点赞取消文章
     *
     * @param likeDto
     * @return 点赞结果
     */
    @ApiOperation(value = "点赞文章/取消点赞", notes = "用户点赞文章/用户取消点赞")
    @PostMapping("/like")
    public Result<Boolean> likeArticle(@RequestBody LikeDto likeDto) {
        Integer userId = likeDto.getUserId();
        Integer articleId = likeDto.getArticleId();
        Integer num = likeDto.getNum();
        if (userId == null || articleId == null || num == null) {
            return Result.error("参数错误");
        }
        log.info("用户{}点赞文章{}", userId, articleId, num);
        boolean result =  likeArticleService.likeArticle((long)userId, (long)articleId, num);
        return Result.success(result);
    }
}
