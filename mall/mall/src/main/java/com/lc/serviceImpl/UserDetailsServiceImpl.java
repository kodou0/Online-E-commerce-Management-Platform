package com.lc.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lc.mapper.UserMapper;
import com.lc.mapper.UserRoleMapper;
import com.lc.model.dto.LoginUser;
import com.lc.model.entity.User;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    @Resource
    private UserMapper userMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        User user = userMapper.selectOne(queryWrapper);
        if(Objects.isNull(user))
            throw new RuntimeException("用户名或密码错误");
        List<String> permissions = userRoleMapper.selectPermsByUserId(user.getId());
        //把数据封装成userDetails对象返回
        //去除权限字符串中所有空值，防止报错
        permissions.removeIf(Objects::isNull);
        return new LoginUser(user,permissions);
    }
}
