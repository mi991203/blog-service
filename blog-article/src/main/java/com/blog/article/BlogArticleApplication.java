package com.blog.article;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.blog.article", "com.blog.common"})
@MapperScan("com.blog.article.mapper")
public class BlogArticleApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogArticleApplication.class, args);
    }
}
