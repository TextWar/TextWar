/*
Navicat SQLite Data Transfer

Source Server         : textwat
Source Server Version : 30714
Source Host           : :0

Target Server Type    : SQLite
Target Server Version : 30714
File Encoding         : 65001

Date: 2020-03-18 11:15:48

By Caocao
*/

PRAGMA foreign_keys = OFF;

-- ----------------------------
-- Table structure for item
-- ----------------------------
DROP TABLE IF EXISTS "main"."item";
CREATE TABLE item(id int primary key,auto increment,name varchar,consumable bool);

-- ----------------------------
-- Records of item
-- ----------------------------

-- ----------------------------
-- Table structure for map
-- ----------------------------
DROP TABLE IF EXISTS "main"."map";
CREATE TABLE map(mapid int primary key,auto increase ment,array varchar,path vaechar);

-- ----------------------------
-- Records of map
-- ----------------------------

-- ----------------------------
-- Table structure for player
-- ----------------------------
DROP TABLE IF EXISTS "main"."player";
CREATE TABLE player(qq varchar,level int,money int,jointime datetime);

-- ----------------------------
-- Records of player
-- ----------------------------

-- ----------------------------
-- Table structure for player_item
-- ----------------------------
DROP TABLE IF EXISTS "main"."player_item";
CREATE TABLE player_item(qq varchar,itemid_1 int,itemid_2 int,itemid_3 int,itemid_4 int,itemid_5 int,itemid_6 int,itemid_7 int,itemid_8 int,itemid_9 int);

-- ----------------------------
-- Records of player_item
-- ----------------------------
