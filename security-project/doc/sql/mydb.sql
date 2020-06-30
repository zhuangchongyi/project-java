/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80017
 Source Host           : localhost:3306
 Source Schema         : mydb

 Target Server Type    : MySQL
 Target Server Version : 80017
 File Encoding         : 65001

 Date: 30/06/2020 12:57:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for persistent_logins
-- ----------------------------
DROP TABLE IF EXISTS `persistent_logins`;
CREATE TABLE `persistent_logins`  (
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `series` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `token` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `last_used` timestamp(0) NOT NULL,
  PRIMARY KEY (`series`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `permission_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限编号',
  `url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求url',
  `role_id` int(11) NULL DEFAULT NULL COMMENT '角色编号',
  `permission` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限',
  PRIMARY KEY (`permission_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (1, '/superAdmin', 1, 'c,r,u,d');
INSERT INTO `sys_permission` VALUES (3, '/admin', 1, 'c,r,u,d');
INSERT INTO `sys_permission` VALUES (5, '/admin', 2, 'r');
INSERT INTO `sys_permission` VALUES (6, '/user', 3, 'c');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色编号',
  `role_code` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色编码',
  `role_name` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, 'ROLE_SUPER_ADMIN', '超级管理员');
INSERT INTO `sys_role` VALUES (2, 'ROLE_ADMIN', '普通管理员');
INSERT INTO `sys_role` VALUES (3, 'ROLE_USER', '普通用户');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `user_name` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `user_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户编码',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `gender` char(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'root', 'root', '$2a$10$kitT0M97FUL0lX2cpefUKuj3zIGLeSZNHE1ANLbK7zvTN8CRHCB/O', '男');
INSERT INTO `sys_user` VALUES (2, '周一', '1001', '$2a$10$xeSiM9zeLsYwzUKu.9.SV.gtPWH.r7LDfl5Itw8m9jO1ImckJ7xbi', '男');
INSERT INTO `sys_user` VALUES (3, '周二', '1003', '$2a$10$xeSiM9zeLsYwzUKu.9.SV.gtPWH.r7LDfl5Itw8m9jO1ImckJ7xbi', '男');
INSERT INTO `sys_user` VALUES (4, '周三', '1004', '$2a$10$xeSiM9zeLsYwzUKu.9.SV.gtPWH.r7LDfl5Itw8m9jO1ImckJ7xbi', '男');
INSERT INTO `sys_user` VALUES (5, '周四', '1005', '$2a$10$xeSiM9zeLsYwzUKu.9.SV.gtPWH.r7LDfl5Itw8m9jO1ImckJ7xbi', '女');
INSERT INTO `sys_user` VALUES (6, '周五', '1006', '$2a$10$xeSiM9zeLsYwzUKu.9.SV.gtPWH.r7LDfl5Itw8m9jO1ImckJ7xbi', '女');
INSERT INTO `sys_user` VALUES (7, '周六', '1007', '$2a$10$xeSiM9zeLsYwzUKu.9.SV.gtPWH.r7LDfl5Itw8m9jO1ImckJ7xbi', '女');
INSERT INTO `sys_user` VALUES (8, '周日', '1008', '$2a$10$xeSiM9zeLsYwzUKu.9.SV.gtPWH.r7LDfl5Itw8m9jO1ImckJ7xbi', '男');
INSERT INTO `sys_user` VALUES (9, 'admin', 'admin', '$2a$10$xeSiM9zeLsYwzUKu.9.SV.gtPWH.r7LDfl5Itw8m9jO1ImckJ7xbi', '男');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户编号',
  `role_id` int(11) NULL DEFAULT NULL COMMENT '角色编号'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);
INSERT INTO `sys_user_role` VALUES (2, 3);
INSERT INTO `sys_user_role` VALUES (9, 2);
INSERT INTO `sys_user_role` VALUES (3, 3);

SET FOREIGN_KEY_CHECKS = 1;
