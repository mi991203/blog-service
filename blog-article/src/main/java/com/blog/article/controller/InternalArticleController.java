package com.blog.article.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.article.dto.LikeCountDto;
import com.blog.article.service.ArticleService;
import com.blog.common.result.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/article/comment")
@Api(tags = "内部文章接口")
public class InternalArticleController {
    @Resource
    private ArticleService articleService;

    @PostMapping("/favorite/handle")
    @ApiOperation(value = "处理文章收藏数", notes = "用于服务间通信，处理文章收藏数")
    @ApiResponses({
            @ApiResponse(code = 200, message = "操作成功"),
            @ApiResponse(code = 400, message = "参数错误"),
            @ApiResponse(code = 500, message = "服务器内部错误")
    })
    public Result<Boolean> handleLikeCount(@RequestBody LikeCountDto likeCountDto) {
        log.info("处理文章点赞数: {}", likeCountDto);
        // 添加空值检查
        if (likeCountDto == null){
            log.error("likeCountDto不能为空");
            return Result.error("likeCountDto不能为空");
        }
        if (likeCountDto.getId() == null){
            log.error("文章ID不能为空");
            return Result.error("文章ID不能为空");
        }
        
        int result =articleService.handleLikeCount(likeCountDto);
        return Result.success(result > 0);
    }
}
