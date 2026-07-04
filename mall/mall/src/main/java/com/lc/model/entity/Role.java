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
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
@TableName("role")
public class Role {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private String remark;
    private Integer status;
    private Date createTime;
    //记录该角色的人数
    @TableField(exist = false)
    private Integer count;
}
