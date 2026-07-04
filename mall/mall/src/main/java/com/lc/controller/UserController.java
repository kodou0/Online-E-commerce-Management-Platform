package com.lc.controller;

import com.lc.common.result.Result;
import com.lc.model.entity.UserInfo;
import com.lc.model.vo.UserCreateVO;
import com.lc.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author: Charles
 * @date: 2022/7/28 16:28
 * @description: 面向后台的用户控制器
 */
@RestController
@RequestMapping("/userInfo")
public class UserController {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * @param pageNum:
     * @param pageSize:
     * @param name:
     * @author: Charles
     * @date: 2022/7/28 22:38
     * @description: 根据名称，分页搜索人员
     */
    @GetMapping("/getPage")
    @PreAuthorize("hasAuthority('/userInfo/getPage')")
    public Result getUserInfoPage(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize,String name,
                                  Integer roleId){
        return Result.ok(userInfoService.selectUserInfoPage(pageNum,pageSize,name,roleId));
    }

    /**
     * @author: Charles
     * @date: 2022/7/29 14:31
     * @description: 根据用户信息和用户名密码插入数据
     */
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('/userInfo/create')")
    public Result createUser(@RequestBody UserCreateVO userCreateVO){

        return userInfoService.createUser(userCreateVO);
    }

    /**
     * @param id:
     * @author: Charles
     * @date: 2022/7/29 14:54
     * @description: 根据id删除用户
     */
    @GetMapping("/delete")
    @PreAuthorize("hasAuthority('/userInfo/delete')")
    public Result deleteUser(Integer id){
        return userInfoService.deleteUserById(id);
    }

    /**
     * @param userInfo:
     * @author: Charles
     * @date: 2022/7/29 15:36
     * @description: 更新用户信息
     */
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('/userInfo/update')")
    public Result updateUser(@RequestBody UserInfo userInfo){
        return userInfoService.updateUser(userInfo);
    }

    /**
     * @param id:
     * @author: Charles
     * @date: 2022/8/1 16:56
     * @description: 根据id获取用户详情
     */
    @GetMapping("/getById")
    public Result getUserById(@RequestParam("id") Integer id){
        return userInfoService.getUserDetails(id);
    }

    /**
     * @param userId: 传入的是用户的账户id
     * @author: Charles
     * @date: 2022/8/4 11:21
     * @description:
     */
    @GetMapping("/getRole")
    public Result getRoleName(@RequestParam("userId") Integer userId){
        return userInfoService.getUserRole(userId);
    }
}
