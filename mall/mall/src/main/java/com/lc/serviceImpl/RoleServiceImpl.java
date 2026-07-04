package com.lc.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lc.common.result.PageResult;
import com.lc.common.result.Result;
import com.lc.mapper.*;
import com.lc.model.entity.Role;
import com.lc.model.entity.RoleMenuDO;
import com.lc.model.entity.RoleResourceDO;
import com.lc.model.entity.UserRoleDO;
import com.lc.model.vo.SimpleRoleVO;
import com.lc.service.RoleService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private RoleResourceMapper roleResourceMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;
    @Override
    public PageResult<Role> selectPage(Integer pageNum, Integer pageSize, String name) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        if(name != null){
            queryWrapper.like("name",name);
        }
        Page<Role> page = new Page<>(pageNum,pageSize);
        roleMapper.selectPage(page,queryWrapper);
        List<Role> roles = page.getRecords();
        for(Role role : roles){
            Integer count = userRoleMapper.selectCountByRoleId(role.getId());
            role.setCount(count);
        }
        return new PageResult<>(page);
    }

    @Override
    public Result roleUpdate(Role role) {
        if(roleMapper.updateById(role) >= 1){
            return Result.ok("更新成功");
        }
        else{
            return Result.error("更新失败");
        }
    }

    @Override
    public Result createRole(Role role) {
        if(roleMapper.insert(role) == 1){
            return Result.ok("插入成功");
        }
        else{
            return Result.error("插入失败");
        }
    }

    @Override
    public Result deleteRole(Integer roleId) {
        //批量删除所有与roleId相关的记录
        QueryWrapper<UserRoleDO> userWrapper = new QueryWrapper<>();
        userWrapper.eq("role_id",roleId);
        userRoleMapper.delete(userWrapper);
        QueryWrapper<RoleMenuDO> roleWrapper = new QueryWrapper<>();
        roleWrapper.eq("role_id",roleId);
        roleMenuMapper.delete(roleWrapper);
        QueryWrapper<RoleResourceDO> resourceWrapper = new QueryWrapper<>();
        resourceWrapper.eq("role_id",roleId);
        roleResourceMapper.delete(resourceWrapper);
        if(roleMapper.deleteById(roleId) == 1){
            return Result.ok("删除成功");
        }
        else{
            return Result.error("删除失败");
        }
    }

    @Override
    public Result statusUpdate(Integer id, Integer status) {
        Role role = roleMapper.selectById(id);
        if(role == null){
            return Result.error("角色不存在");
        }
        //判断传入参数是否与原来的状态相同
        if(role.getStatus() == status){
            return Result.ok("更新成功");
        }
        //将所有与角色关联的表更新
        userRoleMapper.updateStatus(status,id);
        roleResourceMapper.updateStatus(status,id);
        roleMenuMapper.updateStatus(status,id);
        role.setStatus(status);
        if(roleMapper.updateById(role) == 1){
            return Result.ok("更新成功");
        }
        return Result.error("更新失败");
    }

    @Override
    public Result allocateRole(Integer roleId, Integer userId) {
        //先把已有的角色相关id删除
        QueryWrapper<UserRoleDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        userRoleMapper.delete(queryWrapper);
        UserRoleDO userRoleDO = new UserRoleDO();
        userRoleDO.setRoleId(roleId);
        userRoleDO.setUserId(userId);
        if(userRoleMapper.insert(userRoleDO) == 1){
            return Result.ok();
        }
        else{
            return Result.error();
        }

    }

    @Override
    public Integer selectRoleByUserId(String userId) {
        Integer id = Integer.valueOf(userId);
        System.out.println(id);
        QueryWrapper<UserRoleDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",id);
        UserRoleDO userRoleDO = userRoleMapper.selectOne(queryWrapper);
        if(userRoleDO == null){
            return 0;
        }
        return userRoleDO.getRoleId();
    }

    @Override
    public Result allocateResource(List<Integer> resourceIds, Integer roleId) {

        if(resourceIds == null || resourceIds.size() == 0){
            return Result.ok();
        }
        //先把该角色已有的资源记录删除
        QueryWrapper<RoleResourceDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id",roleId);
        roleResourceMapper.delete(queryWrapper);
        if(roleMapper.selectById(roleId) == null){
            return Result.error("角色id不存在");
        }
        if(roleResourceMapper.batchInsert(resourceIds,roleId) == resourceIds.size()){
            return Result.ok();
        }
        else{
            return Result.error();
        }

    }

    @Override
    public Result allocateMenu(List<Integer> menuIds, Integer roleId) {
        if(menuIds == null || menuIds.size() == 0){
            return Result.ok();
        }
        //先把该角色已有的菜单记录删除
        QueryWrapper<RoleMenuDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id",roleId);
        roleMenuMapper.delete(queryWrapper);
        if(roleMapper.selectById(roleId) == null){
            return Result.error("角色id不存在");
        }
        if(roleMenuMapper.batchInsert(menuIds,roleId) == menuIds.size()){
            return Result.ok();
        }
        else{
            return Result.error();
        }
    }

    @Override
    public List<SimpleRoleVO> getSimpleRoleList() {
        return roleMapper.selectSimpleList();
    }
}
