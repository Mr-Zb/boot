package com.base.boot.common.security;


import com.base.boot.common.VO.ResultVO;
import com.base.boot.common.enums.ResultEnum;
import com.base.boot.common.exception.ValidateCodeException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
public class MyAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    //在application配置文件中配置登陆的类型是JSON数据响应还是做页面响应
    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException e)
            throws IOException {
        Map<String, Object> failure = null;

        if (e instanceof ValidateCodeException) {
            //验证码错误
            if (ResultEnum.IMG_NULL_CODE.getMessage().equals(e.getMessage())) {
                failure = ResultVO.failure(ResultEnum.IMG_NULL_CODE.getCode(), ResultEnum.IMG_NULL_CODE.getMessage());
            } else {
                failure = ResultVO.failure(ResultEnum.IMG_ERR_CODE.getCode(), ResultEnum.IMG_ERR_CODE.getMessage());
            }
        }
        if (e instanceof ValidateCodeException) {
            //验证码错误
            if (ResultEnum.USER_NULL.getMessage().equals(e.getMessage())) {
                failure = ResultVO.failure(ResultEnum.USER_NULL.getCode(), ResultEnum.USER_NULL.getMessage());
            } else {
                failure = ResultVO.failure(ResultEnum.USER_NOT.getCode(), ResultEnum.USER_NOT.getMessage());
            }
        }
        if (e instanceof BadCredentialsException) {
            //密码错误
            failure = ResultVO.failure(ResultEnum.USER_LOGIN_FAILED.getCode(), ResultEnum.USER_LOGIN_FAILED.getMessage());
        }
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(
                objectMapper.writeValueAsString(failure));
    }
}
