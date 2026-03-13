package com.blog.api.controller;

import com.blog.common.constant.Constants;
import com.blog.common.result.Result;
import com.blog.common.utils.JwtUtils;
import com.blog.user.dto.LoginDTO;
import com.blog.user.dto.RegisterDTO;
import com.blog.user.service.UserService;
import com.blog.user.vo.LoginVO;
import com.blog.user.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/api/user")
@Api(tags = "用户管理接口")
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 用户注册
     */
    @PostMapping("/register")
    @ApiOperation(value = "用户注册", notes = "用户注册接口，需要提供用户名、密码、昵称等信息")
    public Result<Void> register(@Validated @RequestBody @ApiParam(value = "注册信息", required = true) RegisterDTO registerDTO) {
        log.info("用户注册请求，用户名：{}", registerDTO.getNickname());
        userService.register(registerDTO);
        log.info("用户注册成功，用户名：{}", registerDTO.getNickname());
        return Result.success("注册成功", null);
    }

    @PostMapping("/login")
    @ApiOperation(value = "用户登录", notes = "用户登录接口，登录成功后返回JWT Token")
    public Result<LoginVO> login(@Validated @RequestBody LoginDTO loginDTO) {
        LoginVO loginVO = userService.login(loginDTO);
        return Result.success("登录成功", loginVO);
    }

    /**
     * 用户登出
     */
    @PostMapping("/logout")
    @ApiOperation(value = "用户登出", notes = "用户登出接口，清除JWT Token")
    public Result<Void> logout(@ApiParam(value = "HTTP请求对象，用于获取Token") HttpServletRequest request) {
        String token = request.getHeader(Constants.TOKEN_HEADER);
        if (token != null && token.startsWith(Constants.TOKEN_PREFIX)) {
            token = token.substring(Constants.TOKEN_PREFIX.length());
        }

        // 从Redis中移除token
        if (token != null) {
            JwtUtils.removeToken(token);
        }

        return Result.success("登出成功", null);
    }

    /**
     * 获取当前用户信息
     */
    @GetMapping("/info")
    @ApiOperation(value = "获取当前用户信息", notes = "获取当前登录用户的信息")
    public Result<UserVO> getUserInfo(@ApiParam(value = "HTTP请求对象，用于获取用户ID") HttpServletRequest request) {
        Long userId = (Long) request.getAttribute(Constants.USER_ID);
        log.info("获取用户信息，用户ID：{}", userId);
        UserVO userVO = userService.getUserInfo(userId);
        return Result.success(userVO);
    }

    /**
     * 根据用户ID获取用户信息
     */
    @GetMapping("/info/{userId}")
    @ApiOperation(value = "根据用户ID获取用户信息", notes = "根据用户ID获取指定用户的信息")
    public Result<UserVO> getUserInfoById(@PathVariable @ApiParam(value = "用户ID", required = true) Long userId) {
        log.info("根据ID获取用户信息，用户ID：{}", userId);
        UserVO userVO = userService.getUserInfo(userId);
        return Result.success(userVO);
    }
    /**
     * 修改用户信息
     * */
    @PostMapping("/update/{userId}")
    @ApiOperation(value = "修改用户信息", notes = "修改用户信息")
    public Result<Void> updateUserInfo(@PathVariable @ApiParam(value = "用户ID", required = true) Long userId,
                                       @Validated @RequestBody @ApiParam(value = "用户信息", required = true) UserVO userVO) {
        log.info("修改用户信息，用户ID：{}", userId);
        userVO.setId(String.valueOf(userId));
        boolean result = userService.updateUser(userVO);
        if (!result) {
            return Result.error("修改用户信息失败");
        }
        return Result.success("修改用户信息成功", null);
    }
}
