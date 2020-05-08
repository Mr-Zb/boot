package com.base.boot.common.security;


import com.alibaba.fastjson.JSONObject;
import com.base.boot.common.VO.ResultVO;
import com.base.boot.common.enums.ResultEnum;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zbang
 * 匿名用户访问无权限资源时权限校验
 */
@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().print(JSONObject.toJSONString(ResultVO.failure(ResultEnum.USER_NO_ACCESS.getCode(), ResultEnum.USER_NO_ACCESS.getMessage())));
    }

}
