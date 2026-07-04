-- MySQL dump 10.13  Distrib 5.7.3-m13, for Win64 (x86_64)
--
-- Host: localhost    Database: eshop
-- ------------------------------------------------------
-- Server version	5.7.3-m13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `address` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '地址id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `address` varchar(255) NOT NULL COMMENT '地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `goods`
--

DROP TABLE IF EXISTS `goods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `goods_type_id` int(11) NOT NULL COMMENT '商品类型id',
  `goods_name` varchar(255) NOT NULL COMMENT '商品名',
  `price` decimal(19,4) NOT NULL COMMENT '单价',
  `brand` varchar(255) NOT NULL COMMENT '品牌',
  `shelves` tinyint(1) NOT NULL COMMENT '是否上架，1表示已上架，0表示为上架',
  `img` longtext COMMENT '图片base64编码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `goods`
--

LOCK TABLES `goods` WRITE;
/*!40000 ALTER TABLE `goods` DISABLE KEYS */;
/*!40000 ALTER TABLE `goods` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `goods_type`
--

DROP TABLE IF EXISTS `goods_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `goods_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品类型id',
  `type_name` varchar(255) NOT NULL COMMENT '类型名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `goods_type`
--

LOCK TABLES `goods_type` WRITE;
/*!40000 ALTER TABLE `goods_type` DISABLE KEYS */;
INSERT INTO `goods_type` VALUES (1,'家电'),(2,'生活用品'),(3,'食品'),(4,'学习用具'),(5,'服装服饰'),(6,'运动器材'),(7,'手机电脑');
/*!40000 ALTER TABLE `goods_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父级ID',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `title` varchar(100) DEFAULT NULL COMMENT '菜单名称',
  `level` int(11) DEFAULT NULL COMMENT '菜单级数',
  `sort` int(11) DEFAULT NULL COMMENT '菜单排序',
  `name` varchar(100) DEFAULT NULL COMMENT '前端名称',
  `icon` varchar(200) DEFAULT NULL COMMENT '前端图标',
  `hidden` int(11) DEFAULT NULL COMMENT '前端隐藏',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu`
--

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT INTO `menu` VALUES (2,0,'2020-02-02 14:51:50','商品列表',0,0,'product','product-list',1),(3,1,'2020-02-02 14:52:44','添加商品',1,0,'addProduct','product-add',0),(4,1,'2020-02-02 14:53:51','商品分类',1,0,'productCate','product-cate',0),(5,1,'2020-02-02 14:54:51','商品类型',1,0,'productAttr','product-attr',0),(6,1,'2020-02-02 14:56:29','品牌管理',1,0,'brand','product-brand',0),(8,7,'2020-02-02 16:55:18','订单列表',1,0,'order','product-list',0),(9,7,'2020-02-02 16:56:46','订单设置',1,0,'orderSetting','order-setting',0),(10,7,'2020-02-02 16:57:39','退货申请处理',1,0,'returnApply','order-return',0),(11,7,'2020-02-02 16:59:40','退货原因设置',1,0,'returnReason','order-return-reason',0),(12,0,'2020-02-03 16:20:16','营销',0,0,'sms','DataBoard',1),(13,47,'2020-02-04 16:19:22','小酸再',0,53,'务对较候它','http://dummyimage.com/100x100',99),(14,12,'2020-02-07 16:22:38','品牌推荐',1,0,'homeBrand','product-brand',0),(16,12,'2020-02-07 16:22:38','品牌推荐',1,6,'homeBrand','product-brand',0),(17,12,'2020-02-07 16:23:14','新品推荐',1,0,'homeNew','sms-new',0),(18,12,'2020-02-07 16:26:38','人气推荐',1,0,'homeHot','sms-hot',0),(19,12,'2020-02-07 16:28:16','专题推荐',1,0,'homeSubject','sms-subject',0),(20,12,'2020-02-07 16:28:42','广告列表',1,0,'homeAdvertise','sms-ad',0),(21,0,'2020-02-03 16:20:16','权限',0,0,'ums','Tools',0),(22,21,'2020-02-07 16:29:51','用户列表',1,0,'admin','ums-admin',0),(23,21,'2020-02-07 16:30:13','角色列表',1,0,'role','ums-role',0),(24,21,'2020-02-07 16:30:53','菜单列表',1,0,'menu','ums-menu',0),(25,21,'2020-02-07 16:31:13','资源列表',1,0,'resource','ums-resource',0),(31,39,'2022-07-30 23:15:45','商品列表',1,0,'product','study',0),(32,39,'2022-07-30 23:16:41','添加商品',1,0,'addProduct','study',0),(34,39,'2022-07-30 23:17:38','分类管理',1,0,'productAttr','study',0),(36,0,'2020-02-03 16:20:16','数据统计',0,0,'report','DataLine',0),(38,0,'2020-02-03 16:20:16','购物车',0,0,'shoppingCart','ShoppingCartFull',0),(39,0,'2020-02-03 16:20:16','商品管理',0,0,'product','Shop',0),(40,0,'2020-02-03 16:20:16','订单管理',0,0,'oeder','ShoppingBag',0),(41,40,'2022-07-30 22:51:16','订单管理',1,0,'order','study',0),(61,38,'2022-07-30 21:54:03','购物车',1,0,'shoppingCart','study',0),(62,81,'2022-07-30 21:54:28','心办家总确在节',0,22,'内精律提口回','http://dummyimage.com/100x100',75),(71,36,'2022-08-04 19:43:57','数据统计',1,0,'report','study',0),(73,2,'2022-08-11 08:46:02','111模块',1,1,'my','#',0),(74,2,'2022-08-12 09:34:23','111模块',1,1,'my','#',0),(75,2,'2022-08-12 09:42:18','111模块',1,1,'my','#',0),(76,2,'2022-08-12 09:44:30','111模块',1,1,'my','#',0),(77,2,'2022-08-12 10:04:20','111模块',1,1,'my','#',0),(78,2,'2022-08-13 11:13:01','111模块',1,1,'my','#',0),(79,2,'2022-08-13 11:14:18','111模块',1,1,'my','#',0),(80,2,'2022-08-14 09:32:11','111模块',1,1,'my','#',0),(81,2,'2022-08-14 09:35:51','111模块',1,1,'my','#',0),(82,2,'2022-08-14 09:43:24','111模块',1,1,'my','#',0),(83,2,'2022-08-14 09:45:59','111模块',1,1,'my','#',0),(84,2,'2022-08-14 09:53:14','111模块',1,1,'my','#',0);
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `address_id` int(11) DEFAULT NULL COMMENT '地址id',
  `sum` decimal(19,4) NOT NULL COMMENT '金额总数',
  `deliver` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否发货，1表示已发货，0表示未发货',
  `refund` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否退货，1表示完成退货，0表示未退货',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_info`
