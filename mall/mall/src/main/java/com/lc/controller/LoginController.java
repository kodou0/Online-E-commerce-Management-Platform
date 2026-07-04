package com.lc.controller;

import com.lc.common.result.Result;
import com.lc.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author: Charles
 * @date: 2022/7/26 11:21
 * @description: 面向消费者的接口控制器
 */

@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public Result login(String username, String password){
        return loginService.login(username,password);
    }

    @GetMapping("/logout")
    public Result<String> logout(){
        return loginService.logout();
    }

    @PostMapping("/register")
    public Result<String> userRegister(@RequestParam("username") String username,
                                       @RequestParam("prePassword") String prePassword,
                                       @RequestParam("postPassword") String postPassword){
        return loginService.register(username,prePassword,postPassword);
    }
}
