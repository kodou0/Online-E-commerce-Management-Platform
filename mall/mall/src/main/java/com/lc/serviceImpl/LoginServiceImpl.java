package com.lc.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lc.common.result.Result;
import com.lc.common.utils.JwtUtil;
import com.lc.common.utils.RedisCache;
import com.lc.mapper.UserInfoMapper;
import com.lc.mapper.UserMapper;
import com.lc.mapper.UserSessionMapper;
import com.lc.model.dto.LoginUser;
import com.lc.model.entity.User;
import com.lc.model.entity.UserInfo;
import com.lc.model.entity.UserSession;
import com.lc.service.LoginService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Objects;


@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Resource
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private UserSessionMapper userSessionMapper;

    @Override
    public Result login(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(username,password);
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //2.如果认证没通过给出对应提示
        if(Objects.isNull(authenticate))
            throw new RuntimeException("登录失败");
        //3.生成jwt根据userid，返回jwt
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        Integer userId = loginUser.getUser().getId();
        String jwt = JwtUtil.createJWT(userId.toString());
        //4.把完整的用户信息存入mysql中的session表，userid作为key
        //先检查是否有该条数据，如果有则为更新 否则为插入
        UserSession exist = userSessionMapper.selectByUserId(userId);
        if(exist != null){
            exist.setUserInfo(JSON.toJSONString(loginUser));
            userSessionMapper.updateById(exist);
        }
        else{
            UserSession userSession = new UserSession();
            userSession.setUserId(userId);
            userSession.setUserInfo(JSON.toJSONString(loginUser));
            userSessionMapper.insert(userSession);
        }
        HashMap<String,String> map = new HashMap<>();
        map.put("token",jwt);
        return Result.ok(map);
    }

    @Override
    public Result<String> logout() {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Integer id = loginUser.getUser().getId();
        QueryWrapper<UserSession> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",id);
        userSessionMapper.delete(queryWrapper);
        return Result.ok("注销成功");
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result<String> register(String username, String prePassword, String postPassword) {

        //先检查账号的唯一性
        if(userMapper.selectByUserName(username) != 0){
            return Result.error("用户名已经存在");
        }
        if(!postPassword.equals(postPassword)){
            return Result.error("两次输入的密码不一致");
        }

        //从代码层面写好初始值
        User user = new User();
        user.setUserName(username);
        user.setPassword(passwordEncoder.encode(prePassword));
        user.setEnabled(1);
        userMapper.insert(user);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getId());
        //详细的用户详情等注册完之后再慢慢编辑
        userInfo.setGender(0);
        userInfoMapper.insert(userInfo);
        return Result.ok("注册成功");
    }
}
