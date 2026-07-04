package com.lc.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lc.common.result.Result;
import com.lc.model.entity.Menu;
import com.lc.model.entity.MenuNode;
import com.lc.service.MenuService;
import com.lc.service.RoleService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;

    /**
     * 添加后台菜单
     *
     * @param menu 菜单
     * @return 响应 响应
     */
    @PreAuthorize("hasAuthority('/menu/create')")
    @PostMapping("/create")
    public Result create(@RequestBody Menu menu) {
        int count = menuService.create(menu);
        if (count > 0) {
            return Result.ok(count);
        } else {
            return Result.error("添加菜单失败");
        }
    }

    /**
     * 修改后台菜单
     *
     * @param id   修改id
     * @param menu 菜单
     * @return 响应
     */
    @PreAuthorize("hasAuthority('/menu/update')")
    @PostMapping("/update")
    public Result update(@RequestBody Menu menu) {
        int count = menuService.update(menu);
        if (count > 0) {
            return Result.ok();
        } else {
            return Result.error("菜单更新失败");
        }
    }

    /**
     * 根据ID获取菜单详情
     *
     * @param id 菜单id
     * @return 响应 响应
     */
    @GetMapping("/get")
    public Result getItem(@RequestParam("id") int id) {
        Menu menu = menuService.getItem(id);
        if (menu == null) {
            return Result.error("菜单不存在");
        }
        return Result.ok(menu);
    }

    /**
     * 根据ID删除后台菜单
     *
     * @param id 菜单id
     * @return 响应
     */
    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('/menu/delete')")
    public Result delete(@RequestParam("id") int id) {
        int count = menuService.delete(id);
        if (count > 0) {
            return Result.ok();
        } else {
            return Result.error("删除菜单失败");
        }
    }

    /**
     * 分页查询后台菜单
     *
     * @param parentId 父菜单id
     * @param pageSize 每页条数
     * @param pageNum  页数
     * @return 响应
     */
    @GetMapping("/list")
    public Result list(@RequestParam("parentId") int parentId,
                       @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                       @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<Menu> page = menuService.list(parentId, pageSize, pageNum);
        return Result.ok(page);
    }

    /**
     * 树形结构返回所有菜单列表
     *
     * @return 响应
     */
    @GetMapping("/treeList")
    public Result treeList() {
        List<MenuNode> list = menuService.treeList();
        return Result.ok(list);
    }

    /**
     * 修改菜单显示状态
     *
     * @param id     菜单id
     * @param hidden 是否隐藏
     * @return 响应
     */
    @PreAuthorize("hasAuthority('/menu/updateHidden')")
    @PostMapping("/updateHidden")
    public Result updateHidden(@RequestParam("id") int id, @RequestParam("hidden") Integer hidden) {
        int count = menuService.updateHidden(id, hidden);
        if (count > 0) {
            return Result.ok();
        } else {
            return Result.error("更新菜单显示状态失败");
        }
    }

    @GetMapping("/getRoleMenu")
    public Result getRoleMenu(HttpServletRequest request) {
        //根据token返回角色id
        String userId = request.getHeader("userId");
        Integer roleId = roleService.selectRoleByUserId(userId);
        if (roleId == 0) {
            return Result.error("角色为空");
        }
        List<MenuNode> menus = menuService.getMenuIds(roleId);
        if (menus == null){
            return Result.error("角色菜单为空");
        }
        return Result.ok(menus);
    }
}
