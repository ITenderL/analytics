# 监控告警平台设计方案

## 1. 背景

### 1.1 概述



### 1.2 目标



## 2. 业务流程图



## 3. 接口设计

### 3.1 告警规则



### 3.2 告警结果



## 4. 数据库设计

### 4.1 `MySQL`

#### 4.1.1 表结构设计

##### 4.1.1.1 告警规则表

``` sql
CREATE TABLE `t_analytics_alarm_rule` (
`id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '告警规则id',
`name` VARCHAR ( 255 ) NOT NULL DEFAULT '' COMMENT '告警规则名称',
`leve` TINYINT ( 2 ) NOT NULL COMMENT '告警级别',
`push_type` TINYINT ( 1 ) NOT NULL DEFAULT '0' COMMENT '推送方式 0：聊天工具 1：邮件 2：短信',
`responsible` INT ( 11 ) NOT NULL COMMENT '告警责任人id',
`cron` VARCHAR ( 255 ) NOT NULL DEFAULT '' COMMENT 'cron表达式',
`receivers` VARCHAR ( 255 ) NOT NULL DEFAULT '' COMMENT '告警接收人id集合，逗号分隔',
`creator` BIGINT DEFAULT NULL COMMENT '创建人ID',
`create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`updater` BIGINT DEFAULT NULL COMMENT '更新人ID',
`update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
`deleted` TINYINT ( 1 ) NOT NULL DEFAULT '0' COMMENT '是否删除（0：未删除，1：已删除）',
PRIMARY KEY ( `id` ) USING BTREE,
KEY `uk_name` ( `name` ) USING BTREE 
) ENGINE = INNODB ROW_FORMAT = DYNAMIC COMMENT = '告警规则表';
```



##### 4.1.1.2 告警规则过滤条件表

```sql
CREATE TABLE `t_analytics_alarm_filter` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `rule_id` int NOT NULL COMMENT '告警规则id',
  `type` varchar(64) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '过滤条件类型',
  `filter_id` int NOT NULL COMMENT '过滤条件id',
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '过滤条件名称',
  `leve` tinyint NOT NULL COMMENT '过滤条件层级',
  `scenarios` tinyint(1)  DEFAULT null COMMENT '使用场景，适用于关键词，1：must或者0：must not',
  `is_monitor` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否分别监控 0：否 1：是',
  `is_selected_all` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否分别监控 0：否 1：是',
  `creator` bigint NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` bigint NOT NULL COMMENT '更新人ID',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除（0：未删除，1：已删除）',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_rule_id` (`rule_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='告警过滤条件表';
```



##### 4.1.1.3 告警结果表

```sql
CREATE TABLE `t_analytics_alarm_result` (
`id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
`rule_id` INT NOT NULL COMMENT '告警规则id',
`name` VARCHAR ( 255 ) NOT NULL DEFAULT '' COMMENT '告警规则名称',
`dimension` VARCHAR ( 255 ) NOT NULL DEFAULT '' COMMENT '监控维度',
`condition` VARCHAR ( 255 ) NOT NULL DEFAULT '' COMMENT '告警条件',
`trigger_condition` VARCHAR ( 255 ) NOT NULL DEFAULT '' COMMENT '告警触发条件',
`trigger_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '触发时间',
`status` TINYINT ( 1 ) NOT NULL DEFAULT '0' COMMENT '是否删除（0：未处理，1：处理中，2：已解决）',
`reason` VARCHAR ( 255 ) NOT NULL DEFAULT '' COMMENT '原因',
`description` VARCHAR ( 255 ) NOT NULL DEFAULT '' COMMENT '描述',
`creator` BIGINT NOT NULL COMMENT '创建人ID',
`create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`updater` BIGINT NOT NULL COMMENT '更新人ID',
`update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
`deleted` TINYINT ( 1 ) NOT NULL DEFAULT '0' COMMENT '是否删除（0：未删除，1：已删除）',
PRIMARY KEY ( `id` ) USING BTREE,
KEY `idx_rule_id` ( `rule_id` ) USING BTREE 
) ENGINE = INNODB ROW_FORMAT = DYNAMIC COMMENT = '告警结果表';
```





#### 4.1.2 表关系图（对应：领域模型）



### 4.2 `Elasticsearch`

#### 4.2.1 数据详情索引

``` sql
PUT _index_template/idata_oneid_detail_template
{
  "index_patterns": "idata_oneid_detail*",
  "priority": "1",
  "template": {
    "settings": {
      "number_of_shards": "3",
      "number_of_replicas": "2"
    },
    "mappings": {
      "properties": {
        "oneid": {
          "type": "keyword"
        },
        "pay_time": {
          "type": "date",
          "format": "yyyy-MM-dd"
        },
        "channel_order_code": {
          "type": "keyword"
        },
        "erp_order_code": {
          "type": "keyword"
        },
        "channel_store_code": {
          "type": "keyword"
        },
        "user_acc": {
          "type": "keyword"
        },
        "consignee_name": {
          "type": "keyword"
        },
        "consignee_phone": {
          "type": "keyword"
        },
        "consignee_detail": {
          "type": "text"
        },
        "actual_pay_money": {
          "type": "double"
        },
        "rfd_money": {
          "type": "double"
        },
        "sale_store_name": {
          "type": "keyword"
        }
      }
    },
    "aliases": {
      "idata_oneid_detail": {}
    }
  }
}
```



## 5. 技术要点

### 5.1 核心内容

> 简要体现后续工作的主要内容和难易程度，目的在于让文档阅读人员快速抓住重点。

### 5.2 系统架构图

> 整个架构最终的输出模型状态。如果是部分重构或新增部分，可以用不同颜色区分新旧部分

### 5.3 相关UML图

> 有多种状态需要说明的，则附加，例如泳道图等。如没有，则写无



