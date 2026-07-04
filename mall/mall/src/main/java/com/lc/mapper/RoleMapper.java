package com.lc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lc.model.entity.Role;
import com.lc.model.vo.SimpleRoleVO;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {
    List<SimpleRoleVO> selectSimpleList();

}
