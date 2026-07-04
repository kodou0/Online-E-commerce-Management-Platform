package com.lc.service;

import com.lc.common.result.Result;

public interface LoginService {

    /**
     * @param username:
     * @param password:
     * @author: Charles
     * @date: 2022/7/24 16:27
     * @description: 根据用户名和密码进行登录，返回jwt
     */
    Result login(String username, String password);

    /**

     * @author: Charles
     * @date: 2022/7/26 11:00
     * @description: 用户登出
     */
    Result<String> logout();

    /**
     * @param username: 用户名
     * @param prePassword: 第一次输入的密码
     * @param postPassword: 第二次输入的密码
     * @author: Charles
     * @date: 2022/7/26 11:34
     * @description: 用户注册
     */
    Result<String> register(String username, String prePassword, String postPassword);
}
