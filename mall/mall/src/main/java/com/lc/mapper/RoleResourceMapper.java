package com.lc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lc.model.entity.RoleResourceDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleResourceMapper extends BaseMapper<RoleResourceDO> {

    Integer batchInsert(@Param("ids") List<Integer> ids,@Param("roleId") Integer roleId);

    Integer updateStatus(@Param("status") Integer status, @Param("roleId") Integer roleId);
}
