package com.lc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lc.model.entity.Menu;
import com.lc.model.entity.MenuNode;
import com.lc.service.MenuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
public class MenuTest {
    @Autowired
    private MenuService menuService;

    @Test
    public void testCreate(){
        Menu menu = new Menu();
        menu.setParentId(2);
        menu.setTitle("111模块");
        menu.setLevel(0);
        menu.setSort(1);
        menu.setName("my");
        menu.setIcon("#");
        menu.setHidden(0);
        menu.setCreatedTime(new Date());
        int count = menuService.create(menu);
        if (count > 0) {
            System.out.println("菜单创建成功");
        } else {
            System.out.println("添加菜单失败");
        }
    }

    @Test
    public void testUpdate(){
        int id = 1;
        Menu menu = new Menu();
        menu.setParentId(2);
        menu.setTitle("他的模块");
        menu.setLevel(0);
        menu.setSort(2);
        menu.setName("my");
        menu.setIcon("#");
        menu.setHidden(1);
        menu.setCreatedTime(new Date());
        int count = menuService.update(menu);
        if (count > 0) {
            System.out.println("菜单更新成功");
        } else {
            System.out.println("菜单更新失败");
        }
    }

    @Test
    public void testGetItem(){
        int id = 1;
        Menu menu = menuService.getItem(id);
        System.out.println(menu);
    }

    @Test
    public void testDelete(){
        int id = 1;
        int count = menuService.delete(id);
        if (count > 0) {
            System.out.println("菜单删除成功");
        } else {
            System.out.println("删除菜单失败");
        }
    }

    @Test
    public void testGetPage(){
        int parentId = 2;
        int pageNum = 1;
        int pageSize = 10;
        Page<Menu> page = menuService.list(parentId, pageSize, pageNum);
        List<Menu> lists = page.getRecords();
        lists.forEach(System.out::println);
    }

    @Test
    public void testGetTree(){
        List<MenuNode> list = menuService.treeList();
        list.forEach(System.out::println);
    }

    @Test
    public void testUpdateHidden(){
        int id = 2;
        int hidden = 1;
        int count = menuService.updateHidden(id, hidden);
        if (count > 0) {
            System.out.println("更新菜单显示状态成功");
        } else {
            System.out.println("更新菜单显示状态失败");
        }
    }
}
