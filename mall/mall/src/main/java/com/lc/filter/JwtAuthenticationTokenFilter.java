package com.lc.filter;


import com.alibaba.fastjson.JSONObject;
import com.lc.common.utils.JwtUtil;
import com.lc.common.utils.RedisCache;
import com.lc.mapper.UserSessionMapper;
import com.lc.model.dto.LoginUser;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Resource
    private UserSessionMapper userSessionMapper;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token=request.getHeader("token");
        if(!StringUtils.hasText(token)){
            filterChain.doFilter(request,response);
            return;
        }
        String userId = "";
        //解析token
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userId=claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("token非法");
        }
        String mainId = userId;
        HttpServletRequestWrapper httpServletRequestWrapper = new HttpServletRequestWrapper(request){
            @Override
            public String getHeader(String name) {
                if ("userId".equals(name)){
                    return mainId;
                }
                return super.getHeader(name);
            }
        };
        //从redis获取用户信息
        Integer key = Integer.valueOf(userId);
        LoginUser loginUser = JSONObject.parseObject(userSessionMapper.selectByUserId(key).getUserInfo(),LoginUser.class);
        if(Objects.isNull(loginUser))
            throw new RuntimeException("用户信息不存在");
        //存入SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken=
                new UsernamePasswordAuthenticationToken(loginUser,null,loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(httpServletRequestWrapper,response);

    }
}
