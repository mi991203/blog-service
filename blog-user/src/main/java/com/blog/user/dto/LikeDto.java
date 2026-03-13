package com.blog.user.dto;

import lombok.Data;

@Data
public class LikeDto {
    Integer userId;
    Integer articleId;
    Integer num;
}