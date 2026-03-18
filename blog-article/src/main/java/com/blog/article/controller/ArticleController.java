package com.blog.article.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.article.dto.ArticleDto;
import com.blog.article.entity.Article;
import com.blog.article.service.ArticleService;
import com.blog.common.result.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("admin/article")
@Api(tags = "文章管理接口")
public class ArticleController {
    @Resource
    private ArticleService articleService;

    @PostMapping("/add")
    @ApiOperation(value = "添加文章", notes = "添加文章")
    public Result<Boolean> addArticle(@Valid @RequestBody ArticleDto articleDto) {
        Article article = new Article();
        article.setTitle(articleDto.getTitle());
        article.setContent(articleDto.getContent());
        article.setCategoryId(articleDto.getCategoryId());
        article.setArticleCover(articleDto.getArticleCover());
        articleService.addArticle(article);

        return Result.success(true);
    }

    
}
