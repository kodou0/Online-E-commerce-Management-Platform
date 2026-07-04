package com.lc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lc.model.entity.RoleMenuDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMenuMapper extends BaseMapper<RoleMenuDO> {

    Integer batchInsert(@Param("ids") List<Integer> ids, @Param("roleId") Integer roleId);

    List<Integer> listMenuIdsByRoleId(@Param("roleId") Integer roleId);

    Integer updateStatus(@Param("status") Integer status, @Param("roleId") Integer roleId);
}
