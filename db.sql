-- ============================================================
-- Blog Service - 完整数据库建表脚本
-- 数据库版本: MySQL 8.0+
-- 字符集: utf8mb4
-- ============================================================

-- 创建数据库
CREATE DATABASE IF NOT EXISTS `blog` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE `blog`;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table: app_user（前台用户表）
-- 模块：blog-user
-- ----------------------------
DROP TABLE IF EXISTS `app_user`;
CREATE TABLE `app_user` (
                            `id`            BIGINT       NOT NULL AUTO_INCREMENT COMMENT '用户ID',
                            `mobile`        VARCHAR(20)  DEFAULT NULL COMMENT '手机号',
                            `nickname`      VARCHAR(50)  DEFAULT NULL COMMENT '昵称',
                            `face`          VARCHAR(255) DEFAULT NULL COMMENT '头像URL',
                            `realname`      VARCHAR(50)  DEFAULT NULL COMMENT '真实姓名',
                            `email`         VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
                            `sex`           TINYINT      DEFAULT NULL COMMENT '性别 0:未知 1:男 2:女',
                            `birthday`      DATE         DEFAULT NULL COMMENT '生日',
                            `province`      VARCHAR(50)  DEFAULT NULL COMMENT '省份',
                            `city`          VARCHAR(50)  DEFAULT NULL COMMENT '城市',
                            `district`      VARCHAR(50)  DEFAULT NULL COMMENT '区县',
                            `active_status` TINYINT      NOT NULL DEFAULT 1 COMMENT '激活状态 0:禁用 1:正常',
                            `total_income`  INT          NOT NULL DEFAULT 0 COMMENT '总收入（积分）',
                            `created_time`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `updated_time`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            PRIMARY KEY (`id`) USING BTREE,
                            UNIQUE KEY `uk_mobile` (`mobile`) USING BTREE,
                            KEY `idx_active_status` (`active_status`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='前台用户表';

-- ----------------------------
-- Table: admin_user（后台管理员表）
-- 模块：blog-admin-user
-- ----------------------------
DROP TABLE IF EXISTS `admin_user`;
CREATE TABLE `admin_user` (
                              `id`           INT          NOT NULL AUTO_INCREMENT COMMENT '管理员ID',
                              `username`     VARCHAR(50)  NOT NULL COMMENT '登录用户名',
                              `password`     VARCHAR(100) NOT NULL COMMENT '登录密码（MD5加密）',
                              `admin_name`   VARCHAR(50)  DEFAULT NULL COMMENT '管理员姓名',
                              `created_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              `updated_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                              PRIMARY KEY (`id`) USING BTREE,
                              UNIQUE KEY `uk_username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='后台管理员表';

-- ----------------------------
-- Table: banner（首页轮播图表）
-- 模块：blog-admin-user
-- ----------------------------
DROP TABLE IF EXISTS `banner`;
CREATE TABLE `banner` (
                          `id`          INT          NOT NULL AUTO_INCREMENT COMMENT '轮播图ID',
                          `name`        VARCHAR(100) NOT NULL COMMENT '轮播图名称',
                          `description` VARCHAR(255) DEFAULT NULL COMMENT '轮播图描述',
                          `url`         VARCHAR(500) DEFAULT NULL COMMENT '点击跳转链接',
                          `image`       VARCHAR(500) NOT NULL COMMENT '图片地址',
                          PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='首页轮播图表';

-- ----------------------------
-- Table: category（文章分类表）
-- 模块：blog-admin-user
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
                            `id`        INT         NOT NULL AUTO_INCREMENT COMMENT '分类ID',
                            `name`      VARCHAR(50) NOT NULL COMMENT '分类名称',
                            `tag_color` VARCHAR(20) DEFAULT NULL COMMENT '标签颜色（如 #FF5733）',
                            PRIMARY KEY (`id`) USING BTREE,
                            UNIQUE KEY `uk_name` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文章分类表';

-- ----------------------------
-- Table: article（文章表）
-- 模块：blog-article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
                           `id`             INT          NOT NULL AUTO_INCREMENT COMMENT '文章ID',
                           `title`          VARCHAR(200) NOT NULL COMMENT '文章标题',
                           `content`        LONGTEXT     DEFAULT NULL COMMENT '文章正文（支持 Markdown）',
                           `category_id`    VARCHAR(100) DEFAULT NULL COMMENT '分类ID，多个用逗号分隔',
                           `article_cover`  VARCHAR(500) DEFAULT NULL COMMENT '文章封面图URL',
                           `read_counts`    INT          NOT NULL DEFAULT 0 COMMENT '阅读数',
                           `comment_counts` INT          NOT NULL DEFAULT 0 COMMENT '评论数',
                           `like_count`     INT          NOT NULL DEFAULT 0 COMMENT '点赞数',
                           `favorite_count` INT          NOT NULL DEFAULT 0 COMMENT '收藏数',
                           `is_hot`         TINYINT      NOT NULL DEFAULT 0 COMMENT '是否热门 0:否 1:是',
                           `is_top`         TINYINT      NOT NULL DEFAULT 0 COMMENT '是否置顶 0:否 1:是',
                           `is_delete`      TINYINT      NOT NULL DEFAULT 0 COMMENT '是否删除 0:未删除 1:已删除',
                           `create_time`    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           `update_time`    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                           PRIMARY KEY (`id`) USING BTREE,
                           KEY `idx_is_delete` (`is_delete`) USING BTREE,
                           KEY `idx_is_top`    (`is_top`) USING BTREE,
                           KEY `idx_is_hot`    (`is_hot`) USING BTREE,
                           KEY `idx_create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文章表';

-- ----------------------------
-- Table: comment（文章评论表）
-- 模块：blog-user
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
                           `id`               BIGINT   NOT NULL AUTO_INCREMENT COMMENT '评论ID',
                           `article_id`       BIGINT   NOT NULL COMMENT '文章ID',
                           `user_id`          BIGINT   NOT NULL COMMENT '评论用户ID',
                           `parent_id`        BIGINT   DEFAULT NULL COMMENT '父评论ID（NULL 表示根评论）',
                           `content`          TEXT     NOT NULL COMMENT '评论内容',
                           `reply_to_user_id` BIGINT   DEFAULT NULL COMMENT '被回复的用户ID',
                           `create_time`      DATETIME NOT NULL COMMENT '创建时间',
                           `is_delete`        TINYINT  NOT NULL DEFAULT 0 COMMENT '是否删除 0:未删除 1:已删除',
                           PRIMARY KEY (`id`) USING BTREE,
                           KEY `idx_article_id` (`article_id`) USING BTREE,
                           KEY `idx_user_id`    (`user_id`) USING BTREE,
                           KEY `idx_parent_id`  (`parent_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文章评论表' ROW_FORMAT=Dynamic;

-- ----------------------------
-- Table: article_like（文章点赞表）
-- 模块：blog-user
-- ----------------------------
DROP TABLE IF EXISTS `article_like`;
CREATE TABLE `article_like` (
                                `id`          BIGINT   NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                `user_id`     BIGINT   NOT NULL COMMENT '用户ID',
                                `article_id`  BIGINT   NOT NULL COMMENT '文章ID',
                                `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '点赞时间',
                                PRIMARY KEY (`id`) USING BTREE,
                                UNIQUE KEY `uk_user_article` (`user_id`, `article_id`) USING BTREE,
                                KEY `idx_article_id` (`article_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文章点赞表';

-- ----------------------------
-- Table: article_favorite（文章收藏表）
-- 模块：blog-user
-- ----------------------------
DROP TABLE IF EXISTS `article_favorite`;
CREATE TABLE `article_favorite` (
                                    `id`          BIGINT   NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                    `user_id`     BIGINT   NOT NULL COMMENT '用户ID',
                                    `article_id`  BIGINT   NOT NULL COMMENT '文章ID',
                                    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '收藏时间',
                                    PRIMARY KEY (`id`) USING BTREE,
                                    UNIQUE KEY `uk_user_article` (`user_id`, `article_id`) USING BTREE,
                                    KEY `idx_article_id` (`article_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文章收藏表';

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- 初始化数据
-- ----------------------------

-- 默认管理员账号（密码：admin123，MD5加密后的值，请部署后自行修改）
INSERT INTO `admin_user` (`username`, `password`, `admin_name`) VALUES
    ('admin', '0192023a7bbd73250516f069df18b500', '超级管理员');

-- 默认文章分类
INSERT INTO `category` (`name`, `tag_color`) VALUES
                                                 ('后端开发', '#409EFF'),
                                                 ('前端开发', '#67C23A'),
                                                 ('数据库',   '#E6A23C'),
                                                 ('运维部署', '#F56C6C'),
                                                 ('架构设计', '#909399');
