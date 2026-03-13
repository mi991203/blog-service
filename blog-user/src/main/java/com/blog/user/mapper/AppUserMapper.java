package com.blog.user.mapper;

import com.blog.user.entity.AppUser;
import com.blog.user.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户Mapper
 */
@Mapper
public interface AppUserMapper {

    /**
     * 根据ID查询用户
     */
    AppUser selectById(@Param("id") Long id);

    /**
     * 根据用户名查询用户
     */
    AppUser selectByUsername(@Param("username") String username);

    /**
     * 插入用户
     */
    int insert(AppUser user);

    /**
     * 更新用户
     */
    int updateById(UserVO user);

    /**
     * 根据ID删除用户
     */
    int deleteById(@Param("id") Long id);

    /**
     * 根据手机号查询用户
     */
    AppUser selectByMobile (@Param("mobile") String mobile);
}