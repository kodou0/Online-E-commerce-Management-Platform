package com.lc.service;

import com.lc.common.result.PageResult;
import com.lc.common.result.Result;
import com.lc.model.entity.UserInfo;
import com.lc.model.vo.UserCreateVO;

public interface UserInfoService {
    PageResult<UserInfo> selectUserInfoPage(Integer pageNum, Integer pageSize, String name,Integer roleId);

    Result createUser(UserCreateVO userCreateVO);

    /**
     * @param id:
     * @author: Charles
     * @date: 2022/7/29 15:19
     * @description: 注意同时删除user和userInfo表里面的记录
     */
    Result deleteUserById(Integer id);

    Result updateUser(UserInfo userInfo);

    Result getUserDetails(Integer id);

    Result getUserRole(Integer userId);
}
