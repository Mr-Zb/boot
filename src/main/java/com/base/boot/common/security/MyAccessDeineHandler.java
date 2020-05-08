package com.base.boot.common.security;

import com.alibaba.fastjson.JSONObject;
import com.base.boot.common.VO.ResultVO;
import com.base.boot.common.enums.ResultEnum;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zbang
 * 认证过的用户访问无权限资源时权限校验
 */
@Component
public class MyAccessDeineHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().print(JSONObject.toJSONString(ResultVO.failure(204, ResultEnum.USER_NO_ACCESS.getMessage())));
    }

}
