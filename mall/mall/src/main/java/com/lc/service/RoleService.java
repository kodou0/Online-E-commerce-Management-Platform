package com.lc.service;

import com.lc.common.result.PageResult;
import com.lc.common.result.Result;
import com.lc.model.entity.Role;
import com.lc.model.vo.SimpleRoleVO;

import java.util.List;

public interface RoleService {
    PageResult<Role> selectPage(Integer pageNum, Integer pageSize, String name);

    Result roleUpdate(Role role);

    Result createRole(Role role);

    Result deleteRole(Integer roleId);

    Result statusUpdate(Integer id, Integer status);

    Result allocateRole(Integer roleId, Integer userId);

    Integer selectRoleByUserId(String userId);

    Result allocateResource(List<Integer> resourceIds, Integer roleId);

    Result allocateMenu(List<Integer> menuIds, Integer roleId);

    List<SimpleRoleVO> getSimpleRoleList();
}
