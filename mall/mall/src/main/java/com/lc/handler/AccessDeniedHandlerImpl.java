package com.lc.handler;

import com.alibaba.fastjson.JSON;
import com.lc.common.result.AuthResult;
import com.lc.common.result.ResultCode;
import com.lc.common.utils.WebUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException {
        AuthResult result = new AuthResult(ResultCode.FORBIDDEN.getCode(), "用户没有该资源的权限",null);
        String jsonString = JSON.toJSONString(result);
        WebUtils.renderString(response,jsonString);
    }
}
