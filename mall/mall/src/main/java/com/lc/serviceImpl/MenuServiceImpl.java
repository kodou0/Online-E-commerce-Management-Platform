package com.lc.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lc.mapper.MenuMapper;
import com.lc.mapper.RoleMenuMapper;
import com.lc.model.entity.Menu;
import com.lc.model.entity.MenuNode;
import com.lc.service.MenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    /**
     * 修改菜单层级
     */
    private void updateLevel(Menu menu) {
        if (menu.getParentId() == 0) {
            //没有父菜单时为一级菜单
            menu.setLevel(0);
        } else {
            //有父菜单时选择根据父菜单level设置
            Menu parentMenu = menuMapper.selectById(menu.getParentId());
            if (parentMenu != null) {
                menu.setLevel(parentMenu.getLevel() + 1);
            } else {
                menu.setLevel(0);
            }
        }
    }

    @Override
    public int create(Menu menu) {
        menu.setCreatedTime(new Date());
        updateLevel(menu);
        return menuMapper.insert(menu);
    }

    @Override
    public int update(Menu menu) {
        updateLevel(menu);
        return menuMapper.updateById(menu);
    }

    @Override
    public Menu getItem(int id) {
        return menuMapper.selectById(id);
    }

    @Override
    public int delete(int id) {
        return menuMapper.deleteById(id);
    }

    @Override
    public Page<Menu> list(int parentId, Integer pageSize, Integer pageNum) {
        Page<Menu> page = new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Menu::getParentId, parentId);
        wrapper.orderByDesc(Menu::getSort);
        menuMapper.selectPage(page, wrapper);
        return page;
    }

    @Override
    public List<MenuNode> treeList() {
        List<Menu> menuList = menuMapper.selectList(null);
        List<MenuNode> result = menuList.stream()
                .filter(menu -> menu.getParentId() == 0)
                .map(menu -> covertMenuNode(menu, menuList))
                .collect(Collectors.toList());
        return result;
    }

    @Override
    public int updateHidden(int id, Integer hidden) {
        Menu menu = new Menu();
        menu.setId(id);
        menu.setHidden(hidden);
        return menuMapper.updateById(menu);
    }

    @Override
    public List<MenuNode> getMenuIds(Integer roleId) {
        List<Integer> menuIds = roleMenuMapper.listMenuIdsByRoleId(roleId);
        List<Menu> menuList = menuMapper.selectBatchIds(menuIds);
        List<MenuNode> result = menuList.stream()
                .filter(menu -> menu.getParentId() == 0)
                .map(menu -> covertMenuNode(menu, menuList))
                .collect(Collectors.toList());
        return result;
    }

    /**
     * 将Menu转化为MenuNode并设置children属性
     */
    private MenuNode covertMenuNode(Menu menu, List<Menu> menuList) {
        MenuNode node = new MenuNode();
        BeanUtils.copyProperties(menu, node);
        List<MenuNode> children = menuList.stream()
                .filter(subMenu -> subMenu.getParentId() == menu.getId())
                .map(subMenu -> covertMenuNode(subMenu, menuList)).collect(Collectors.toList());
        node.setChildren(children);
        return node;
    }

}
