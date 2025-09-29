/*
 Navicat Premium Data Transfer

 Source Server         : LeanOneForum
 Source Server Type    : MySQL
 Source Server Version : 80036
 Source Host           : db.leafone.20050508.xyz:3306
 Source Schema         : leafone

 Target Server Type    : MySQL
 Target Server Version : 80036
 File Encoding         : 65001

 Date: 26/09/2025 16:21:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for categories
-- ----------------------------
DROP TABLE IF EXISTS `categories`;
CREATE TABLE `categories`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分类名称',
  `slug` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '唯一短标识（用于URL）',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分类简介',
  `parent_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '父级分类（可空）',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_categories_name`(`name` ASC) USING BTREE,
  UNIQUE INDEX `uq_categories_slug`(`slug` ASC) USING BTREE,
  INDEX `idx_categories_parent_id`(`parent_id` ASC) USING BTREE,
  CONSTRAINT `fk_categories_parent` FOREIGN KEY (`parent_id`) REFERENCES `categories` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '文章分类表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of categories
-- ----------------------------
INSERT INTO `categories` VALUES (1, '公告', 'gonggao', '论坛公告与规则', NULL, '2025-09-18 08:50:00');
INSERT INTO `categories` VALUES (2, '闲聊', 'xianliao', '日常生活与随聊', NULL, '2025-09-18 08:50:00');
INSERT INTO `categories` VALUES (3, '学习交流', 'xuexi', '学习与资料分享', NULL, '2025-09-18 08:50:00');
INSERT INTO `categories` VALUES (4, '二手交易', 'ershou', '闲置物品交易区', NULL, '2025-09-18 08:50:00');
INSERT INTO `categories` VALUES (5, '失物招领', 'shiwu', '失物招领与寻物', NULL, '2025-09-18 08:50:00');
INSERT INTO `categories` VALUES (6, '活动赛事', 'huodong', '社团活动与赛事', NULL, '2025-09-18 08:50:00');

-- ----------------------------
-- Table structure for permissions
-- ----------------------------
DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限唯一编码，如 forum.thread.delete',
  `display_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '展示名称',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_permissions_code`(`code` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '权限表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of permissions
-- ----------------------------
INSERT INTO `permissions` VALUES (1, 'forum.thread.create', '创建主题', '创建新的主题帖', '2025-09-17 14:59:16');
INSERT INTO `permissions` VALUES (2, 'forum.thread.edit', '编辑主题', '编辑自己或被允许的主题', '2025-09-17 14:59:16');
INSERT INTO `permissions` VALUES (3, 'forum.thread.delete', '删除主题', '删除主题（含他人主题）', '2025-09-17 14:59:16');
INSERT INTO `permissions` VALUES (4, 'forum.post.create', '发表回复', '发表帖子回复', '2025-09-17 14:59:16');
INSERT INTO `permissions` VALUES (5, 'forum.post.edit', '编辑回复', '编辑自己或被允许的回复', '2025-09-17 14:59:16');
INSERT INTO `permissions` VALUES (6, 'forum.post.delete', '删除回复', '删除回复（含他人回复）', '2025-09-17 14:59:16');
INSERT INTO `permissions` VALUES (7, 'forum.user.ban', '封禁用户', '封禁/解封用户', '2025-09-17 14:59:16');
INSERT INTO `permissions` VALUES (8, 'forum.user.manage', '管理用户资料', '管理用户信息、重置密码等', '2025-09-17 14:59:16');
INSERT INTO `permissions` VALUES (9, 'forum.section.manage', '管理版块', '增删改版块/分区', '2025-09-17 14:59:16');
INSERT INTO `permissions` VALUES (10, 'forum.settings.manage', '管理论坛设置', '站点配置/全局设置', '2025-09-17 14:59:16');

-- ----------------------------
-- Table structure for PostLikes
-- ----------------------------
DROP TABLE IF EXISTS `post_likes`;
CREATE TABLE `post_likes`  (
  `post_id` bigint UNSIGNED NOT NULL,
  `user_id` bigint UNSIGNED NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`post_id`, `user_id`) USING BTREE,
  INDEX `idx_post_likes_user_id`(`user_id` ASC) USING BTREE,
  CONSTRAINT `fk_post_likes_post` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_post_likes_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '文章点赞关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of PostLikes
-- ----------------------------
INSERT INTO `post_likes` VALUES (1, 18, '2025-09-18 09:05:30');
INSERT INTO `post_likes` VALUES (1, 19, '2025-09-18 09:06:10');
INSERT INTO `post_likes` VALUES (1, 21, '2025-09-18 09:07:00');
INSERT INTO `post_likes` VALUES (1, 22, '2025-09-18 09:08:20');
INSERT INTO `post_likes` VALUES (1, 25, '2025-09-18 09:09:45');
INSERT INTO `post_likes` VALUES (2, 19, '2025-09-18 09:15:00');
INSERT INTO `post_likes` VALUES (2, 21, '2025-09-18 09:16:00');
INSERT INTO `post_likes` VALUES (2, 22, '2025-09-18 09:17:00');
INSERT INTO `post_likes` VALUES (3, 6, '2025-09-18 09:12:00');
INSERT INTO `post_likes` VALUES (3, 18, '2025-09-18 09:13:00');
INSERT INTO `post_likes` VALUES (3, 20, '2025-09-18 09:14:00');
INSERT INTO `post_likes` VALUES (3, 21, '2025-09-18 09:15:00');
INSERT INTO `post_likes` VALUES (3, 25, '2025-09-18 09:16:00');
INSERT INTO `post_likes` VALUES (3, 26, '2025-09-18 09:17:00');
INSERT INTO `post_likes` VALUES (3, 38, '2025-09-18 09:18:00');
INSERT INTO `post_likes` VALUES (3, 40, '2025-09-18 09:19:00');
INSERT INTO `post_likes` VALUES (4, 21, '2025-09-18 09:25:00');
INSERT INTO `post_likes` VALUES (4, 24, '2025-09-18 09:26:00');
INSERT INTO `post_likes` VALUES (5, 23, '2025-09-18 09:32:00');
INSERT INTO `post_likes` VALUES (6, 25, '2025-09-19 01:39:02');
INSERT INTO `post_likes` VALUES (6, 40, '2025-09-18 09:40:00');
INSERT INTO `post_likes` VALUES (6, 41, '2025-09-18 09:41:00');
INSERT INTO `post_likes` VALUES (6, 42, '2025-09-18 09:42:00');
INSERT INTO `post_likes` VALUES (8, 33, '2025-09-18 09:27:00');
INSERT INTO `post_likes` VALUES (8, 40, '2025-09-19 01:42:49');
INSERT INTO `post_likes` VALUES (16, 40, '2025-09-19 01:43:01');

-- ----------------------------
-- Table structure for PostReplies
-- ----------------------------
DROP TABLE IF EXISTS `post_replies`;
CREATE TABLE `post_replies`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `post_id` bigint UNSIGNED NOT NULL COMMENT '文章ID',
  `user_id` bigint UNSIGNED NOT NULL COMMENT '回复者用户ID',
  `parent_reply_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '父级回复ID（楼中楼，可空）',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '回复内容',
  `like_count` int UNSIGNED NULL DEFAULT 0 COMMENT '点赞数（冗余计数）',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_replies_post_id`(`post_id` ASC) USING BTREE,
  INDEX `idx_replies_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_replies_parent_id`(`parent_reply_id` ASC) USING BTREE,
  CONSTRAINT `fk_replies_parent` FOREIGN KEY (`parent_reply_id`) REFERENCES `post_replies` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_replies_post` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_replies_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '文章回复表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of PostReplies
-- ----------------------------
INSERT INTO `post_replies` VALUES (1, 1, 18, NULL, '支持！一起建设文明友好的社区～', 2, '2025-09-18 09:10:00', '2025-09-18 09:10:00');
INSERT INTO `post_replies` VALUES (2, 1, 19, 1, '置顶很有用，新人一看就懂规则。', 1, '2025-09-18 09:15:00', '2025-09-18 09:15:00');
INSERT INTO `post_replies` VALUES (3, 1, 20, NULL, '赞同，互相尊重才能更高效交流。', 0, '2025-09-18 09:40:00', '2025-09-18 09:40:00');
INSERT INTO `post_replies` VALUES (4, 2, 21, NULL, '今天的一食堂鱼香肉丝真不错！', 1, '2025-09-18 09:18:00', '2025-09-18 09:18:00');
INSERT INTO `post_replies` VALUES (5, 2, 22, NULL, '我更喜欢二食堂，选择多一点。', 0, '2025-09-18 09:25:00', '2025-09-18 09:25:00');
INSERT INTO `post_replies` VALUES (6, 3, 25, NULL, '谢谢分享，准备期中刚好用得上。', 8, '2025-09-18 09:20:00', '2025-09-25 09:49:30');
INSERT INTO `post_replies` VALUES (7, 3, 26, NULL, '能发一下下载链接吗？', 2, '2025-09-18 09:22:00', '2025-09-25 09:56:02');
INSERT INTO `post_replies` VALUES (8, 3, 19, 7, '已补链接到正文与评论～', 1, '2025-09-18 09:35:00', '2025-09-18 09:35:00');
INSERT INTO `post_replies` VALUES (9, 3, 38, NULL, '有没有电子版手册，纸质有点重。', 0, '2025-09-18 09:50:00', '2025-09-18 09:50:00');
INSERT INTO `post_replies` VALUES (10, 4, 24, NULL, '多少钱？车况如何？', 0, '2025-09-18 09:30:00', '2025-09-18 09:30:00');
INSERT INTO `post_replies` VALUES (11, 5, 23, NULL, '我之前在三食堂门口也捡到过一次。', 0, '2025-09-18 09:28:00', '2025-09-18 09:28:00');
INSERT INTO `post_replies` VALUES (12, 5, 21, 11, '谢谢提醒，我已联系失主。', 0, '2025-09-18 09:35:00', '2025-09-18 09:35:00');
INSERT INTO `post_replies` VALUES (13, 6, 40, NULL, '报名 +1', 1, '2025-09-18 09:33:00', '2025-09-18 09:33:00');
INSERT INTO `post_replies` VALUES (14, 6, 41, NULL, '有名额吗？', 0, '2025-09-18 09:45:00', '2025-09-18 09:45:00');
INSERT INTO `post_replies` VALUES (15, 6, 42, NULL, '求组队～', 0, '2025-09-18 09:55:00', '2025-09-18 09:55:00');
INSERT INTO `post_replies` VALUES (16, 30, 6, NULL, '啊欸口服厉害Joe奥委会佛i饿啊为规范哈维了深刻地将阿法狗皮拉水晶富婆', 0, '2025-09-25 09:30:00', '2025-09-25 09:30:00');

-- ----------------------------
-- Table structure for posts
-- ----------------------------
DROP TABLE IF EXISTS `posts`;
CREATE TABLE `posts`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '文章ID',
  `category_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '分类ID（可空）',
  `author_id` bigint UNSIGNED NOT NULL COMMENT '作者用户ID（FK users.id）',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标题',
  `cover_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '封面',
  `synopsis` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '简介',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '正文内容（可存Markdown）',
  `status` enum('draft','published','archived') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'published' COMMENT '状态：草稿/已发布/归档',
  `is_pinned` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否置顶',
  `is_locked` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否锁帖（禁止新回复）',
  `view_count` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '浏览次数（PV）',
  `reply_count` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '回复数（冗余计数）',
  `like_count` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '点赞数（冗余计数）',
  `last_reply_at` timestamp NULL DEFAULT NULL COMMENT '最后回复时间',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_posts_category_id`(`category_id` ASC) USING BTREE,
  INDEX `idx_posts_author_id`(`author_id` ASC) USING BTREE,
  INDEX `idx_posts_status`(`status` ASC) USING BTREE,
  INDEX `idx_posts_is_pinned`(`is_pinned` ASC) USING BTREE,
  INDEX `idx_posts_last_reply_at`(`last_reply_at` ASC) USING BTREE,
  CONSTRAINT `fk_posts_author` FOREIGN KEY (`author_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_posts_category` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 82 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '文章主表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of posts
-- ----------------------------
INSERT INTO `posts` VALUES (1, 1, 6, '欢迎来到叶一论坛', 'https://pub-bb2418c72b2345ca95be56a4f387301b.r2.dev/LeafOneForum/cover_default.png', '简介', '大家好，欢迎加入叶一论坛！请遵守社区规范，文明交流～', 'published', 1, 0, 256, 3, 9, '2025-09-18 09:40:00', '2025-09-18 09:00:00', '2025-09-21 19:05:39');
INSERT INTO `posts` VALUES (2, 2, 18, '今天食堂好吃吗？', 'https://pub-bb2418c72b2345ca95be56a4f387301b.r2.dev/LeafOneForum/cover_default.png', '简介', '中午刚去了一食堂，感觉不错，大家觉得呢？', 'published', 0, 0, 120, 2, 3, '2025-09-18 09:25:00', '2025-09-18 09:05:00', '2025-09-21 19:05:39');
INSERT INTO `posts` VALUES (3, 3, 19, 'C 语言复习资料分享', 'https://pub-bb2418c72b2345ca95be56a4f387301b.r2.dev/LeafOneForum/cover_default.png', '简介', '整理了一套 C 语言复习资料和题库，欢迎同学们查收与补充。', 'published', 0, 0, 340, 4, 8, '2025-09-18 09:50:00', '2025-09-18 09:10:00', '2025-09-21 19:05:39');
INSERT INTO `posts` VALUES (4, 4, 20, '出闲置二手自行车', 'https://pub-bb2418c72b2345ca95be56a4f387301b.r2.dev/LeafOneForum/cover_default.png', '简介', '九成新山地车，含车锁与头盔，价格可议，有需要的留言。', 'published', 0, 0, 75, 1, 2, '2025-09-18 09:30:00', '2025-09-18 09:12:00', '2025-09-21 19:05:39');
INSERT INTO `posts` VALUES (5, 5, 21, '失物招领：校园卡', 'https://pub-bb2418c72b2345ca95be56a4f387301b.r2.dev/LeafOneForum/cover_default.png', '简介', '在图书馆二楼自习区捡到一张校园卡，私信核对信息后归还。', 'published', 0, 0, 60, 2, 1, '2025-09-18 09:35:00', '2025-09-18 09:13:00', '2025-09-21 19:05:39');
INSERT INTO `posts` VALUES (6, 6, 22, '周末篮球赛报名', 'https://pub-bb2418c72b2345ca95be56a4f387301b.r2.dev/LeafOneForum/cover_default.png', '简介', '本周六下午在操场 3 号场地，有兴趣的同学在此贴下方留言报名。', 'published', 0, 0, 98, 3, 19, '2025-09-18 09:55:00', '2025-09-18 09:15:00', '2025-09-21 19:05:39');
INSERT INTO `posts` VALUES (7, 3, 25, '期中复习自习室推荐', 'https://pub-bb2418c72b2345ca95be56a4f387301b.r2.dev/LeafOneForum/cover_default.png', '简介', '求推荐安静、插座多、网络稳定的自习室，感谢！', 'published', 0, 0, 45, 0, 0, '2025-09-18 19:16:32', '2025-09-18 09:20:00', '2025-09-21 19:05:39');
INSERT INTO `posts` VALUES (8, 2, 6, '今晚有人夜跑吗', 'https://pub-bb2418c72b2345ca95be56a4f387301b.r2.dev/LeafOneForum/cover_default.png', '简介', '晚上 20:30 准备操场夜跑，有一起的同学吗？', 'published', 0, 0, 33, 1, 4, '2025-09-18 09:28:00', '2025-09-18 09:22:00', '2025-09-21 19:05:39');
INSERT INTO `posts` VALUES (9, 1, 9, '求助：校园网断线问题', 'https://pub-bb2418c72b2345ca95be56a4f387301b.r2.dev/LeafOneForum/cover_default.png', '简介', '最近校园网经常断线，请问大家有没有遇到类似情况，有没有临时解决办法？', 'published', 0, 0, 142, 4, 10, '2025-09-18 10:20:00', '2025-09-18 10:00:00', '2025-09-21 19:05:39');
INSERT INTO `posts` VALUES (11, 3, 11, '机器学习线上资料分享', 'https://pub-bb2418c72b2345ca95be56a4f387301b.r2.dev/LeafOneForum/cover_default.png', '简介', '整理了一些机器学习入门的视频和笔记，适合自学，大家欢迎补充。', 'published', 0, 0, 621, 9, 45, '2025-09-18 12:00:00', '2025-09-17 20:00:00', '2025-09-21 19:05:39');
INSERT INTO `posts` VALUES (12, 4, 12, '求租：下学期合租房', 'https://pub-bb2418c72b2345ca95be56a4f387301b.r2.dev/LeafOneForum/cover_default.png', '简介', '下学期想在校外找合租，有无室友或推荐小区，预算不超过 1200/月。', 'published', 0, 0, 85, 2, 7, '2025-09-18 12:30:00', '2025-09-18 10:15:00', '2025-09-21 19:05:39');
INSERT INTO `posts` VALUES (14, 6, 14, '校园优惠：附近餐厅折扣', 'https://pub-bb2418c72b2345ca95be56a4f387301b.r2.dev/LeafOneForum/cover_default.png', '简介', '某餐厅对学生有 8 折优惠，凭学生证有效，地址附上。', 'published', 0, 0, 44, 1, 3, '2025-09-18 13:20:00', '2025-09-18 12:40:00', '2025-09-21 19:05:39');
INSERT INTO `posts` VALUES (15, 1, 15, '程序员面试经验分享', 'https://pub-bb2418c72b2345ca95be56a4f387301b.r2.dev/LeafOneForum/cover_default.png', '简介', '分享一些算法面试与系统设计的经验与常见题目，供大家参考。', 'published', 1, 0, 980, 15, 120, '2025-09-18 14:00:00', '2025-09-16 09:00:00', '2025-09-21 19:05:39');
INSERT INTO `posts` VALUES (16, 2, 16, '今天食堂排队很长', 'https://pub-bb2418c72b2345ca95be56a4f387301b.r2.dev/LeafOneForum/cover_default.png', '简介', '中午二食堂排队时间超过 20 分钟，有没有避开高峰的建议？', 'published', 0, 0, 134, 3, 10, '2025-09-18 14:15:00', '2025-09-18 13:05:00', '2025-09-21 19:05:39');
INSERT INTO `posts` VALUES (17, 3, 17, '期末复习资料征集', 'https://pub-bb2418c72b2345ca95be56a4f387301b.r2.dev/LeafOneForum/cover_default.png', '简介', '期末快到了，大家把有用的复习资料贴出来，互相帮助。', 'published', 0, 0, 402, 6, 27, '2025-09-18 14:40:00', '2025-09-17 21:10:00', '2025-09-21 19:05:39');
INSERT INTO `posts` VALUES (18, 4, 18, '失物招领：耳机', 'https://pub-bb2418c72b2345ca95be56a4f387301b.r2.dev/LeafOneForum/cover_default.png', '简介', '在教学楼门口捡到一副蓝牙耳机，描述并私信可核对后归还。', 'published', 0, 0, 37, 0, 2, '2025-09-18 15:00:00', '2025-09-18 14:45:00', '2025-09-21 19:05:39');
INSERT INTO `posts` VALUES (19, 5, 19, '周日电影想约人一起去', 'https://pub-bb2418c72b2345ca95be56a4f387301b.r2.dev/LeafOneForum/cover_default.png', '简介', '周日想去看新上映的电影，有人一起的吗，票价可AA。', 'published', 0, 0, 66, 2, 5, '2025-09-18 15:20:00', '2025-09-18 14:50:00', '2025-09-21 19:05:39');
INSERT INTO `posts` VALUES (20, 6, 20, '校园健身房器材维护通知', 'https://pub-bb2418c72b2345ca95be56a4f387301b.r2.dev/LeafOneForum/cover_default.png', '简介', '健身房将于周末进行器材检修，届时暂停开放，请相互转告。', 'published', 0, 0, 28, 0, 1, '2025-09-18 15:45:00', '2025-09-18 15:10:00', '2025-09-21 19:05:39');
INSERT INTO `posts` VALUES (21, 1, 21, '数学题讨论：线性代数难点', 'https://pub-bb2418c72b2345ca95be56a4f387301b.r2.dev/LeafOneForum/cover_default.png', '简介', '哪位同学能讲解一下特征值与相似矩阵的几道习题？', 'published', 0, 1, 315, 7, 35, '2025-09-18 16:10:00', '2025-09-17 18:00:00', '2025-09-21 19:05:39');
INSERT INTO `posts` VALUES (22, 2, 22, '校内兼职推荐', 'https://pub-bb2418c72b2345ca95be56a4f387301b.r2.dev/LeafOneForum/cover_default.png', '简介', '收集了一些靠谱的校内兼职岗位，待遇与时间都不错，欢迎查看。', 'published', 0, 0, 190, 4, 10, '2025-09-18 16:30:00', '2025-09-16 14:00:00', '2025-09-21 19:05:39');
INSERT INTO `posts` VALUES (23, 3, 23, '算法题分享：图论经典题', 'https://pub-bb2418c72b2345ca95be56a4f387301b.r2.dev/LeafOneForum/cover_default.png', '简介', '附上几道图论的经典题，以及我的解题思路，欢迎讨论优化。', 'published', 0, 0, 753, 12, 58, '2025-09-18 16:55:00', '2025-09-15 20:00:00', '2025-09-21 19:05:39');
INSERT INTO `posts` VALUES (24, 4, 24, '骑行团本周日集合', 'https://pub-bb2418c72b2345ca95be56a4f387301b.r2.dev/LeafOneForum/cover_default.png', '简介', '本周日组织校园周边骑行，集合地点体育馆门口，欢迎加入。', 'published', 0, 0, 121, 3, 9, '2025-09-18 17:20:00', '2025-09-18 11:45:00', '2025-09-21 19:05:39');
INSERT INTO `posts` VALUES (25, 5, 25, '课程问答：数据库优化', 'https://pub-bb2418c72b2345ca95be56a4f387301b.r2.dev/LeafOneForum/cover_default.png', '简介', '请教大家 MySQL 索引优化与慢查询排查经验，哪个工具好用？', 'published', 0, 0, 467, 8, 36, '2025-09-18 17:40:00', '2025-09-17 09:30:00', '2025-09-21 19:05:39');
INSERT INTO `posts` VALUES (26, 6, 26, '摄影社招新', 'https://pub-bb2418c72b2345ca95be56a4f387301b.r2.dev/LeafOneForum/cover_default.png', '简介', '摄影社本学期招新，有兴趣的同学来体验摄影外拍与后期交流。', 'published', 0, 0, 53, 1, 4, '2025-09-18 18:05:00', '2025-09-18 17:00:00', '2025-09-21 19:05:39');
INSERT INTO `posts` VALUES (28, 2, 28, '求推荐靠谱外卖商家', 'https://pub-bb2418c72b2345ca95be56a4f387301b.r2.dev/LeafOneForum/cover_default.png', '简介', '最近想试试附近的外卖店，大家有没有好评商家推荐？', 'published', 0, 0, 102, 2, 6, '2025-09-18 18:55:00', '2025-09-18 17:30:00', '2025-09-21 19:05:39');
INSERT INTO `posts` VALUES (29, 3, 29, '竞赛题讨论：ACM 训练', 'https://pub-bb2418c72b2345ca95be56a4f387301b.r2.dev/LeafOneForum/cover_default.png', '简介', '本周训练题目录及解题报告分享，希望能帮助备赛同学。', 'published', 1, 0, 930, 20, 150, '2025-09-18 19:15:00', '2025-09-10 08:00:00', '2025-09-21 19:05:39');
INSERT INTO `posts` VALUES (30, 4, 30, '宿舍网购收货问题', 'https://pub-bb2418c72b2345ca95be56a4f387301b.r2.dev/LeafOneForum/cover_default.png', '简介', '有人遇到宿舍快递放错地、丢失的情况吗？如何解决更靠谱？', 'published', 0, 0, 71, 1, 3, '2025-09-18 19:40:00', '2025-09-18 19:00:00', '2025-09-21 19:05:39');
INSERT INTO `posts` VALUES (33, 1, 33, '英语角本周主题：口语练习', 'https://pub-bb2418c72b2345ca95be56a4f387301b.r2.dev/LeafOneForum/cover_default.png', '简介', '本周英语角主题为日常口语交流，欢迎练习口语的同学参加。', 'published', 0, 0, 48, 1, 8, '2025-09-18 20:55:00', '2025-09-18 20:00:00', '2025-09-21 19:05:39');
INSERT INTO `posts` VALUES (34, 2, 34, '校园公交时间调整通知', 'https://pub-bb2418c72b2345ca95be56a4f387301b.r2.dev/LeafOneForum/cover_default.png', '简介', '因施工，部分校车线路本周临时调整，请提前查看时刻表。', 'published', 0, 0, 22, 0, 1, '2025-09-18 21:15:00', '2025-09-18 20:10:00', '2025-09-21 19:05:39');
INSERT INTO `posts` VALUES (35, 3, 35, '程序性能调优经验帖', 'https://pub-bb2418c72b2345ca95be56a4f387301b.r2.dev/LeafOneForum/cover_default.png', '简介', '分享一些 Java 与数据库性能调优的实战经验与工具使用。', 'published', 0, 0, 512, 10, 60, '2025-09-18 21:40:00', '2025-09-17 22:00:00', '2025-09-21 19:05:39');
INSERT INTO `posts` VALUES (36, 4, 36, '失物：自行车钥匙', 'https://pub-bb2418c72b2345ca95be56a4f387301b.r2.dev/LeafOneForum/cover_default.png', '简介', '下午在图书馆门口掉了钥匙串，有黄绿两色钥匙扣，看到请联系。', 'published', 0, 0, 18, 0, 0, '2025-09-18 22:00:00', '2025-09-18 21:30:00', '2025-09-21 19:05:39');
INSERT INTO `posts` VALUES (37, 5, 37, '关于校园餐卡充值问题', 'https://pub-bb2418c72b2345ca95be56a4f387301b.r2.dev/LeafOneForum/cover_default.png', '简介', '校园餐卡充值失败的同学遇到过吗，充值后金额不显示怎么办？', 'published', 0, 0, 95, 2, 9, '2025-09-18 22:25:00', '2025-09-18 21:00:00', '2025-09-21 19:05:39');
INSERT INTO `posts` VALUES (38, 6, 38, '社团活动：桌游之夜', 'https://pub-bb2418c72b2345ca95be56a4f387301b.r2.dev/LeafOneForum/cover_default.png', '简介', '本周五社团举办桌游之夜，备有多款桌游，欢迎报名参加。', 'published', 0, 0, 64, 1, 6, '2025-09-18 22:50:00', '2025-09-18 20:40:00', '2025-09-21 19:05:39');
INSERT INTO `posts` VALUES (39, 1, 39, '考研经验交流', 'https://pub-bb2418c72b2345ca95be56a4f387301b.r2.dev/Blog/desktop-banner/4.webp', '简介', '分享考研复习计划、经验以及资料来源，互相鼓励。', 'published', 0, 0, 410, 11, 48, '2025-09-18 23:10:00', '2025-09-14 09:00:00', '2025-09-21 20:14:58');
INSERT INTO `posts` VALUES (40, 2, 40, '求推荐物美价廉的打印店', 'https://pub-bb2418c72b2345ca95be56a4f387301b.r2.dev/LeafOneForum/cover_default.png', '简介', '需要大量打印资料，想找离学校近、价格合理的打印店。', 'published', 0, 0, 76, 2, 11, '2025-09-18 23:35:00', '2025-09-18 22:10:00', '2025-09-21 19:05:39');
INSERT INTO `posts` VALUES (41, 3, 9, '学术讲座：量子计算入门', 'https://pub-bb2418c72b2345ca95be56a4f387301b.r2.dev/LeafOneForum/cover_default.png', '简介', '本周将有关于量子计算的学术讲座，地点与时间贴在下方，欢迎参加。', 'published', 0, 0, 305, 6, 22, '2025-09-19 09:00:00', '2025-09-18 08:00:00', '2025-09-21 19:05:39');
INSERT INTO `posts` VALUES (43, 5, 11, '分享几道数学有趣题', 'https://pub-bb2418c72b2345ca95be56a4f387301b.r2.dev/LeafOneForum/cover_default.png', '简介', '贴几道数学小题，欢迎感兴趣的同学一起来讨论答案与解法。', 'published', 0, 0, 223, 5, 16, '2025-09-19 09:45:00', '2025-09-18 09:40:00', '2025-09-21 19:05:39');
INSERT INTO `posts` VALUES (44, 6, 12, '实验课程组队通知', 'https://pub-bb2418c72b2345ca95be56a4f387301b.r2.dev/LeafOneForum/cover_default.png', '简介', '实验课需要组队完成项目，寻找两名组员，有意者请留言。', 'published', 0, 0, 67, 2, 5, '2025-09-19 10:10:00', '2025-09-18 10:00:00', '2025-09-21 19:05:39');
INSERT INTO `posts` VALUES (46, 2, 14, '二手手机转让', 'https://pub-bb2418c72b2345ca95be56a4f387301b.r2.dev/LeafOneForum/cover_default.png', '简介', '小米八成新，功能正常，不接受试机，仅限同校同城面交。', 'published', 0, 0, 132, 2, 8, '2025-09-19 11:00:00', '2025-09-18 11:20:00', '2025-09-21 19:05:39');
INSERT INTO `posts` VALUES (47, 3, 15, '分享读书笔记：高效学习法', 'https://pub-bb2418c72b2345ca95be56a4f387301b.r2.dev/LeafOneForum/cover_default.png', '简介', '最近读了有关学习方法的书，整理成笔记分享给大家。', 'published', 0, 0, 284, 6, 21, '2025-09-19 11:25:00', '2025-09-16 19:00:00', '2025-09-21 19:05:39');
INSERT INTO `posts` VALUES (48, 4, 16, '校园摄影比赛通知', 'https://pub-bb2418c72b2345ca95be56a4f387301b.r2.dev/LeafOneForum/cover_default.png', '简介', '校园将举办摄影比赛，有奖金与证书，欢迎投稿作品。', 'published', 0, 0, 119, 3, 9, '2025-09-19 11:50:00', '2025-09-18 10:40:00', '2025-09-21 19:05:39');
INSERT INTO `posts` VALUES (49, 5, 17, '求助：LaTeX 排版问题', 'https://pub-bb2418c72b2345ca95be56a4f387301b.r2.dev/LeafOneForum/cover_default.png', '简介', '数学公式在 LaTeX 中对齐有问题，求解答或示例代码。', 'published', 0, 0, 341, 7, 29, '2025-09-19 12:15:00', '2025-09-17 21:30:00', '2025-09-21 19:05:39');
INSERT INTO `posts` VALUES (50, 6, 18, '周末志愿活动回顾', 'https://pub-bb2418c72b2345ca95be56a4f387301b.r2.dev/LeafOneForum/cover_default.png', '简介', '分享本次志愿活动的照片与心得，感谢参与的同学们。', 'published', 0, 0, 58, 1, 4, '2025-09-19 12:40:00', '2025-09-18 12:00:00', '2025-09-21 19:05:39');
INSERT INTO `posts` VALUES (51, 1, 19, '考试经验：如何高效记忆', 'https://pub-bb2418c72b2345ca95be56a4f387301b.r2.dev/LeafOneForum/cover_default.png', '简介', '总结了一些记忆方法和复习计划，供考前复习参考。', 'published', 0, 0, 476, 9, 33, '2025-09-19 13:05:00', '2025-09-15 08:00:00', '2025-09-21 19:05:39');
INSERT INTO `posts` VALUES (52, 2, 20, '校内绿化志愿招募', 'https://pub-bb2418c72b2345ca95be56a4f387301b.r2.dev/LeafOneForum/cover_default.png', '简介', '需要若干名志愿者参与校园绿化维护，报名方式见贴内。', 'published', 0, 0, 34, 0, 4, '2025-09-19 13:30:00', '2025-09-18 13:10:00', '2025-09-21 19:05:39');
INSERT INTO `posts` VALUES (53, 3, 21, '求学术论文模板', 'https://pub-bb2418c72b2345ca95be56a4f387301b.r2.dev/LeafOneForum/cover_default.png', '简介', '请问哪里可以下载到学校推荐的论文模板（Word/LaTeX）？', 'published', 0, 0, 205, 4, 14, '2025-09-19 13:55:00', '2025-09-17 10:00:00', '2025-09-21 19:05:39');
INSERT INTO `posts` VALUES (54, 4, 22, '室友招募：寻找室友合租', 'https://pub-bb2418c72b2345ca95be56a4f387301b.r2.dev/LeafOneForum/cover_default.png', '简介', '下学期想找室友合租，房子已看好，诚寻靠谱室友。', 'published', 0, 0, 96, 2, 7, '2025-09-19 14:20:00', '2025-09-18 14:00:00', '2025-09-21 19:05:39');
INSERT INTO `posts` VALUES (55, 5, 23, '线上课程推荐：深度学习', 'https://pub-bb2418c72b2345ca95be56a4f387301b.r2.dev/LeafOneForum/cover_default.png', '简介', '推荐几门口碑不错的深度学习在线课程与习题集。', 'published', 0, 0, 522, 11, 47, '2025-09-19 14:45:00', '2025-09-16 21:00:00', '2025-09-21 19:05:39');
INSERT INTO `posts` VALUES (56, 6, 24, '校园艺术节活动安排', 'https://pub-bb2418c72b2345ca95be56a4f387301b.r2.dev/LeafOneForum/cover_default.png', '简介', '艺术节将于下月举行，活动安排与报名链接见帖内。', 'published', 0, 0, 88, 2, 6, '2025-09-19 15:10:00', '2025-09-18 15:00:00', '2025-09-21 19:05:39');
INSERT INTO `posts` VALUES (57, 1, 25, '代码托管工具比较：Git/GitLab/GitHub', 'https://pub-bb2418c72b2345ca95be56a4f387301b.r2.dev/LeafOneForum/cover_default.png', '简介', '比较几个常见的代码托管平台的优缺点与使用建议。', 'published', 0, 0, 438, 8, 30, '2025-09-19 15:35:00', '2025-09-17 11:00:00', '2025-09-21 19:05:39');
INSERT INTO `posts` VALUES (58, 2, 26, '归档：往期活动照片', 'https://pub-bb2418c72b2345ca95be56a4f387301b.r2.dev/LeafOneForum/cover_default.png', '简介', '汇总了上学期的活动照片，移动至相册并做了标注（归档）', 'archived', 0, 0, 152, 3, 10, '2025-09-18 18:00:00', '2025-09-10 17:00:00', '2025-09-21 19:05:39');
INSERT INTO `posts` VALUES (59, 6, 22, '东方故事都是符合公司的发货给', 'https://pub-bb2418c72b2345ca95be56a4f387301b.r2.dev/Blog/desktop-banner/4.webp', '啊会计师公会放大三个代表卡死了咖啡红色', 'v二十多个VS豆腐干山豆根山豆根三个', 'published', 0, 0, 0, 0, 0, '2025-09-11 22:10:49', '2025-09-21 20:56:01', '2025-09-21 22:10:52');
INSERT INTO `posts` VALUES (75, 1, 177, '用户发帖权限测试', 'https://ts1.tc.mm.bing.net/th?id=ORMS.bd79219b113a011bc9ea8751c46a19f0&pid=Wdp&w=268&h=140&qlt=90&c=1&rs=1&dpr=1&p=0', '测试', '速度，不能反抗精神的不能反抗精神的部分就开始', 'published', 0, 0, 0, 0, 0, NULL, '2025-09-23 21:10:13', '2025-09-23 21:10:13');
INSERT INTO `posts` VALUES (76, 2, 177, '用户发帖权限测试', 'https://pub-bb2418c72b2345ca95be56a4f387301b.r2.dev/LeafOneForum/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20250922211538_1994_14.jpg', '测试', '爱上了天赐哥', 'published', 0, 0, 8, 0, 0, NULL, '2025-09-23 21:12:29', '2025-09-24 06:30:43');
INSERT INTO `posts` VALUES (77, 1, 178, '用户发帖权限测试', 'https://pub-bb2418c72b2345ca95be56a4f387301b.r2.dev/LeafOneForum/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20250922211538_1994_14.jpg', '我爱天赐哥', '我爱天赐哥', 'published', 0, 0, 6, 0, 0, NULL, '2025-09-23 16:48:45', '2025-09-24 06:29:43');
INSERT INTO `posts` VALUES (78, 2, 178, '威威企鹅企鹅', 'https://ts1.tc.mm.bing.net/th?id=ORMS.bd79219b113a011bc9ea8751c46a19f0&pid=Wdp&w=268&h=140&qlt=90&c=1&rs=1&dpr=1&p=0', '我爱天赐哥', '的算法撒旦飞洒地方士大夫', 'published', 0, 0, 0, 0, 0, NULL, '2025-09-23 16:49:30', '2025-09-23 16:49:30');
INSERT INTO `posts` VALUES (79, 4, 6, '用户发帖测试', 'https://vip.123pan.cn/1819141230/图片库/轻兰映画/解压/轻兰映画_ Grand.003 [82P-416MB]/0043.jpg', '测试', '好复杂付租方式如上图反复风行天下推推图幸幸福福粉', 'published', 0, 0, 0, 0, 0, NULL, '2025-09-24 00:54:59', '2025-09-24 00:54:59');
INSERT INTO `posts` VALUES (81, 2, 177, '66岁山口百惠近况曝光：条纹衫出街，发福驼背，却活得自在', 'https://img-s.msn.cn/tenant/amp/entityid/AA1N73Sp.img?w=640&h=657&m=6&x=259&y=173&s=269&d=269', '提起山口百惠，人们总会想到1970年代那位清纯、优雅、光芒四射的偶像，她音乐、戏剧和银幕形象深深刻在一代人的记忆里。', '提起山口百惠，人们总会想到1970年代那位清纯、优雅、光芒四射的偶像，她音乐、戏剧和银幕形象深深刻在一代人的记忆里。\n\n\n66岁山口百惠近况曝光：条纹衫出街，发福驼背，却活得自在\n66岁山口百惠近况曝光：条纹衫出街，发福驼背，却活得自在\n自1980年隐退、与三浦友和结婚后，她彻底退出演艺圈，把生活过成了另一种“传奇”。\n\n\n66岁山口百惠近况曝光：条纹衫出街，发福驼背，却活得自在\n66岁山口百惠近况曝光：条纹衫出街，发福驼背，却活得自在\n近日，有网友拍到山口百惠与丈夫逛街的照片，状态引发讨论——穿着条纹长款上衣、背部略有弯曲、身形发福。\n\n\n66岁山口百惠近况曝光：条纹衫出街，发福驼背，却活得自在\n66岁山口百惠近况曝光：条纹衫出街，发福驼背，却活得自在\n最近被拍：照片里的她\n\n根据网友拍到的照片，山口百惠与丈夫三浦友和一同外出，地点似乎是东京某处商区或街道。她穿了一件条纹长款上衣，颜色与款式都偏低调。衣服较长，遮住部分下半身；背部弯曲、身形比年轻时明显圆润，有些发福的迹象。与这些细节相比，更引人注意的，是她的神情与姿态——没有故作姿态，没有明星光环，而是一个中年女性的真实与自在。\n\n\n66岁山口百惠近况曝光：条纹衫出街，发福驼背，却活得自在\n66岁山口百惠近况曝光：条纹衫出街，发福驼背，却活得自在\n照片中，山口百惠和妻子的伴侣看似放慢脚步，挑选物品，偶尔低头与丈夫交谈，动作自然，没有刻意遮掩。这样的状态，与她几十年来鲜少公开露面的隐退形象一致——她未曾重返舞台，也不追求时尚杂志曝光，而是以普通人的方式生活。\n\n\n66岁山口百惠近况曝光：条纹衫出街，发福驼背，却活得自在\n66岁山口百惠近况曝光：条纹衫出街，发福驼背，却活得自在\n身体与岁月：不可避免的变化\n\n岁月总会在我们的肌肤与骨骼上留下印记。山口百惠如今已是六十多岁，相对于她风华正茂的年代，不仅是体型，连体态也出现了一些中年特征：背部稍弯、肩颈可能因为年长、因久站或久坐不佳影响姿势；体型虽然发福，但并未失去气质——她的脸庞仍显温润、五官虽有松弛，但神态端正、眼神平静。\n\n\n66岁山口百惠近况曝光：条纹衫出街，发福驼背，却活得自在\n66岁山口百惠近况曝光：条纹衫出街，发福驼背，却活得自在\n隐退后的生活：更多是自我与家庭\n\n隐退四十余年，山口百惠的生活几乎与公众视线隔绝。根据她过往的访谈、媒体零星的抓拍，以及维基与新闻资料，她没有再出演影视作品，也鲜少接受公开采访。她的重心已经完全转向家庭：丈夫三浦友和，两子，以及儿孙。她也投入一些兴趣爱好，比如拼布艺术、手工、园艺等，这些让她保持内心的丰富与平静。\n\n\n66岁山口百惠近况曝光：条纹衫出街，发福驼背，却活得自在\n66岁山口百惠近况曝光：条纹衫出街，发福驼背，却活得自在\n有报道称她搬到乡下居住（或长期兼顾乡间与城市生活），希望过一种更宁静、脱离商业化、少些外界干扰的日子。\n\n随着年龄增长，她可能越来越重视健康管理，包括舒适的穿衣、行动的便利，也许减少辛苦活动，多一些静态爱好，如园艺、手工、阅读等。\n\n\n66岁山口百惠近况曝光：条纹衫出街，发福驼背，却活得自在\n66岁山口百惠近况曝光：条纹衫出街，发福驼背，却活得自在\n从偶像到普通人：我们不应忘记的尊重\n\n山口百惠的故事，对我们所有人都是一种提醒：无论多么闪耀，多么被人仰望，终将面对人生的常态 —— 岁月、变老、家庭、健康。我们对偶像的期待不应是永远不变的完美，而是理解、体谅与尊重。\n\n\n66岁山口百惠近况曝光：条纹衫出街，发福驼背，却活得自在\n66岁山口百惠近况曝光：条纹衫出街，发福驼背，却活得自在\n当我们看到她穿条纹长款上衣、背部有些弯、身形发福，不应该先批评，而是感受到一种岁月在身上的痕迹，一种坚持与选择的结果。她并没有抛弃自己，而是在新的人生阶段活出另一种美。\n\n\n66岁山口百惠近况曝光：条纹衫出街，发福驼背，却活得自在\n66岁山口百惠近况曝光：条纹衫出街，发福驼背，却活得自在\n结语\n\n山口百惠已经不再是那个站在舞台中央，被镁光灯簇拥的少女，但她并未消失，而是以自己的方式存在。她可能不追求大众的赞叹，但那份来自家庭的爱的陪伴、平稳日子的满足，可能是她所追求的真正幸福。\n\n\n66岁山口百惠近况曝光：条纹衫出街，发福驼背，却活得自在\n66岁山口百惠近况曝光：条纹衫出街，发福驼背，却活得自在\n照片中的她或许有背部弯曲、有些发福，但这正是“人生”的一部分。愿我们在怀念她年轻风华的同时，也能以宽容的心，欣赏她现在的姿态——一个在人生黄昏仍然温暖、真实的山口百惠。\n\n编辑：李慧\n\n一审：李慧\n\n二审：汤世明\n\n三审：王超', 'published', 0, 0, 0, 0, 0, NULL, '2025-09-24 05:49:57', '2025-09-24 05:49:57');

-- ----------------------------
-- Table structure for reply_likes
-- ----------------------------
DROP TABLE IF EXISTS `reply_likes`;
CREATE TABLE `reply_likes`  (
  `reply_id` bigint UNSIGNED NOT NULL,
  `user_id` bigint UNSIGNED NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`reply_id`, `user_id`) USING BTREE,
  INDEX `idx_reply_likes_user_id`(`user_id` ASC) USING BTREE,
  CONSTRAINT `fk_reply_likes_reply` FOREIGN KEY (`reply_id`) REFERENCES `post_replies` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_reply_likes_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '回复点赞关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of reply_likes
-- ----------------------------
INSERT INTO `reply_likes` VALUES (1, 19, '2025-09-18 09:11:00');
INSERT INTO `reply_likes` VALUES (1, 21, '2025-09-18 09:11:30');
INSERT INTO `reply_likes` VALUES (2, 22, '2025-09-18 09:16:00');
INSERT INTO `reply_likes` VALUES (4, 22, '2025-09-18 09:19:00');
INSERT INTO `reply_likes` VALUES (6, 6, '2025-09-25 09:49:17');
INSERT INTO `reply_likes` VALUES (6, 19, '2025-09-18 09:21:00');
INSERT INTO `reply_likes` VALUES (6, 26, '2025-09-18 09:21:30');
INSERT INTO `reply_likes` VALUES (7, 6, '2025-09-25 09:56:01');
INSERT INTO `reply_likes` VALUES (7, 25, '2025-09-18 09:23:00');
INSERT INTO `reply_likes` VALUES (8, 26, '2025-09-18 09:36:00');
INSERT INTO `reply_likes` VALUES (13, 41, '2025-09-18 09:34:00');

-- ----------------------------
-- Table structure for RolePermissions
-- ----------------------------
DROP TABLE IF EXISTS `role_permissions`;
CREATE TABLE `role_permissions`  (
  `role_id` bigint UNSIGNED NOT NULL,
  `permission_id` bigint UNSIGNED NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`role_id`, `permission_id`) USING BTREE,
  INDEX `idx_role_permissions_permission_id`(`permission_id` ASC) USING BTREE,
  CONSTRAINT `fk_rp_permission` FOREIGN KEY (`permission_id`) REFERENCES `permissions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_rp_role` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色-权限 关系' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of RolePermissions
-- ----------------------------
INSERT INTO `role_permissions` VALUES (1, 1, '2025-09-17 14:59:42');
INSERT INTO `role_permissions` VALUES (1, 2, '2025-09-17 14:59:42');
INSERT INTO `role_permissions` VALUES (1, 3, '2025-09-17 14:59:42');
INSERT INTO `role_permissions` VALUES (1, 4, '2025-09-17 14:59:42');
INSERT INTO `role_permissions` VALUES (1, 5, '2025-09-17 14:59:42');
INSERT INTO `role_permissions` VALUES (1, 6, '2025-09-17 14:59:42');
INSERT INTO `role_permissions` VALUES (1, 7, '2025-09-17 14:59:42');
INSERT INTO `role_permissions` VALUES (1, 8, '2025-09-17 14:59:42');
INSERT INTO `role_permissions` VALUES (1, 9, '2025-09-17 14:59:42');
INSERT INTO `role_permissions` VALUES (1, 10, '2025-09-17 14:59:42');
INSERT INTO `role_permissions` VALUES (2, 1, '2025-09-17 15:00:09');
INSERT INTO `role_permissions` VALUES (2, 2, '2025-09-17 15:00:09');
INSERT INTO `role_permissions` VALUES (2, 4, '2025-09-17 15:00:09');
INSERT INTO `role_permissions` VALUES (2, 5, '2025-09-17 15:00:09');
INSERT INTO `role_permissions` VALUES (2, 9, '2025-09-17 15:00:09');
INSERT INTO `role_permissions` VALUES (2, 10, '2025-09-17 15:00:09');
INSERT INTO `role_permissions` VALUES (4, 1, '2025-09-17 15:00:36');
INSERT INTO `role_permissions` VALUES (4, 2, '2025-09-17 15:00:36');
INSERT INTO `role_permissions` VALUES (4, 4, '2025-09-17 15:00:36');
INSERT INTO `role_permissions` VALUES (4, 5, '2025-09-17 15:00:36');

-- ----------------------------
-- Table structure for Role
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '系统唯一标识，如 admin, organizer, moderator, user, banned',
  `display_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '展示名称',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `is_system` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否系统内置角色',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_roles_name`(`name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of Role
-- ----------------------------
INSERT INTO `roles` VALUES (1, 'organizer', '组织者', '拥有全部权限', 1, '2025-09-17 14:57:54');
INSERT INTO `roles` VALUES (2, 'admin', '管理员', '负责组织管理/活动管理等', 1, '2025-09-17 14:57:54');
INSERT INTO `roles` VALUES (3, 'moderator', '版主', '负责板块/内容的日常管理', 1, '2025-09-17 14:57:54');
INSERT INTO `roles` VALUES (4, 'user', '普通用户', '普通论坛用户', 1, '2025-09-17 14:57:54');
INSERT INTO `roles` VALUES (5, 'banned', '封禁用户', '被封禁用户（限制发言/行为）', 1, '2025-09-17 14:57:54');

-- ----------------------------
-- Table structure for UserRoles
-- ----------------------------
DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE `user_roles`  (
  `user_id` bigint UNSIGNED NOT NULL,
  `role_id` bigint UNSIGNED NULL DEFAULT 5,
  `granted_by_user_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '授予者（可空）',
  `effective_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '生效时间',
  `expires_at` timestamp NULL DEFAULT NULL COMMENT '过期时间（可空）',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `activation_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE,
  INDEX `idx_user_roles_role_id`(`role_id` ASC) USING BTREE,
  INDEX `idx_user_roles_expires_at`(`expires_at` ASC) USING BTREE,
  CONSTRAINT `fk_ur_role` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_ur_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户-角色 关系' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of UserRoles
-- ----------------------------
INSERT INTO `user_roles` VALUES (6, 2, NULL, '2025-09-17 15:00:56', NULL, '2025-09-17 15:00:56', NULL);
INSERT INTO `user_roles` VALUES (19, 5, NULL, '2025-09-17 21:17:32', NULL, '2025-09-17 21:17:32', NULL);
INSERT INTO `user_roles` VALUES (76, 5, NULL, '2025-09-17 23:06:39', NULL, '2025-09-17 23:06:39', '936f7b50-3f87-41d7-89c7-5b89efa9dc80');
INSERT INTO `user_roles` VALUES (77, 5, NULL, '2025-09-17 23:32:17', NULL, '2025-09-17 23:32:17', '49ca677d-827c-4c29-8151-88aebcc07323');
INSERT INTO `user_roles` VALUES (78, 5, NULL, '2025-09-17 23:34:53', NULL, '2025-09-17 23:34:53', '45c48f7b-6e9d-4324-82c9-c92fa3476222');
INSERT INTO `user_roles` VALUES (79, 5, NULL, '2025-09-17 23:35:57', NULL, '2025-09-17 23:35:57', 'd09c1f97-d495-4780-ab9d-6f0817fd8528');
INSERT INTO `user_roles` VALUES (80, 5, NULL, '2025-09-17 23:37:20', NULL, '2025-09-17 23:37:20', '7c2920d6-7bd4-4771-b5a4-70ca0202bd15');
INSERT INTO `user_roles` VALUES (81, 5, NULL, '2025-09-17 23:39:49', NULL, '2025-09-17 23:39:49', '1686a59c-4c08-4e03-951e-cee3451b4316');
INSERT INTO `user_roles` VALUES (82, 4, NULL, '2025-09-17 23:42:47', NULL, '2025-09-17 23:42:47', 'd613e4b5-0ae7-4b2e-b7cc-9177bb76c3a0');
INSERT INTO `user_roles` VALUES (88, 4, NULL, '2025-09-18 17:53:11', NULL, '2025-09-18 17:53:11', '0934b445-6b66-4cdb-9d34-b6e8981bf01d');
INSERT INTO `user_roles` VALUES (89, 5, NULL, '2025-09-19 12:23:01', NULL, '2025-09-19 12:23:01', 'dd9078a3-f3fd-4bd6-867f-2919731d3bd4');
INSERT INTO `user_roles` VALUES (90, 4, NULL, '2025-09-19 12:26:07', NULL, '2025-09-19 12:26:07', '29409880-21eb-4bf2-8e87-bfb15f05d2df');
INSERT INTO `user_roles` VALUES (95, 5, NULL, '2025-09-22 19:48:55', NULL, '2025-09-22 19:48:55', 'ab8fb92d-47f4-435d-9ed9-30b24634760c');
INSERT INTO `user_roles` VALUES (165, 5, NULL, '2025-09-23 12:12:21', NULL, '2025-09-23 12:12:21', 'c3875a4d-e7d0-4e3b-a3eb-c36ab4a714ef');
INSERT INTO `user_roles` VALUES (166, 5, NULL, '2025-09-23 12:12:41', NULL, '2025-09-23 12:12:41', '0223fa6a-1380-4e93-9864-a3bb65d07443');
INSERT INTO `user_roles` VALUES (167, 4, NULL, '2025-09-23 12:13:58', NULL, '2025-09-23 12:13:58', 'effdce77-11be-4d23-8d4b-6b0977ba4bac');
INSERT INTO `user_roles` VALUES (169, 5, NULL, '2025-09-23 12:19:23', NULL, '2025-09-23 12:19:23', 'ffd29556-16a5-4e0d-afb0-9deffca8c283');
INSERT INTO `user_roles` VALUES (170, 4, NULL, '2025-09-23 12:20:52', NULL, '2025-09-23 12:20:52', 'b0f5a578-67b4-4965-862c-5232a6840cb4');
INSERT INTO `user_roles` VALUES (171, 4, NULL, '2025-09-23 12:27:26', NULL, '2025-09-23 12:27:26', '2a731e9d-b35b-47a9-89f1-2dc1c5cf7d64');
INSERT INTO `user_roles` VALUES (173, 4, NULL, '2025-09-23 12:39:51', NULL, '2025-09-23 12:39:51', '1b9b578e-bd70-4111-afe1-976623cae896');
INSERT INTO `user_roles` VALUES (174, 4, NULL, '2025-09-23 13:04:55', NULL, '2025-09-23 13:04:55', '0bb8ff53-3879-478e-a443-e0cd94e75de5');
INSERT INTO `user_roles` VALUES (176, 4, NULL, '2025-09-23 21:04:26', NULL, '2025-09-23 21:04:26', '901043fe-d16d-48dc-8807-080751a844ab');
INSERT INTO `user_roles` VALUES (177, 4, NULL, '2025-09-23 21:08:24', NULL, '2025-09-23 21:08:24', '2a2537ce-a50e-44db-b20d-02101d7f263e');
INSERT INTO `user_roles` VALUES (178, 4, NULL, '2025-09-23 16:47:12', NULL, '2025-09-23 16:47:12', 'ee5974c7-fc61-42b9-a23f-81a421eba2b0');
INSERT INTO `user_roles` VALUES (179, 4, NULL, '2025-09-23 17:34:32', NULL, '2025-09-23 17:34:32', '52079daa-1261-4b1a-a7c0-a2a6089b5543');
INSERT INTO `user_roles` VALUES (180, 4, NULL, '2025-09-23 17:41:33', NULL, '2025-09-23 17:41:33', 'dbca2b50-242e-44ea-9879-be86a753dc6c');
INSERT INTO `user_roles` VALUES (181, 5, NULL, '2025-09-24 00:20:33', NULL, '2025-09-24 00:20:33', 'a20274ee-da2a-477b-a2aa-9dfa58f4b848');
INSERT INTO `user_roles` VALUES (183, 5, NULL, '2025-09-24 00:24:29', NULL, '2025-09-24 00:24:29', '498a7212-bdaa-4c61-8dcd-1dfc383342fc');
INSERT INTO `user_roles` VALUES (184, 5, NULL, '2025-09-24 00:24:30', NULL, '2025-09-24 00:24:30', '8068645f-bc7f-40f8-a599-6152268891fc');
INSERT INTO `user_roles` VALUES (186, 5, NULL, '2025-09-24 00:42:16', NULL, '2025-09-24 00:42:16', '25e7a82c-48b7-4356-8846-f3df9d8e36c5');
INSERT INTO `user_roles` VALUES (188, 5, NULL, '2025-09-24 05:04:38', NULL, '2025-09-24 05:04:38', '434dc25d-33ba-4661-be8d-7d73ed9fcdd9');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '昵称',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '邮箱（唯一）',
  `password_hash` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码哈希（如 bcrypt）',
  `room_number` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '南一548' COMMENT '房间号，例如：南一548',
  `room_meter_password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'cy@123' COMMENT '房间电表密码（默认值）',
  `avatar_url` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '头像图片URL',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '账号创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_users_email`(`email` ASC) USING BTREE,
  INDEX `idx_users_room_number`(`room_number` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 189 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '叶一论坛-用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (6, 'luotao', '2547364328@qq.com', '123456789', '南一548', 'cy@123', 'https://ts3.tc.mm.bing.net/th?id=ORMS.0ba398b6d3b28a8a96ca3b0e180d9d5c&pid=Wdp&w=268&h=140&qlt=90&c=1&rs=1&dpr=1&p=0', '2025-09-17 01:08:32');
INSERT INTO `users` VALUES (18, '小明', 'user001@yeyiforum.local', 'pwd001', '南一501', 'cy@123', '', '2025-09-17 21:08:24');
INSERT INTO `users` VALUES (19, '小红', 'user002@yeyiforum.local', 'pwd002', '南二502', 'cy@123', '', '2025-09-17 21:08:24');
INSERT INTO `users` VALUES (20, '小刚', 'user003@yeyiforum.local', 'pwd003', '南三503', 'cy@123', '', '2025-09-17 21:08:24');
INSERT INTO `users` VALUES (21, '小芳', 'user004@yeyiforum.local', 'pwd004', '南四504', 'cy@123', '', '2025-09-17 21:08:24');
INSERT INTO `users` VALUES (22, '阿强', 'user005@yeyiforum.local', 'pwd005', '南五505', 'cy@123', '', '2025-09-17 21:08:24');
INSERT INTO `users` VALUES (23, '阿美', 'user006@yeyiforum.local', 'pwd006', '南一506', 'cy@123', '', '2025-09-17 21:08:24');
INSERT INTO `users` VALUES (24, '王小明', 'user007@yeyiforum.local', 'pwd007', '南二507', 'cy@123', '', '2025-09-17 21:08:24');
INSERT INTO `users` VALUES (25, '李雷', 'user008@yeyiforum.local', 'pwd008', '南三508', 'cy@123', '', '2025-09-17 21:08:24');
INSERT INTO `users` VALUES (26, '韩梅梅', 'user009@yeyiforum.local', 'pwd009', '南四509', 'cy@123', '', '2025-09-17 21:08:24');
INSERT INTO `users` VALUES (28, '李四', 'user011@yeyiforum.local', 'pwd011', '南一511', 'cy@123', '', '2025-09-17 21:08:24');
INSERT INTO `users` VALUES (29, '王五', 'user012@yeyiforum.local', 'pwd012', '南二512', 'cy@123', '', '2025-09-17 21:08:24');
INSERT INTO `users` VALUES (30, '赵六', 'user013@yeyiforum.local', 'pwd013', '南三513', 'cy@123', '', '2025-09-17 21:08:24');
INSERT INTO `users` VALUES (33, '吴九', 'user016@yeyiforum.local', 'pwd016', '南一516', 'cy@123', '', '2025-09-17 21:08:24');
INSERT INTO `users` VALUES (34, '郑十', 'user017@yeyiforum.local', 'pwd017', '南二517', 'cy@123', '', '2025-09-17 21:08:24');
INSERT INTO `users` VALUES (35, '钱多多', 'user018@yeyiforum.local', 'pwd018', '南三518', 'cy@123', '', '2025-09-17 21:08:24');
INSERT INTO `users` VALUES (36, '李想', 'user019@yeyiforum.local', 'pwd019', '南四519', 'cy@123', '', '2025-09-17 21:08:24');
INSERT INTO `users` VALUES (37, '王者', 'user020@yeyiforum.local', 'pwd020', '南五520', 'cy@123', '', '2025-09-17 21:08:24');
INSERT INTO `users` VALUES (38, '梦想家', 'user021@yeyiforum.local', 'pwd021', '南一521', 'cy@123', '', '2025-09-17 21:08:24');
INSERT INTO `users` VALUES (39, '快乐羊', 'user022@yeyiforum.local', 'pwd022', '南二522', 'cy@123', '', '2025-09-17 21:08:24');
INSERT INTO `users` VALUES (40, '程序猿', 'user023@yeyiforum.local', 'pwd023', '南三523', 'cy@123', '', '2025-09-17 21:08:24');
INSERT INTO `users` VALUES (41, '码农甲', 'user024@yeyiforum.local', 'pwd024', '南四524', 'cy@123', '', '2025-09-17 21:08:24');
INSERT INTO `users` VALUES (42, '码农乙', 'user025@yeyiforum.local', 'pwd025', '南五525', 'cy@123', '', '2025-09-17 21:08:24');
INSERT INTO `users` VALUES (43, '北极星', 'user026@yeyiforum.local', 'pwd026', '南一526', 'cy@123', '', '2025-09-17 21:08:24');
INSERT INTO `users` VALUES (44, '南风', 'user027@yeyiforum.local', 'pwd027', '南二527', 'cy@123', '', '2025-09-17 21:08:24');
INSERT INTO `users` VALUES (45, '东篱', 'user028@yeyiforum.local', 'pwd028', '南三528', 'cy@123', '', '2025-09-17 21:08:24');
INSERT INTO `users` VALUES (46, '西桥', 'user029@yeyiforum.local', 'pwd029', '南四529', 'cy@123', '', '2025-09-17 21:08:24');
INSERT INTO `users` VALUES (47, '山海', 'user030@yeyiforum.local', 'pwd030', '南五530', 'cy@123', '', '2025-09-17 21:08:24');
INSERT INTO `users` VALUES (48, '星河', 'user031@yeyiforum.local', 'pwd031', '南一531', 'cy@123', '', '2025-09-17 21:08:24');
INSERT INTO `users` VALUES (49, '流水', 'user032@yeyiforum.local', 'pwd032', '南二532', 'cy@123', '', '2025-09-17 21:08:24');
INSERT INTO `users` VALUES (50, '青石', 'user033@yeyiforum.local', 'pwd033', '南三533', 'cy@123', '', '2025-09-17 21:08:24');
INSERT INTO `users` VALUES (51, '木子', 'user034@yeyiforum.local', 'pwd034', '南四534', 'cy@123', '', '2025-09-17 21:08:24');
INSERT INTO `users` VALUES (52, '橙子', 'user035@yeyiforum.local', 'pwd035', '南五535', 'cy@123', '', '2025-09-17 21:08:24');
INSERT INTO `users` VALUES (53, '柠檬', 'user036@yeyiforum.local', 'pwd036', '南一536', 'cy@123', '', '2025-09-17 21:08:24');
INSERT INTO `users` VALUES (54, '葡萄', 'user037@yeyiforum.local', 'pwd037', '南二537', 'cy@123', '', '2025-09-17 21:08:24');
INSERT INTO `users` VALUES (55, '西瓜', 'user038@yeyiforum.local', 'pwd038', '南三538', 'cy@123', '', '2025-09-17 21:08:24');
INSERT INTO `users` VALUES (56, '橘子', 'user039@yeyiforum.local', 'pwd039', '南四539', 'cy@123', '', '2025-09-17 21:08:24');
INSERT INTO `users` VALUES (57, '蓝天', 'user040@yeyiforum.local', 'pwd040', '南五540', 'cy@123', '', '2025-09-17 21:08:24');
INSERT INTO `users` VALUES (58, '白云', 'user041@yeyiforum.local', 'pwd041', '南一541', 'cy@123', '', '2025-09-17 21:08:24');
INSERT INTO `users` VALUES (59, '黑夜', 'user042@yeyiforum.local', 'pwd042', '南二542', 'cy@123', '', '2025-09-17 21:08:24');
INSERT INTO `users` VALUES (60, '清风', 'user043@yeyiforum.local', 'pwd043', '南三543', 'cy@123', '', '2025-09-17 21:08:24');
INSERT INTO `users` VALUES (61, '明月', 'user044@yeyiforum.local', 'pwd044', '南四544', 'cy@123', '', '2025-09-17 21:08:24');
INSERT INTO `users` VALUES (62, '暖阳', 'user045@yeyiforum.local', 'pwd045', '南五545', 'cy@123', '', '2025-09-17 21:08:24');
INSERT INTO `users` VALUES (63, '晨曦', 'user046@yeyiforum.local', 'pwd046', '南一546', 'cy@123', '', '2025-09-17 21:08:24');
INSERT INTO `users` VALUES (64, '夕阳', 'user047@yeyiforum.local', 'pwd047', '南二547', 'cy@123', '', '2025-09-17 21:08:24');
INSERT INTO `users` VALUES (65, '秋水', 'user048@yeyiforum.local', 'pwd048', '南三548', 'cy@123', '', '2025-09-17 21:08:24');
INSERT INTO `users` VALUES (66, '冬雪', 'user049@yeyiforum.local', 'pwd049', '南四549', 'cy@123', '', '2025-09-17 21:08:24');
INSERT INTO `users` VALUES (67, '春雨', 'user050@yeyiforum.local', 'pwd050', '南五550', 'cy@123', '', '2025-09-17 21:08:24');
INSERT INTO `users` VALUES (68, 'luotao34565', '123438@qq.com', '123456789', '南一548', 'cy@123', '', '2025-09-17 21:45:08');
INSERT INTO `users` VALUES (70, 'luotao34565', '12343866@qq.com', '123456789', '南一548', 'cy@123', '', '2025-09-17 21:49:52');
INSERT INTO `users` VALUES (71, 'luotao8976', '25499998@qq.com', '123456789', '南一548', 'cy@123', '', '2025-09-17 23:01:25');
INSERT INTO `users` VALUES (72, 'luotao8976', '254976898@qq.com', '123456789', '南一548', 'cy@123', '', '2025-09-17 23:02:13');
INSERT INTO `users` VALUES (73, 'luotao8976', '21111111118@qq.com', '123456789', '南一548', 'cy@123', '', '2025-09-17 23:03:34');
INSERT INTO `users` VALUES (74, 'luotao8976', '2177777778@qq.com', '123456789', '南一548', 'cy@123', '', '2025-09-17 23:04:56');
INSERT INTO `users` VALUES (75, 'luotao8976', '21756768@qq.com', '123456789', '南一548', 'cy@123', '', '2025-09-17 23:05:43');
INSERT INTO `users` VALUES (76, 'luotao8976', '21756787@qq.com', '123456789', '南一548', 'cy@123', '', '2025-09-17 23:06:39');
INSERT INTO `users` VALUES (77, 'luotao756', '21755678@qq.com', '123456789', '南一548', 'cy@123', '', '2025-09-17 23:32:17');
INSERT INTO `users` VALUES (78, 'luotao765466', '217545468@qq.com', '123456789', '南一548', 'cy@123', '', '2025-09-17 23:34:53');
INSERT INTO `users` VALUES (79, 'luotao76645666', '21754345348@qq.com', '123456789', '南一548', 'cy@123', '', '2025-09-17 23:35:57');
INSERT INTO `users` VALUES (80, 'luotao5666', '2175434hgj8@qq.com', '123456789', '南一548', 'cy@123', '', '2025-09-17 23:37:20');
INSERT INTO `users` VALUES (81, 'luotao67676', '2175gfdsfggj8@qq.com', '123456789', '南一548', 'cy@123', '', '2025-09-17 23:39:49');
INSERT INTO `users` VALUES (82, 'luota56576', '2175gwqeqw8@qq.com', '123456789', '南一548', 'cy@123', '', '2025-09-17 23:42:47');
INSERT INTO `users` VALUES (88, '发邮件测试', 'ltyuyun01@163.com', '123456789', '南一548', 'cy@123', '', '2025-09-18 17:53:11');
INSERT INTO `users` VALUES (89, 'luotao456346', '3780555240@qq.com', '111111', '南一548', 'cy@123', '', '2025-09-19 12:23:01');
INSERT INTO `users` VALUES (90, '按实际宽度和', 'j9xamvgkk@163.com', '111111', '南一548', 'cy@123', '', '2025-09-19 12:26:07');
INSERT INTO `users` VALUES (95, 'luota56576', '2175gwqsdfdfsdf8@qq.com', '123456789', '南一548', 'cy@123', '', '2025-09-22 19:48:55');
INSERT INTO `users` VALUES (165, 'luotao456346', 'ltyuyun0135453@163.com', '123456789', '南一548', 'cy@123', '', '2025-09-23 12:12:21');
INSERT INTO `users` VALUES (166, 'luotao456346', 'ltyuyu67868@163.com', '123456789', '南一548', 'cy@123', '', '2025-09-23 12:12:41');
INSERT INTO `users` VALUES (167, 'luotao456346', 'ltyu5476456753@163.com', '123456789', '南一548', 'cy@123', '', '2025-09-23 12:13:58');
INSERT INTO `users` VALUES (169, 'luotao456346', 'l53@163.com', '123456789', '南一548', 'cy@123', '', '2025-09-23 12:19:23');
INSERT INTO `users` VALUES (170, 'luota3fdsaf', 'intelligible.spend@linkmail.info', '123456789', '南一548', 'cy@123', '', '2025-09-23 12:20:52');
INSERT INTO `users` VALUES (171, 'test_ajkshdfkaj', 'idyllic.mob@linkmail.info', '123456789', '南一548', 'cy@123', '', '2025-09-23 12:27:26');
INSERT INTO `users` VALUES (173, 'test_ajksesdfrs', 'focused-varahamihira-4171@smail.pw', '123456789', '南一548', 'cy@123', '', '2025-09-23 12:39:51');
INSERT INTO `users` VALUES (174, 'test_ajkjhdsgcvd', 'magical-lovelace-7131@smail.pw', '123456789', '南一548', 'cy@123', '', '2025-09-23 13:04:55');
INSERT INTO `users` VALUES (176, 'luotao4dsfs', 'pedantic-elgamal-4731@smail.pw', '123456789', '南一548', 'cy@123', '', '2025-09-23 21:04:26');
INSERT INTO `users` VALUES (177, 'luotao4dsfs', 'thirsty-solomon-7677@smail.pw', '123456789', '南一548', 'cy@123', '', '2025-09-23 21:08:24');
INSERT INTO `users` VALUES (178, '胡天赐宇宙第一帅', 'nifty-pare-3732@smail.pw', '123456', '南一548', 'cy@123', '', '2025-09-23 16:47:12');
INSERT INTO `users` VALUES (179, '天赐哥天下第一', 'luotaogithubb@163.com', '123456789', '南一548', 'cy@123', '', '2025-09-23 17:34:32');
INSERT INTO `users` VALUES (180, '天赐哥最骚', 'luotaogithubc@163.com', '123456789', '南一548', 'cy@123', '', '2025-09-23 17:41:33');
INSERT INTO `users` VALUES (181, '123123', '113076550@qq.com', 'gsm460514', '南一548', 'cy@123', '', '2025-09-24 00:20:33');
INSERT INTO `users` VALUES (183, 'zxk', 'zhengxukang0110@qq.com', '123456', '南一548', 'cy@123', '', '2025-09-24 00:24:29');
INSERT INTO `users` VALUES (184, '123', '3242574554@qq.com', '123456', '南一548', 'cy@123', '', '2025-09-24 00:24:30');
INSERT INTO `users` VALUES (186, 'Yh', '183000855@qq.com', '123456', '南一548', 'cy@123', '', '2025-09-24 00:42:16');
INSERT INTO `users` VALUES (188, 'luotao4dsfs', 'pensive-mcclintock-0255@smail.pw', '121212', '南一548', 'cy@123', '', '2025-09-24 05:04:38');

SET FOREIGN_KEY_CHECKS = 1;
