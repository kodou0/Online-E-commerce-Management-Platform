package com.lc.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lc.model.entity.Menu;
import com.lc.model.entity.MenuNode;

import java.util.List;

public interface MenuService {
    /**
     * 创建后台菜单
     */
    int create(Menu menu);

    /**
     * 更新后台菜单
     * @param menu
     * @return
     */
    int update(Menu menu);

    Menu getItem(int id);

    int delete(int id);

    Page<Menu> list(int parentId, Integer pageSize, Integer pageNum);

    List<MenuNode> treeList();

    int updateHidden(int id, Integer hidden);

    List<MenuNode> getMenuIds(Integer roleId);
}
