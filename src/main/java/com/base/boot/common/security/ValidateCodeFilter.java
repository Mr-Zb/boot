package com.base.boot.common.security;

import com.base.boot.common.enums.ResultEnum;
import com.base.boot.common.exception.ValidateCodeException;
import com.base.boot.common.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Component
public class ValidateCodeFilter extends OncePerRequestFilter {

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        //TODO 查询是否打开验证码开关
        if (StringUtils.startsWithIgnoreCase("/login", request.getRequestURI())
                && StringUtils.startsWithIgnoreCase(request.getMethod(), "post")) {
            try {
                validateCode(request);
            } catch (ValidateCodeException e) {
                authenticationFailureHandler.onAuthenticationFailure(request, httpServletResponse, e);
                return;
            }
        }
        filterChain.doFilter(request, httpServletResponse);
    }

    private void validateCode(HttpServletRequest request) {
        Object codeInSession = request.getSession().getAttribute(Constants.IMGAGECODE);
        String codeInRequest = request.getParameter("imageCode");
        if (StringUtils.isEmpty(codeInRequest)) {
            throw new ValidateCodeException(ResultEnum.IMG_NULL_CODE.getMessage());
        } else if (!StringUtils.startsWithIgnoreCase((String) codeInSession, codeInRequest)) {
            request.getSession().removeAttribute(Constants.IMGAGECODE);
            throw new ValidateCodeException(ResultEnum.IMG_ERR_CODE.getMessage());
        }
    }

}