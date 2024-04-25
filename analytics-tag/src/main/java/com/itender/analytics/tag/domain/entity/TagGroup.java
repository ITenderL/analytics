package com.itender.analytics.tag.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yuanhewei
 * @date 2024/4/25 15:08
 * @description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_tag_group")
public class TagGroup {

    /**
     * 标签id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 标签组名
     */
    @TableField("tag_group_name")
    private String name;
}
