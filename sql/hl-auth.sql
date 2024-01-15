/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : hl-auth

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 15/01/2024 15:44:05
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ums_admin
-- ----------------------------
DROP TABLE IF EXISTS `ums_admin`;
CREATE TABLE `ums_admin`  (
  `id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `username` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '管理员名称',
  `password` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '管理员密码',
  `sex` tinyint(10) NULL DEFAULT NULL COMMENT '0：男 1：女',
  `wx_open_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'wxopenid',
  `last_login_ip` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '最近一次登录IP地址',
  `last_login_time` datetime(0) NULL DEFAULT NULL COMMENT '最近一次登录时间',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '\'' COMMENT '头像图片',
  `add_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '管理员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ums_admin
-- ----------------------------
INSERT INTO `ums_admin` VALUES ('8c2bf63982c58f7e43f14c6ddcb08581', 'admin', 'e10adc3949ba59abbe56e057f20f883e', 0, NULL, '0:0:0:0:0:0:0:1', '2023-06-12 14:10:02', 'https://hl-mall-tiny.oss-cn-chengdu.aliyuncs.com/hlmall/images/20240115/2.png', '2023-06-02 13:08:03', '2024-01-15 14:55:21', 0);

-- ----------------------------
-- Table structure for ums_admin_role
-- ----------------------------
DROP TABLE IF EXISTS `ums_admin_role`;
CREATE TABLE `ums_admin_role`  (
  `id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `admin_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '管理员id',
  `role_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '后台用户和角色关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ums_admin_role
-- ----------------------------
INSERT INTO `ums_admin_role` VALUES ('16ff8f327f76549de4180571d82978a8', '8c2bf63982c58f7e43f14c6ddcb08581', '90dd4022ad735b191f6f22a3b38b6303');
INSERT INTO `ums_admin_role` VALUES ('fd09268ff33ff12f68e9d130e0022224', '8c2bf63982c58f7e43f14c6ddcb08581', '57db19fa5d18da3bc2c2e20852aa08ac');

-- ----------------------------
-- Table structure for ums_menu
-- ----------------------------
DROP TABLE IF EXISTS `ums_menu`;
CREATE TABLE `ums_menu`  (
  `id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `pid` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '父级id',
  `path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单地址',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '组件名称',
  `component` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '组件地址',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
  `is_link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '超链接地址',
  `is_hide` tinyint(1) NULL DEFAULT NULL COMMENT '是否隐藏',
  `is_keep_alive` tinyint(1) NULL DEFAULT NULL COMMENT '是否缓存组件',
  `is_affix` tinyint(1) NULL DEFAULT NULL COMMENT '是否固定',
  `is_iframe` tinyint(1) NULL DEFAULT NULL COMMENT '是否内嵌窗口',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `sort` int(10) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ums_menu
-- ----------------------------
INSERT INTO `ums_menu` VALUES ('2bd76aa9d67c8757d63df4a23f2e8c20', '3a1a9273d679e2bc098c40cbc4f7a04e', '/system/menu', 'systemMenu', '/system/menu', '菜单管理', NULL, 0, 1, 0, NULL, 'ph:align-bottom', 3);
INSERT INTO `ums_menu` VALUES ('3a1a9273d679e2bc098c40cbc4f7a04e', '', '/system', 'system', 'layout', '系统管理', NULL, 0, 1, 0, NULL, 'ic:baseline-brightness-5', 0);
INSERT INTO `ums_menu` VALUES ('4ffdf5a9ff76a55ebb139453532d5a15', '3a1a9273d679e2bc098c40cbc4f7a04e', '/system/role', 'systemRole', '/system/role', '角色管理', '', 0, 1, 0, 0, 'mdi:airballoon', 2);
INSERT INTO `ums_menu` VALUES ('690c754029a1a86551d9a34b372bf498', '3a1a9273d679e2bc098c40cbc4f7a04e', '/system/user', 'systemUser', '/system/user', '用户管理', '', 0, 1, 0, 0, 'uil:capture', 1);

-- ----------------------------
-- Table structure for ums_role
-- ----------------------------
DROP TABLE IF EXISTS `ums_role`;
CREATE TABLE `ums_role`  (
  `id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色key',
  `desc` varchar(1023) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色描述',
  `enabled` tinyint(1) NULL DEFAULT 1 COMMENT '是否启用',
  `add_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name_UNIQUE`(`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ums_role
-- ----------------------------
INSERT INTO `ums_role` VALUES ('57db19fa5d18da3bc2c2e20852aa08ac', '管理员', 'admin', '拥有所有权限', 1, '2023-05-30 14:20:02', '2023-05-30 14:20:02', 0);
INSERT INTO `ums_role` VALUES ('90dd4022ad735b191f6f22a3b38b6303', 'test', 'test', 'test', 1, '2023-06-09 13:49:21', '2023-06-09 13:49:21', 0);

-- ----------------------------
-- Table structure for ums_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `ums_role_menu`;
CREATE TABLE `ums_role_menu`  (
  `id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色id',
  `menu_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色和菜单关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ums_role_menu
-- ----------------------------
INSERT INTO `ums_role_menu` VALUES ('18326dfd09891d57a0376c387df4e3b7', '90dd4022ad735b191f6f22a3b38b6303', '690c754029a1a86551d9a34b372bf498');
INSERT INTO `ums_role_menu` VALUES ('722799e049c4e420b246a053cfb585ca', '57db19fa5d18da3bc2c2e20852aa08ac', '3a1a9273d679e2bc098c40cbc4f7a04e');
INSERT INTO `ums_role_menu` VALUES ('ca45095c53366a246cca57be59ff308e', '57db19fa5d18da3bc2c2e20852aa08ac', '4ffdf5a9ff76a55ebb139453532d5a15');
INSERT INTO `ums_role_menu` VALUES ('e5b40b94820d16527962f61959a1f3b8', '57db19fa5d18da3bc2c2e20852aa08ac', '2bd76aa9d67c8757d63df4a23f2e8c20');
INSERT INTO `ums_role_menu` VALUES ('ec665d61e86331ac49f207541ab5fabb', '57db19fa5d18da3bc2c2e20852aa08ac', '690c754029a1a86551d9a34b372bf498');
INSERT INTO `ums_role_menu` VALUES ('f40a080747ab055ffcdb605d695b2f53', '90dd4022ad735b191f6f22a3b38b6303', '2bd76aa9d67c8757d63df4a23f2e8c20');

SET FOREIGN_KEY_CHECKS = 1;
