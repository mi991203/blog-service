package com.blog.user.service.impl;

import com.blog.common.exception.BusinessException;
import com.blog.common.utils.JwtUtils;
import com.blog.user.dto.LoginDTO;
import com.blog.user.dto.RegisterDTO;
import com.blog.user.entity.AppUser;
import com.blog.user.mapper.AppUserMapper;
import com.blog.user.service.UserService;
import com.blog.user.service.VerifyCodeService;
import com.blog.user.vo.LoginVO;
import com.blog.user.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private AppUserMapper appUserMapper;

    @Resource
    private VerifyCodeService verifyCodeService;

    @Override
    public boolean register(RegisterDTO registerDTO) {
        log.info("开始注册用户，昵称：{}", registerDTO.getNickname());

        // 获取验证码
        String captchaKey = registerDTO.getCaptchaCode();

        // 验证验证码
        if (!verifyCodeService.validateVerifyCode(captchaKey)) {
            log.warn("注册验证码验证失败，key：{}", captchaKey);
            throw new BusinessException("验证码错误");
        }

        // 检查用户是否已存在
        AppUser existUser = getUerByMobile(registerDTO.getMobile());
        if (existUser != null) {

            throw new BusinessException("手机号已存在");
        }

        // 创建用户
        AppUser user = new AppUser();
        user.setMobile(registerDTO.getMobile());
        user.setNickname(registerDTO.getNickname());
        user.setRealname(registerDTO.getNickname());
        user.setActiveStatus(1); // 激活状态
        user.setTotalIncome(0); // 初始收入为0


        // 注意：密码字段在AppUser中不存在，需要根据实际情况处理
        // 如果需要密码字段，需要在AppUser中添加password字段

        boolean result = appUserMapper.insert(user) > 0;
        log.info("用户注册完成，昵称：{}，结果：{}", registerDTO.getNickname(), result);
        return result;
    }

    @Override
    public LoginVO login(LoginDTO loginDTO) {
        // 获取验证码
        String captchaKey = loginDTO.getCaptchaCode();

        // 验证验证码
        if (!verifyCodeService.validateVerifyCode(captchaKey)) {
            throw new BusinessException("验证码错误");
        }

        // 查询用户
        AppUser user = getUerByMobile(loginDTO.getMobile());
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        // 注意：由于AppUser中没有密码字段，这里无法进行密码验证
        // 需要根据实际情况决定是否在AppUser中添加密码字段

        // 检查用户状态
        if (user.getActiveStatus() == 0) {
            throw new BusinessException("用户已被禁用");
        }

        // 生成token (注意：这里需要将String类型的ID转换为Long类型)
        Long userId = null;
        try {
            userId = Long.valueOf(user.getId());
        } catch (NumberFormatException e) {
            userId = 0L; // 默认值
        }
        String token = "Bearer "+ JwtUtils.generateToken(userId, user.getMobile(), user.getNickname());
        log.debug("生成JWT Token，用户ID：{}，用户名：{}", userId, user.getNickname());

        // 构建返回结果
        LoginVO loginVO = new LoginVO();
        UserVO userVO = new UserVO();
        // 注意：UserVO需要根据AppUser的字段进行调整
        userVO.setNickname(user.getNickname());
        userVO.setRealname(user.getNickname());
        userVO.setEmail(user.getEmail());
        userVO.setMobile(user.getMobile());
        userVO.setId(String.valueOf(userId));
        loginVO.setUser(userVO);
        loginVO.setToken(token);

        return loginVO;
    }

    @Override
    public AppUser getUerByMobile(String mobile) {
        return appUserMapper.selectByMobile(mobile);
    }

    @Override
    public AppUser getUserById(Long id) {
        return appUserMapper.selectById(id);
    }

    @Override
    public UserVO getUserInfo(Long userId) {
        AppUser user = getUserById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        UserVO userVO = new UserVO();
        // 将String类型的ID转换为Long类型
        Long id = null;
        try {
            id = Long.valueOf(user.getId());
        } catch (NumberFormatException e) {
            id = 0L; // 默认值
        }

        userVO.setNickname(user.getNickname());
        userVO.setRealname(user.getNickname());
        userVO.setEmail(user.getEmail());
        userVO.setMobile(user.getMobile());
        return userVO;
    }

    @Override
    public boolean updateUser(UserVO user) {
        user.setUpdatedTime(new Date());
        return appUserMapper.updateById(user) > 0;
    }
}
