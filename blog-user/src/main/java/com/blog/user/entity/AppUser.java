package com.blog.user.entity;

import lombok.Data;

import java.util.Date;
@Data
public class AppUser {
    private Integer id;

    private String mobile;

    private String nickname;

    private String face;

    private String realname;

    private String email;

    private Integer sex;

    private Date birthday;

    private String province;

    private String city;

    private String district;

    private Integer activeStatus;

    private Integer totalIncome;

    private Date createdTime;

    private Date updatedTime;


}