--

DROP TABLE IF EXISTS `order_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单信息id',
  `goods_id` int(11) NOT NULL COMMENT '商品id',
  `amount` int(11) NOT NULL DEFAULT '0' COMMENT '订购商品数量',
  `order_id` int(11) NOT NULL COMMENT '订单id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_info`
--

LOCK TABLES `order_info` WRITE;
/*!40000 ALTER TABLE `order_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resource`
--

DROP TABLE IF EXISTS `resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `name` varchar(200) DEFAULT NULL COMMENT '资源名称',
  `permission` varchar(200) DEFAULT NULL COMMENT '资源权限标识符',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `category_id` bigint(20) DEFAULT NULL COMMENT '资源分类ID(用户管理1 权限模块2 商品模块3 订单模块4 数据统计5 其他模块6)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resource`
--

LOCK TABLES `resource` WRITE;
/*!40000 ALTER TABLE `resource` DISABLE KEYS */;
INSERT INTO `resource` VALUES (1,'2022-07-31 16:53:25','分页搜索人员','/userInfo/getPage','分页搜索人员',1),(2,'2022-07-31 17:52:50','创建菜单','/menu/create','创建菜单',6),(3,'2022-07-31 17:54:03','修改后台菜单','/menu/update','修改后台菜单',6),(4,'2022-07-31 17:54:44','根据ID删除后台菜单','/menu/delete','根据ID删除后台菜单',6),(5,'2022-07-31 17:55:11','修改菜单显示状态','/menu/updateHidden','修改菜单显示状态',6),(6,'2022-07-31 17:56:38','更新订单','/order/updateOrder','更新订单',4),(8,'2022-08-21 14:46:50','添加用户','/userInfo/create','添加用户',1),(12,'2022-08-06 10:30:11','根据id删除用户','/userInfo/delete','删除用户',1),(13,'2022-08-06 10:30:48','更新用户信息','/userInfo/update','更新用户信息',1),(16,'2022-08-06 11:41:49','获取角色信息','/role/getPage','获取角色信息',2),(17,'2022-08-06 11:42:07','更新角色信息','/role/update','更新角色信息',2),(18,'2022-08-06 11:42:26','插入角色','/role/insert','插入角色',2),(19,'2022-08-06 11:42:43','删除角色','/role/delete','无',2),(20,'2022-08-06 11:43:00','停用/启用角色','/role/statusUpdate','无',2),(21,'2022-08-06 11:43:18','分配角色','/role/allocate','无',2),(22,'2022-08-06 11:43:43','分配资源','/role/allocate_resource','无',2),(23,'2022-08-06 11:43:59','分配菜单','/role/allocate_menu','无',2),(24,'2022-08-06 11:44:46','添加资源','/resource/create','无',2),(25,'2022-08-06 11:45:02','更新资源信息','/resource/update','无',2),(26,'2022-08-06 11:45:58','删除资源','/resource/delete','无',2),(27,'2022-08-06 11:46:32','获取资源列表','/resource/getPage','无',2);
/*!40000 ALTER TABLE `resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `name` varchar(32) NOT NULL COMMENT '角色名',
  `remark` varchar(100) NOT NULL COMMENT '角色描述',
  `status` int(11) NOT NULL COMMENT '是否启用，0正常，1停用',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (3,'超级管理员','test',0,'2022-07-31 17:49:08'),(12,'管理员','数据统计，商品管理，订单管理，购物车',0,'2022-08-05 23:28:05'),(13,'普通用户','购物车',0,'2022-08-05 23:28:52'),(14,'test','test',0,'2022-08-21 10:39:04');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_menu`
--

DROP TABLE IF EXISTS `role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `menu_id` int(11) DEFAULT NULL COMMENT '菜单id',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '表状态 0 正常 1停用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=361 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_menu`
--

LOCK TABLES `role_menu` WRITE;
/*!40000 ALTER TABLE `role_menu` DISABLE KEYS */;
INSERT INTO `role_menu` VALUES (221,13,38,0),(241,12,40,0),(325,3,2,0),(326,3,12,0),(327,3,21,0),(328,3,36,0),(329,3,38,0),(330,3,39,0),(331,3,40,0),(332,3,73,0),(333,3,74,0),(334,3,75,0),(335,3,76,0),(336,3,77,0),(337,3,78,0),(338,3,79,0),(339,3,80,0),(340,3,81,0),(341,3,62,0),(342,3,82,0),(343,3,83,0),(344,3,84,0),(345,3,14,0),(346,3,16,0),(347,3,17,0),(348,3,18,0),(349,3,19,0),(350,3,20,0),(351,3,22,0),(352,3,23,0),(353,3,24,0),(354,3,25,0),(355,3,71,0),(356,3,61,0),(357,3,31,0),(358,3,32,0),(359,3,34,0),(360,3,41,0);
/*!40000 ALTER TABLE `role_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_resource`
--

DROP TABLE IF EXISTS `role_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `resource_id` int(11) NOT NULL COMMENT '资源对应id',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '表状态 0正常 1停用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=304 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_resource`
--

LOCK TABLES `role_resource` WRITE;
/*!40000 ALTER TABLE `role_resource` DISABLE KEYS */;
INSERT INTO `role_resource` VALUES (261,12,6,0),(283,3,1,0),(284,3,2,0),(285,3,3,0),(286,3,4,0),(287,3,5,0),(288,3,6,0),(289,3,8,0),(290,3,12,0),(291,3,13,0),(292,3,16,0),(293,3,17,0),(294,3,18,0),(295,3,19,0),(296,3,20,0),(297,3,21,0),(298,3,22,0),(299,3,23,0),(300,3,24,0),(301,3,25,0),(302,3,26,0),(303,3,27,0);
/*!40000 ALTER TABLE `role_resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shopping_cart`
--

DROP TABLE IF EXISTS `shopping_cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shopping_cart` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `goods_id` int(11) NOT NULL COMMENT '商品id',
  `amount` int(11) NOT NULL DEFAULT '0' COMMENT '购物车内该商品数量',
  `img_url` longtext COMMENT '图片base64编码',
  `goods_name` varchar(255) NOT NULL COMMENT '商品名称',
  `price` decimal(10,2) NOT NULL COMMENT '商品单价',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shopping_cart`
--

LOCK TABLES `shopping_cart` WRITE;
/*!40000 ALTER TABLE `shopping_cart` DISABLE KEYS */;
/*!40000 ALTER TABLE `shopping_cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(32) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `enabled` tinyint(1) NOT NULL COMMENT '是否可用，1表示可用，0表示不可用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'test','$2a$10$3gFsxSRuUYkm3SkDsVkr9.RjWPnImxmvJlFezVXNwYfiwCaIvUz.O',1),(26,'admin','$2a$10$hv5MxPvXZUkPr80G4TBvoOclt3Y40gDytmG4q9zvzgSjO9NgjHmte',1),(40,'test23','$2a$10$6SVWqbRjE079uz8WHX/i1.col/1Tsr5b8AJKJFT1VKvxXdGF6oGay',1),(41,'testuser','$2a$10$hv5MxPvXZUkPr80G4TBvoOclt3Y40gDytmG4q9zvzgSjO9NgjHmte',1),(42,'yang123','$2a$10$pdqG2WHA1UG3kiasOsWVVecfDvjGUduIOhjeIkmNFGjf7k2JBQmza',1),(44,'yangsiwen','$2a$10$0HNWR08D7iiXVs5Q5Y2jU.q7s.CBSF5OBdAvQyYOSs.WoVd/LglZK',1),(45,'charles1','$2a$10$aFa.FqEdRXLTbUOHokjRguKpDJUM/FFP9tcvnPbiIjVECqaaz7lfi',1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_info`
--

DROP TABLE IF EXISTS `user_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户信息id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `name` varchar(255) DEFAULT NULL COMMENT '姓名',
  `gender` tinyint(1) NOT NULL COMMENT '表示性别，0表示未知，1表示男，2表示女',
  `phone` varchar(11) DEFAULT NULL COMMENT '联系方式',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_info`
--

LOCK TABLES `user_info` WRITE;
/*!40000 ALTER TABLE `user_info` DISABLE KEYS */;
INSERT INTO `user_info` VALUES (1,1,'张三',1,'18126712313'),(25,26,'超级管理员',1,'123456'),(39,40,NULL,0,NULL),(41,41,NULL,0,NULL),(42,42,'管理员',2,'18302402022'),(44,44,'赵红',2,'13688889999'),(45,45,'管理',1,'13988889999');
/*!40000 ALTER TABLE `user_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户角色id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '关联表状态0正常，1停用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (18,17,13,0),(20,26,3,0),(23,17,12,0),(26,1,13,0),(28,42,3,0),(29,43,12,0),(30,44,3,0),(31,45,12,0);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_session`
--

DROP TABLE IF EXISTS `user_session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_session` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `user_info` varchar(1000) NOT NULL COMMENT '用户的json信息字符串',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_session`
--

LOCK TABLES `user_session` WRITE;
/*!40000 ALTER TABLE `user_session` DISABLE KEYS */;
INSERT INTO `user_session` VALUES (4,38,'{\"accountNonExpired\":true,\"accountNonLocked\":true,\"credentialsNonExpired\":true,\"enabled\":true,\"password\":\"$2a$10$WN7lESOuRDicxb7objYKaOJAL4Ad1YwGzg/56Pxhc9dGQXP3BjhHm\",\"permissions\":[],\"user\":{\"enabled\":1,\"id\":38,\"password\":\"$2a$10$WN7lESOuRDicxb7objYKaOJAL4Ad1YwGzg/56Pxhc9dGQXP3BjhHm\",\"userName\":\"testtest\"},\"username\":\"testtest\"}'),(5,17,'{\"accountNonExpired\":true,\"accountNonLocked\":true,\"credentialsNonExpired\":true,\"enabled\":true,\"password\":\"$2a$10$etYVakifKoNLL9a825qXz.mtcnajXS.XotB3zT4.weM52BcnUaera\",\"permissions\":[\"/order/updateOrder\"],\"user\":{\"enabled\":1,\"id\":17,\"password\":\"$2a$10$etYVakifKoNLL9a825qXz.mtcnajXS.XotB3zT4.weM52BcnUaera\",\"userName\":\"charles1\"},\"username\":\"charles1\"}'),(6,40,'{\"accountNonExpired\":true,\"accountNonLocked\":true,\"credentialsNonExpired\":true,\"enabled\":true,\"password\":\"$2a$10$6SVWqbRjE079uz8WHX/i1.col/1Tsr5b8AJKJFT1VKvxXdGF6oGay\",\"permissions\":[],\"user\":{\"enabled\":1,\"id\":40,\"password\":\"$2a$10$6SVWqbRjE079uz8WHX/i1.col/1Tsr5b8AJKJFT1VKvxXdGF6oGay\",\"userName\":\"test23\"},\"username\":\"test23\"}'),(8,41,'{\"accountNonExpired\":true,\"accountNonLocked\":true,\"credentialsNonExpired\":true,\"enabled\":true,\"password\":\"$2a$10$hv5MxPvXZUkPr80G4TBvoOclt3Y40gDytmG4q9zvzgSjO9NgjHmte\",\"permissions\":[],\"user\":{\"enabled\":1,\"id\":41,\"password\":\"$2a$10$hv5MxPvXZUkPr80G4TBvoOclt3Y40gDytmG4q9zvzgSjO9NgjHmte\",\"userName\":\"testuser\"},\"username\":\"testuser\"}');
/*!40000 ALTER TABLE `user_session` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-06-18 11:01:24
