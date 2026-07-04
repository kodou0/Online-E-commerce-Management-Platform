package com.lc.model.entity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("`menu`")
public class Menu {
    @TableId("id")
    private int id;
    @TableField("parent_id")
    private int parentId;
    @TableField("title")
    private String title;
    @TableField("level")
    private int level;
    @TableField("sort")
    private int sort;
    @TableField("name")
    private String name;
    @TableField("icon")
    private String icon;
    @TableField("hidden")
    private int hidden;
    @TableField("created_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;}
