package com.lc.controller;


import com.lc.common.result.Result;
import com.lc.model.entity.Role;
import com.lc.service.RoleService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * @param pageNum:
     * @param pageSize:
     * @param name:
     * @author: Charles
     * @date: 2022/7/30 15:36
     * @description: 根据名称分页搜索角色列表
     */
    @GetMapping("/getPage")
    @PreAuthorize("hasAuthority('/role/getPage')")
    public Result getRolePage(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, String name){
        return Result.ok(roleService.selectPage(pageNum,pageSize,name));
    }

    /**
     * @param role:
     * @author: Charles
     * @date: 2022/7/30 16:28
     * @description: 更新角色信息
     */
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('/role/update')")
    public Result roleInfoUpdate(@RequestBody Role role){
        return roleService.roleUpdate(role);
    }

    /**
     * @param role:
     * @author: Charles
     * @date: 2022/7/30 20:30
     * @description: 插入角色
     */
    @PostMapping("/insert")
    @PreAuthorize("hasAuthority('/role/insert')")
    public Result roleCreate(@RequestBody Role role){
        return roleService.createRole(role);
    }

    /**
     * @param roleId:
     * @author: Charles
     * @date: 2022/7/30 20:36
     * @description: 根据id删除角色
     */
    @GetMapping("/delete")
    @PreAuthorize("hasAuthority('/role/delete')")
    public Result roleDelete(@RequestParam("roleId") Integer roleId){
        return roleService.deleteRole(roleId);
    }

    /**
     * @param id:
     * @param status:
     * @author: Charles
     * @date: 2022/7/31 17:19
     * @description: 停用/启用角色
     */
    @PostMapping("/statusUpdate")
    @PreAuthorize("hasAuthority('/role/statusUpdate')")
    public Result roleStatusUpdate(@RequestParam("id") Integer id,@RequestParam("status") Integer status){
        return roleService.statusUpdate(id,status);
    }

    /**
     * @param roleId:
     * @param userId:
     * @author: Charles
     * @date: 2022/8/1 21:25
     * @description: 分配角色
     */
    @PostMapping("/allocate")
    @PreAuthorize("hasAuthority('/role/allocate')")
    public Result roleAllocate(@RequestParam("roleId") Integer roleId,@RequestParam("userId") Integer userId){
        return roleService.allocateRole(roleId,userId);
    }

    /**
     * @param request:
     * @author: Charles
     * @date: 2022/8/2 16:57
     * @description: 获取用户的角色id
     */
    @GetMapping("/getRoleId")
    public Result getRoleId(HttpServletRequest request){
        //根据token返回角色id
        String userId = request.getHeader("userId");
        Integer res = roleService.selectRoleByUserId(userId);
        if(res == 0){
            return Result.error("角色为空");
        }
        else{
            return Result.ok(res);
        }
    }

    /**
     * @param resourceIds:
     * @param roleId:
     * @author: Charles
     * @date: 2022/8/2 20:58
     * @description: 根据角色id分配资源
     */
    @PreAuthorize("hasAuthority('/role/allocate_resource')")
    @PostMapping("/allocate_resource")
    public Result resourceAllocate(@RequestParam("resourceIds") List<Integer> resourceIds, Integer roleId){
        return roleService.allocateResource(resourceIds,roleId);
    }


    /**
     * @param menuIds:
     * @param roleId:
     * @author: Charles
     * @date: 2022/8/2 21:34
     * @description: 根据角色id分配菜单
     */
    @PreAuthorize("hasAuthority('/role/allocate_menu')")
    @PostMapping("/allocate_menu")
    public Result menuAllocate(@RequestParam("menuIds") List<Integer> menuIds,Integer roleId){
        return roleService.allocateMenu(menuIds,roleId);
    }

    /**

     * @author: Charles
     * @date: 2022/8/5 0:05
     * @description: 获取角色精简列表
     */
    @PreAuthorize("hasAuthority('/role/getPage')")
    @GetMapping("/getSimpleList")
    public Result getSimpleRoleList(){
        return Result.ok(roleService.getSimpleRoleList());
    }

}
