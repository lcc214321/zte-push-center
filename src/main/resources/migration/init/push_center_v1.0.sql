/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50719
 Source Host           : localhost:3306
 Source Schema         : push_center

 Target Server Type    : MySQL
 Target Server Version : 50719
 File Encoding         : 65001

 Date: 29/12/2020 16:35:45
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
SET sql_safe_updates=0;

-- ----------------------------
-- Table structure for app
-- ----------------------------
DROP TABLE IF EXISTS `app`;
CREATE TABLE `app`  (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `app_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
    `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `flag` int NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for app_role
-- ----------------------------
DROP TABLE IF EXISTS `app_role`;
CREATE TABLE `app_role` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `app_id` bigint NOT NULL COMMENT '服务ID',
    `role_id` bigint NOT NULL COMMENT '权限ID',
    `status` int NOT NULL DEFAULT '0',
    `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `flag` int NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='服务对应的推送权限表';

-- ----------------------------
-- Table structure for provider
-- ----------------------------
DROP TABLE IF EXISTS `provider`;
CREATE TABLE `provider` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `provider_name` varchar(20) DEFAULT NULL COMMENT '第三方服务商名',
  `description` varchar(120) DEFAULT NULL COMMENT '描述信息  ',
  `created_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `flag` int(1) DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for provider_sms_template
-- ----------------------------
DROP TABLE IF EXISTS `provider_sms_template`;
CREATE TABLE `provider_sms_template` (
  `id` int(8) NOT NULL AUTO_INCREMENT COMMENT '服务商短信模版表',
  `provider_id` int(8) NOT NULL COMMENT '服务供应商表id',
  `sms_template_id` int(8) NOT NULL COMMENT 'sms_template表id',
  `example` text NOT NULL COMMENT '短信内容',
  `sms_config_id` int(8) NOT NULL COMMENT '模版对应的配置id',
  `s_template_id` varchar(30) NOT NULL COMMENT '服务商短信模版id',
  `sign` varchar(45) NOT NULL COMMENT '短信签名',
  `order` int(4) NOT NULL DEFAULT '0' COMMENT '排序',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `flag` int(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
-- ----------------------------
-- Table structure for dic
-- ----------------------------
DROP TABLE IF EXISTS `dic`;
CREATE TABLE `dic`  (
    `id` int(8) NOT NULL DEFAULT 0,
    `key` int(8) NOT NULL,
    `value` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `flag` int NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `role_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
    `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `flag` int NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for script
-- ----------------------------
DROP TABLE IF EXISTS `script`;
CREATE TABLE `script` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` int(1) NOT NULL COMMENT '脚本类型',
  `related` int(1) NOT NULL DEFAULT '0' COMMENT '是否关联',
  `script_tag` varchar(255) DEFAULT NULL COMMENT '脚本唯一标识',
  `provider_id` int(8) DEFAULT NULL COMMENT 'config表主键',
  `description` varchar(120) DEFAULT NULL COMMENT '描述',
  `context` text NOT NULL COMMENT '脚本内容',
  `script_name` varchar(255) NOT NULL COMMENT '脚本名',
  `created_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `flag` int(1) DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for secret_key
-- ----------------------------
DROP TABLE IF EXISTS `secret_key`;
CREATE TABLE `secret_key`  (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `app_id` bigint(20) NOT NULL,
    `app_key` varchar(40) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'Key',
    `app_secret` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '密钥',
    `app_token` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'Key和密钥生成的Token（2小时过期）',
    `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `flag` int NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'App的Key和Secret存储表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sms_config
-- ----------------------------
DROP TABLE IF EXISTS `sms_config`;
CREATE TABLE `sms_config` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `name` varchar(60) DEFAULT NULL,
  `description` varchar(120) DEFAULT NULL,
  `provider_id` int(8) NOT NULL COMMENT 'config表id',
  `s_app_id` varchar(255) DEFAULT NULL COMMENT '第三方短信服务配置的appId',
  `secret_id` varchar(255) DEFAULT NULL COMMENT '第三方短信服务配置的secretId',
  `secret_key` varchar(255) DEFAULT NULL COMMENT '第三方短信服务配置的secretKey',
  `created_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `flag` int(1) DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sms_info
-- ----------------------------
DROP TABLE IF EXISTS `sms_info`;
CREATE TABLE `sms_info`  (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `app_id` bigint(20) NOT NULL COMMENT 'appid',
    `app_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'app名称',
    `phone_num` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '接收人号码',
    `transmit_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '发送时间 ',
    `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
    `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '短信内容',
    `provider` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '短信平台',
    `template_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '模版id',
    `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `flag` int NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `sms_info_id_sequence`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for template
-- ----------------------------
DROP TABLE IF EXISTS `template`;
CREATE TABLE `template` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `type` int(4) DEFAULT '0',
  `name` varchar(45) DEFAULT '',
  `description` varchar(120) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `flag` int(1) DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
-- ----------------------------
-- Table structure for table `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `user_name` varchar(45) COLLATE utf8_bin NOT NULL COMMENT '用户名',
    `user_real_name` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '用户真实姓名',
    `password` text COLLATE utf8_bin NOT NULL COMMENT '密码（加密）',
    `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `flag` int NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='管理平台用户';
SET FOREIGN_KEY_CHECKS = 1;
