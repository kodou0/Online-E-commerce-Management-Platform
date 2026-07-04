package com.lc.model.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
@TableName("user")
public class User {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("username")
    private String userName;
    @TableField("password")
    private String password;
    @TableField("enabled")
    private int enabled;
}
