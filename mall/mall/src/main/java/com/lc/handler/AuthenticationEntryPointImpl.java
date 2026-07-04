package com.lc.handler;

import com.alibaba.fastjson.JSON;
import com.lc.common.result.AuthResult;
import com.lc.common.result.ResultCode;
import com.lc.common.utils.WebUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {

        AuthResult result = new AuthResult(ResultCode.UNAUTHORIZED.getCode(), "用户认证失败请查询登录",null);
        String jsonString = JSON.toJSONString(result);
        WebUtils.renderString(response,jsonString);
    }
}
