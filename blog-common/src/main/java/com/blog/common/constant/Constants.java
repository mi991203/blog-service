package com.blog.common.constant;

/**
 * 常量类
 */
public class Constants {

    /**
     * JWT token 前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * JWT token header名称
     */
    public static final String TOKEN_HEADER = "Authorization";

    /**
     * 用户ID
     */
    public static final String USER_ID = "userId";

    /**
     * 用户名
     */
    public static final String USERNAME = "username";

    /**
     * 用户状态：正常
     */
    public static final Integer USER_STATUS_NORMAL = 1;

    /**
     * 用户状态：禁用
     */
    public static final Integer USER_STATUS_DISABLED = 0;

    /**
     *  分类标签
     */
    public static final String CATEGORY_TAG_LIST = "category_tag_list";

    /**
     *  分类标签的过期时间永久
     */
    public static final Long CATEGORY_TAG_LIST_EXPIRE_TIME = -1L;
}
