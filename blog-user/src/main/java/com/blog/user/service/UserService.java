package com.blog.user.service;

import com.blog.user.dto.LoginDTO;
import com.blog.user.dto.RegisterDTO;
import com.blog.user.entity.AppUser;
import com.blog.user.vo.LoginVO;
import com.blog.user.vo.UserVO;

public interface UserService {
    /**
     * 用户注册
     */
    boolean register(RegisterDTO registerDTO);

    /**
     * 用户登录
     */
    LoginVO login(LoginDTO loginDTO);

    AppUser getUerByMobile(String mobile);

    /**
     * 根据ID查询用户
     */
    AppUser getUserById(Long id);

    /**
     * 获取用户信息
     */
    UserVO getUserInfo(Long userId);

    boolean updateUser(UserVO user);
}
