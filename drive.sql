/*
Navicat MySQL Data Transfer

Source Server         : fqh
Source Server Version : 50736
Source Host           : 47.108.226.134:3306
Source Database       : drive

Target Server Type    : MYSQL
Target Server Version : 50736
File Encoding         : 65001

Date: 2023-07-06 19:16:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for apply
-- ----------------------------
DROP TABLE IF EXISTS `apply`;
CREATE TABLE `apply` (
  `id` int(11) NOT NULL COMMENT '考试报名申请id',
  `uid` int(11) NOT NULL COMMENT '学员id',
  `state2` int(11) NOT NULL DEFAULT '0' COMMENT '科目二报名状态(0表示不可报名，1可以报名)',
  `state3` int(11) NOT NULL DEFAULT '0' COMMENT '科目三报名状态(0表示不可报名，1可以报名)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of apply
-- ----------------------------

-- ----------------------------
-- Table structure for coach
-- ----------------------------
DROP TABLE IF EXISTS `coach`;
CREATE TABLE `coach` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '教练id',
  `name` varchar(255) NOT NULL COMMENT '姓名',
  `password` varchar(255) NOT NULL DEFAULT '123456' COMMENT '密码',
  `img` varchar(255) DEFAULT NULL COMMENT '头像',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `sex` varchar(255) DEFAULT NULL COMMENT '性别',
  `teaching_time` datetime DEFAULT NULL COMMENT '入职时间',
  `des` varchar(255) DEFAULT NULL COMMENT '简介',
  `phone` varchar(255) NOT NULL COMMENT '电话',
  `subject` int(255) DEFAULT NULL COMMENT '教练培训科目（2表示科目二、3表示科目三）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of coach
-- ----------------------------
INSERT INTO `coach` VALUES ('1', '陈老师', '123456', 'http://localhost:9090/files/1070e0ba98c04eef97b72dac4e6b2562.jpeg', '20', '女', '2023-06-23 18:30:33', '一级教练员', '123123112312', '3');
INSERT INTO `coach` VALUES ('2', '冯启航', '123456', '', '23', '男', '2015-07-10 12:00:00', '一级教练员', '123', '3');
INSERT INTO `coach` VALUES ('7', '阿迪斯', '123456', 'http://localhost:9090/file/1.png', '53', '女', '2014-07-10 16:02:01', '自行车', '231415', '2');

-- ----------------------------
-- Table structure for coach_reservation
-- ----------------------------
DROP TABLE IF EXISTS `coach_reservation`;
CREATE TABLE `coach_reservation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cid` int(11) DEFAULT NULL,
  `time1` int(11) DEFAULT NULL,
  `time2` int(11) DEFAULT NULL,
  `time3` int(11) DEFAULT NULL,
  `time4` int(11) DEFAULT NULL,
  `date` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of coach_reservation
-- ----------------------------
INSERT INTO `coach_reservation` VALUES ('12', '8', '0', '0', '0', '0', '2023-07-06');
INSERT INTO `coach_reservation` VALUES ('13', '2', '0', '0', '0', '0', '2023-07-06');
INSERT INTO `coach_reservation` VALUES ('14', '30', '0', '0', '0', '0', '2023-07-06');
INSERT INTO `coach_reservation` VALUES ('15', '1', '0', '0', '0', '0', '2023-07-06');

-- ----------------------------
-- Table structure for coach_student
-- ----------------------------
DROP TABLE IF EXISTS `coach_student`;
CREATE TABLE `coach_student` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cid` int(11) DEFAULT NULL,
  `uid` int(11) DEFAULT NULL,
  `subject2` int(11) DEFAULT '0',
  `subject3` int(11) DEFAULT '0',
  `cl2` int(11) DEFAULT '0',
  `cl3` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of coach_student
-- ----------------------------
INSERT INTO `coach_student` VALUES ('30', '1', '8', '9000', '36014000', '0', '1');
INSERT INTO `coach_student` VALUES ('32', '1', '5', '0', '0', '0', '0');
INSERT INTO `coach_student` VALUES ('33', '1', '9', '0', '0', '0', '0');

-- ----------------------------
-- Table structure for code
-- ----------------------------
DROP TABLE IF EXISTS `code`;
CREATE TABLE `code` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `type` int(255) DEFAULT '1' COMMENT '对应了用户的类型 1是学生 2是教练',
  `code` varchar(255) DEFAULT NULL COMMENT '验证码',
  `phone` varchar(255) DEFAULT NULL COMMENT '电话',
  `uid` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of code
-- ----------------------------
INSERT INTO `code` VALUES ('3', '1', null, '18983219046', '2');
INSERT INTO `code` VALUES ('4', '1', null, '19112330951', '5');
INSERT INTO `code` VALUES ('5', '1', null, '18184015092', '8');

-- ----------------------------
-- Table structure for exam
-- ----------------------------
DROP TABLE IF EXISTS `exam`;
CREATE TABLE `exam` (
  `id` int(11) NOT NULL COMMENT '考试id',
  `uid` int(11) NOT NULL COMMENT '学员id',
  `name` varchar(255) DEFAULT NULL COMMENT '考试名称',
  `state` varchar(255) DEFAULT NULL COMMENT '考试状态',
  `code` int(11) DEFAULT NULL COMMENT '分数',
  `type` int(11) DEFAULT '0' COMMENT '0为提交，1为未提交',
  `codestate` varchar(255) DEFAULT NULL COMMENT '各科分数情况',
  `trueid` int(11) NOT NULL AUTO_INCREMENT,
  `outdata` int(11) NOT NULL DEFAULT '0' COMMENT '中途退出次数',
  PRIMARY KEY (`trueid`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of exam
-- ----------------------------
INSERT INTO `exam` VALUES ('1', '9', null, '[{\"answer\":\"\",\"id\":3,\"note\":\"\",\"state\":0,\"type\":0},{\"answer\":\"\",\"id\":4,\"note\":\"\",\"state\":0,\"type\":0},{\"answer\":\"\",\"id\":10,\"note\":\"\",\"state\":0,\"type\":0},{\"answer\":\"\",\"id\":17,\"note\":\"\",\"state\":0,\"type\":1}]', null, '0', null, '4', '0');
INSERT INTO `exam` VALUES ('2', '9', null, '[{\"id\":11,\"answer\":[\"A\",\"D\"],\"state\":1,\"note\":\"\",\"type\":1},{\"id\":14,\"answer\":\"B\",\"state\":1,\"note\":\"\",\"type\":0},{\"id\":15,\"answer\":\"D\",\"state\":1,\"note\":\"\",\"type\":0},{\"id\":16,\"answer\":[\"B\",\"C\"],\"state\":1,\"note\":\"\",\"type\":1}]', null, '0', '', '5', '0');
INSERT INTO `exam` VALUES ('1', '5', null, '[{\"answer\":\"\",\"id\":3,\"note\":\"\",\"state\":0,\"type\":0},{\"answer\":\"\",\"id\":4,\"note\":\"\",\"state\":0,\"type\":0},{\"answer\":\"\",\"id\":10,\"note\":\"\",\"state\":0,\"type\":0},{\"answer\":\"\",\"id\":17,\"note\":\"\",\"state\":0,\"type\":1}]', null, '0', null, '6', '0');
INSERT INTO `exam` VALUES ('2', '5', null, '[{\"answer\":\"\",\"id\":11,\"note\":\"\",\"state\":0,\"type\":1},{\"answer\":\"\",\"id\":14,\"note\":\"\",\"state\":0,\"type\":0},{\"answer\":\"\",\"id\":15,\"note\":\"\",\"state\":0,\"type\":0},{\"answer\":\"\",\"id\":16,\"note\":\"\",\"state\":0,\"type\":1}]', null, '0', null, '7', '0');
INSERT INTO `exam` VALUES ('1', '8', null, '[{\"answer\":\"\",\"id\":3,\"note\":\"\",\"state\":0,\"type\":0},{\"answer\":\"\",\"id\":4,\"note\":\"\",\"state\":0,\"type\":0},{\"answer\":\"\",\"id\":10,\"note\":\"\",\"state\":0,\"type\":0},{\"answer\":\"\",\"id\":17,\"note\":\"\",\"state\":0,\"type\":1}]', null, '0', null, '8', '0');
INSERT INTO `exam` VALUES ('2', '8', null, '[{\"answer\":\"\",\"id\":11,\"note\":\"\",\"state\":0,\"type\":1},{\"answer\":\"\",\"id\":14,\"note\":\"\",\"state\":0,\"type\":0},{\"answer\":\"\",\"id\":15,\"note\":\"\",\"state\":0,\"type\":0},{\"answer\":\"\",\"id\":16,\"note\":\"\",\"state\":0,\"type\":1}]', null, '0', null, '9', '0');

-- ----------------------------
-- Table structure for exam_paper
-- ----------------------------
DROP TABLE IF EXISTS `exam_paper`;
CREATE TABLE `exam_paper` (
  `id` int(11) NOT NULL COMMENT '考试-试卷id',
  `xid` varchar(255) DEFAULT '' COMMENT '试卷paper的id',
  `subject` varchar(255) DEFAULT NULL COMMENT '试题科目，科目一、科目四',
  `btime` datetime DEFAULT NULL,
  `etime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of exam_paper
-- ----------------------------
INSERT INTO `exam_paper` VALUES ('1', '[{\"id\":3},{\"id\":4},{\"id\":10},{\"id\":17}]', '科目一', '2023-07-06 13:20:00', '2023-07-07 13:20:06');
INSERT INTO `exam_paper` VALUES ('2', '[{\"id\":11},{\"id\":14},{\"id\":15},{\"id\":16}]', '科目四', '2023-07-07 13:20:13', '2023-07-07 14:20:16');

-- ----------------------------
-- Table structure for files
-- ----------------------------
DROP TABLE IF EXISTS `files`;
CREATE TABLE `files` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) DEFAULT NULL COMMENT '文件名称',
  `type` varchar(255) DEFAULT NULL COMMENT '文件类型',
  `size` bigint(20) DEFAULT NULL COMMENT '文件大小(kb)',
  `url` varchar(255) DEFAULT NULL COMMENT '下载链接',
  `md5` varchar(255) DEFAULT NULL COMMENT '文件md5',
  `is_delete` tinyint(1) DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of files
-- ----------------------------
INSERT INTO `files` VALUES ('1', '4ffde0a2faa6f7cd9cd88bbc9778f0de.jpeg', 'jpeg', '88', 'http://localhost:9090/file/1070e0ba98c04eef97b72dac4e6b2562.jpeg', '59adeb97bb56df00bdac5c39c1b4e0b9', '0');
INSERT INTO `files` VALUES ('2', '2fd1c70f0234ddd1ff8ffe0349c87710.jpeg', 'jpeg', '222', 'http://localhost:9090/file/7d58266e46ba42d380a85644aebc9194.jpeg', '51679603978403b44a3ff8c1cd6f2b91', '0');
INSERT INTO `files` VALUES ('3', '0005018384903087_b.jpg', 'jpg', '187', 'http://localhost:9090/file/19963fef98e846aea52b4fc49361e4bb.jpg', '7b2c532e23797f58600c61f2d352531c', '0');
INSERT INTO `files` VALUES ('4', '0b4ab012cc1d72912410b60eb28b3103.jpeg', 'jpeg', '3', 'http://localhost:9090/file/511f4249586a49cb855f2ffda6889897.jpeg', '475d134332e37a1d1fcdd1bf3f34d931', '0');

-- ----------------------------
-- Table structure for pass
-- ----------------------------
DROP TABLE IF EXISTS `pass`;
CREATE TABLE `pass` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL COMMENT '用户id',
  `subject1` int(11) DEFAULT '0' COMMENT '科目一是否合格',
  `subject2` int(11) DEFAULT '0',
  `subject3` int(11) DEFAULT '0',
  `subject4` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pass
-- ----------------------------

-- ----------------------------
-- Table structure for progress
-- ----------------------------
DROP TABLE IF EXISTS `progress`;
CREATE TABLE `progress` (
  `uid` int(11) NOT NULL COMMENT '学员id',
  `tid` int(11) NOT NULL COMMENT '任务id',
  `videostate` varchar(255) DEFAULT NULL COMMENT '视频完成情况',
  `filestate` varchar(255) DEFAULT NULL COMMENT '文档完成情况',
  `teststate` varchar(255) DEFAULT NULL COMMENT '模拟考试完成情况',
  `drivestate` int(11) NOT NULL DEFAULT '0' COMMENT '考试资格',
  `type` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of progress
-- ----------------------------
INSERT INTO `progress` VALUES ('5', '1', '[{\"id\":1,\"answer\":17,\"state\":1,\"note\":\"\",\"type\":0},{\"id\":2,\"answer\":8,\"state\":1,\"note\":\"\",\"type\":0}]', null, '[{\"id\":3,\"answer\":\"C\",\"state\":1,\"note\":\"\",\"type\":0},{\"id\":4,\"answer\":\"A\",\"state\":0,\"note\":\"\",\"type\":0},{\"id\":10,\"answer\":\"\",\"state\":0,\"note\":\"\",\"type\":0},{\"id\":17,\"answer\":[\"A\",\"B\",\"D\"],\"state\":1,\"note\":\"\",\"type\":1}]', '0', null);
INSERT INTO `progress` VALUES ('5', '2', null, null, '[{\"id\":11,\"answer\":[\"A\",\"D\"],\"state\":1,\"note\":\"\",\"type\":1},{\"id\":14,\"answer\":\"\",\"state\":0,\"note\":\"\",\"type\":0},{\"id\":15,\"answer\":\"D\",\"state\":0,\"note\":\"\",\"type\":0},{\"id\":16,\"answer\":[],\"state\":0,\"note\":\"\",\"type\":1}]', '0', null);
INSERT INTO `progress` VALUES ('8', '1', '[{\"id\":1,\"answer\":38,\"state\":1,\"note\":\"\"},{\"id\":2,\"answer\":18,\"state\":1,\"note\":\"\"}]', null, '[{\"id\":3,\"answer\":\"C\",\"state\":1,\"note\":\"\",\"type\":0},{\"id\":4,\"answer\":\"A\",\"state\":1,\"note\":\"\",\"type\":0},{\"id\":10,\"answer\":\"A\",\"state\":1,\"note\":\"\",\"type\":0},{\"id\":17,\"answer\":[\"A\",\"B\",\"D\"],\"state\":1,\"note\":\"\",\"type\":1}]', '0', null);
INSERT INTO `progress` VALUES ('8', '2', null, null, '[{\"answer\":\"\",\"id\":11,\"note\":\"\",\"state\":0,\"type\":1},{\"answer\":\"\",\"id\":14,\"note\":\"\",\"state\":0,\"type\":0},{\"answer\":\"\",\"id\":15,\"note\":\"\",\"state\":0,\"type\":0},{\"answer\":\"\",\"id\":16,\"note\":\"\",\"state\":0,\"type\":1}]', '0', null);
INSERT INTO `progress` VALUES ('9', '1', '[{\"id\":1,\"answer\":3,\"state\":0,\"note\":\"\"},{\"id\":2,\"answer\":0,\"state\":0,\"note\":\"\"}]', null, '[{\"answer\":\"\",\"id\":3,\"note\":\"\",\"state\":0,\"type\":0},{\"answer\":\"\",\"id\":4,\"note\":\"\",\"state\":0,\"type\":0},{\"answer\":\"\",\"id\":10,\"note\":\"\",\"state\":0,\"type\":0},{\"answer\":\"\",\"id\":17,\"note\":\"\",\"state\":0,\"type\":1}]', '0', null);
INSERT INTO `progress` VALUES ('9', '2', null, null, '[{\"id\":11,\"answer\":[\"A\",\"D\"],\"state\":1,\"note\":\"\",\"type\":1},{\"id\":14,\"answer\":\"B\",\"state\":1,\"note\":\"\",\"type\":0},{\"id\":15,\"answer\":\"D\",\"state\":1,\"note\":\"\",\"type\":0},{\"id\":16,\"answer\":[\"A\",\"D\"],\"state\":1,\"note\":\"\",\"type\":1}]', '0', null);

-- ----------------------------
-- Table structure for student_reservation
-- ----------------------------
DROP TABLE IF EXISTS `student_reservation`;
CREATE TABLE `student_reservation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cid` int(11) DEFAULT NULL,
  `uid` int(11) DEFAULT NULL,
  `start_time` varchar(255) DEFAULT NULL,
  `end_time` varchar(255) DEFAULT NULL,
  `date` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student_reservation
-- ----------------------------

-- ----------------------------
-- Table structure for study_time
-- ----------------------------
DROP TABLE IF EXISTS `study_time`;
CREATE TABLE `study_time` (
  `id` int(11) NOT NULL COMMENT '学习时间id',
  `uid` int(11) NOT NULL COMMENT '学员id',
  `type` int(11) NOT NULL COMMENT '类型',
  `bTime` datetime DEFAULT NULL COMMENT '任务开始时间',
  `eTime` datetime DEFAULT NULL COMMENT '任务结束时间',
  `total` varchar(255) DEFAULT NULL COMMENT '当前打卡学习时长',
  `popTime` datetime DEFAULT NULL COMMENT '检测弹窗跳出时间',
  `closeTime` datetime DEFAULT NULL COMMENT '检测弹窗关闭时间',
  PRIMARY KEY (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of study_time
-- ----------------------------

-- ----------------------------
-- Table structure for super_manager
-- ----------------------------
DROP TABLE IF EXISTS `super_manager`;
CREATE TABLE `super_manager` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '超级管理员id',
  `name` varchar(255) NOT NULL COMMENT '姓名',
  `password` varchar(255) NOT NULL DEFAULT '123456' COMMENT '密码',
  `avatar` varchar(255) DEFAULT NULL COMMENT '管理员头像',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of super_manager
-- ----------------------------
INSERT INTO `super_manager` VALUES ('1', '欧皇', '123456', 'http://localhost:9090/files/1070e0ba98c04eef97b72dac4e6b2562.jpeg');

-- ----------------------------
-- Table structure for task
-- ----------------------------
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '任务id',
  `subject` varchar(255) DEFAULT NULL COMMENT '科目类别（科目一、科目四）',
  `video` varchar(255) DEFAULT NULL COMMENT '视频集合',
  `file` varchar(255) DEFAULT NULL COMMENT '文档集合',
  `test` varchar(255) DEFAULT NULL COMMENT '模拟考试集合',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of task
-- ----------------------------
INSERT INTO `task` VALUES ('1', '科目一', '[{\"id\":1},{\"id\":2}]', null, '[{\"id\":3},{\"id\":4},{\"id\":10},{\"id\":17}]');
INSERT INTO `task` VALUES ('2', '科目四', '', null, '[{\"id\":11},{\"id\":14},{\"id\":15},{\"id\":16}]');
INSERT INTO `task` VALUES ('3', '科目三', '[{\"id\":1},{\"id\":3},{\"id\":4},{\"id\":5}]', null, '[{\"id\":3},{\"id\":4},{\"id\":10},{\"id\":17}]');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '学员id',
  `name` varchar(255) NOT NULL COMMENT '姓名',
  `sex` varchar(255) DEFAULT NULL COMMENT '性别',
  `phone` varchar(255) DEFAULT NULL COMMENT '电话',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `img` varchar(255) DEFAULT NULL COMMENT '头像',
  `cid` int(11) DEFAULT NULL COMMENT '教练id',
  `graduate` int(11) NOT NULL DEFAULT '0' COMMENT '1表示已经毕业，0未毕业',
  `password` varchar(255) NOT NULL DEFAULT '123456' COMMENT '密码',
  `idnumber` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '陈老师', '女', '123123112312', '22', 'http://localhost:9090/static/avatar/meinv.jpg', '1', '0', '123456', '1111');
INSERT INTO `user` VALUES ('3', '冯启航', '男', '123', '22', null, '2', '0', '123456', '121212');
INSERT INTO `user` VALUES ('4', '阿迪斯', '女', '231415', '21', null, '7', '0', '123456', '232323');
INSERT INTO `user` VALUES ('5', '雷岩', '男', '19112330951', '21', null, null, '0', '123456', '88888');
INSERT INTO `user` VALUES ('8', 'zfy', '男', '18184015092', '18', null, null, '0', 'qwer', '2222');
INSERT INTO `user` VALUES ('9', '6986986', '男', null, '21', null, null, '0', '123456', '55555');

-- ----------------------------
-- Table structure for user_files
-- ----------------------------
DROP TABLE IF EXISTS `user_files`;
CREATE TABLE `user_files` (
  `id` int(11) NOT NULL COMMENT '报名材料id',
  `uid` int(11) NOT NULL COMMENT '对应学员的学员id',
  `name` varchar(255) NOT NULL COMMENT '材料名',
  `type` varchar(255) DEFAULT NULL COMMENT '材料文件类型',
  `size` bigint(20) DEFAULT NULL COMMENT '材料文件大小',
  `url` varchar(255) DEFAULT NULL COMMENT '材料文件路径',
  `md5` varchar(255) DEFAULT NULL COMMENT '材料文件标识符',
  `is_delete` int(11) NOT NULL DEFAULT '0' COMMENT '文件是否删除（逻辑删除）',
  `is_pass` int(11) NOT NULL DEFAULT '0' COMMENT '表示该报名材料对应的学员报告是否通过（1表示通过）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_files
-- ----------------------------
INSERT INTO `user_files` VALUES ('1', '1', 'user', null, null, 'http://localhost:9876/file/c3394fbaee074a089fd9fa1deb132373.png', null, '0', '1');

-- ----------------------------
-- Table structure for video
-- ----------------------------
DROP TABLE IF EXISTS `video`;
CREATE TABLE `video` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '学习视频id',
  `name` varchar(255) NOT NULL COMMENT '视频名称',
  `size` varchar(255) DEFAULT NULL COMMENT '视频大小',
  `url` varchar(255) DEFAULT NULL COMMENT '视频路径',
  `md5` varchar(255) DEFAULT NULL COMMENT '视频唯一标识符',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '视频是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of video
-- ----------------------------
INSERT INTO `video` VALUES ('1', '测试视频', null, 'http://localhost:8080/static/video/sp.mp4', '', '0');
INSERT INTO `video` VALUES ('2', '星空', null, 'http://localhost:8080/static/video/sp2.mp4', null, '0');
INSERT INTO `video` VALUES ('3', '2.png', '7', 'http://localhost:9876/file/c3394fbaee074a089fd9fa1deb132373.png', '803660dce2214b412ccdb8fa829e3fff', '0');
INSERT INTO `video` VALUES ('4', '3.png', '1', 'http://localhost:9876/file/cab88e77fd4344fdb4fca21f070e2e74.png', 'b83677f2a1ed10f1fb8d99c1b4221135', '0');
INSERT INTO `video` VALUES ('5', '3.png', '1', 'http://localhost:9876/file/cab88e77fd4344fdb4fca21f070e2e74.png', 'b83677f2a1ed10f1fb8d99c1b4221135', '0');
INSERT INTO `video` VALUES ('6', '2.png', '7', 'http://localhost:9876/file/c3394fbaee074a089fd9fa1deb132373.png', '803660dce2214b412ccdb8fa829e3fff', '0');
INSERT INTO `video` VALUES ('7', '3.png', '1', 'http://localhost:9876/file/cab88e77fd4344fdb4fca21f070e2e74.png', 'b83677f2a1ed10f1fb8d99c1b4221135', '0');
INSERT INTO `video` VALUES ('8', '2.png', '7', 'http://localhost:9876/file/c3394fbaee074a089fd9fa1deb132373.png', '803660dce2214b412ccdb8fa829e3fff', '0');

-- ----------------------------
-- Table structure for xzt
-- ----------------------------
DROP TABLE IF EXISTS `xzt`;
CREATE TABLE `xzt` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question` varchar(255) DEFAULT NULL,
  `a` varchar(255) DEFAULT NULL,
  `b` varchar(255) DEFAULT NULL,
  `c` varchar(255) DEFAULT NULL,
  `d` varchar(255) DEFAULT NULL,
  `answer` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `analysis` varchar(255) DEFAULT NULL,
  `subject` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xzt
-- ----------------------------
INSERT INTO `xzt` VALUES ('3', '以下哪个英雄是女性？', '泰隆', '盖伦', '卡莎', '德莱厄斯', 'C', '单选题', '卡莎是卡萨丁的女儿，是女性。其他三人都是男性。选C。', '科目一');
INSERT INTO `xzt` VALUES ('4', '以下哪个是《英雄联盟》的简称？', 'LOL', 'LPL', 'LDL', 'LCK', 'A', '单选题', '无', '科目一');
INSERT INTO `xzt` VALUES ('10', '1+1=？', '2', '3', '4', '1', 'A', '单选题', '1+1=2', '科目一');
INSERT INTO `xzt` VALUES ('11', '下面哪些学科属于工科', '计算机', '高等数学', '线性代数', '人工智能', 'AD', '多选题', '属于工科的是计算机和人工智能，其余两个学科都是理科', '科目四');
INSERT INTO `xzt` VALUES ('12', '以下属于违禁品的有哪些？', '方便面', '烟花、爆竹', '手机', '枪支', 'BD', '多选题', '无', '科目一');
INSERT INTO `xzt` VALUES ('13', '555', '55', '55', '5', '55', 'B', '单选题', '5', '科目一');
INSERT INTO `xzt` VALUES ('14', '9+9=？', '17', '18', '19', '20', 'B', '单选题', '9+9=18', '科目四');
INSERT INTO `xzt` VALUES ('15', '交警的电话是什么', '110', '120', '119', '122', 'D', '单选题', '122是交警的电话', '科目四');
INSERT INTO `xzt` VALUES ('16', '英雄联盟里面有哪些英雄属于诺克萨斯', '盖伦', '德莱厄斯', '斯维因', '亚索', 'BC', '多选题', '无', '科目四');
INSERT INTO `xzt` VALUES ('17', '下面哪些英雄属于弗雷尔卓德', '艾希', '泰达米尔', '德莱厄斯', '沃利贝尔', 'ABD', '多选题', '艾希和蛮王还有狗熊都是弗雷尔卓德的', '科目一');
INSERT INTO `xzt` VALUES ('29', '英雄联盟里面有哪些英雄属于诺克萨斯', '盖伦', '德莱厄斯', '斯维因', '亚索', 'BC', '多选题', '无', '科目四');
INSERT INTO `xzt` VALUES ('37', '1+1=？', '2', '3', '4', '1', 'A', '单选题', '1+1=2', '科目一');
INSERT INTO `xzt` VALUES ('38', '9+9=？', '17', '18', '19', '20', 'B', '单选题', '9+9=18', '科目四');
DROP TRIGGER IF EXISTS `delete`;
DELIMITER ;;
CREATE TRIGGER `delete` AFTER DELETE ON `user` FOR EACH ROW begin
DELETE FROM progress WHERE progress.uid=Old.id;
end
;;
DELIMITER ;
