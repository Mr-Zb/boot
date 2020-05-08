package com.base.boot.common.security;

import com.base.boot.common.enums.ResultEnum;
import com.base.boot.common.exception.JwtException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: zbang
 * @Date: 2020/5/3 1:24 下午
 */
@Component
public class JwtAuthorizationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtHelper jwtHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if (requestURI.contains("sys") || requestURI.contains("user")) {
            String token = request.getHeader("token");
            Claims claims;
            if (token == null) {
                throw new JwtException(ResultEnum.LOGIN_IS_NULL.getCode(), ResultEnum.LOGIN_IS_NULL.getMessage());
            } else {
                try {
                    claims = jwtHelper.parseJWT(token);
                    if (claims.getExpiration().before(new Date())) {
                        throw new JwtException(ResultEnum.LOGIN_IS_OVERDUE.getCode(), ResultEnum.LOGIN_IS_OVERDUE.getMessage());
                    }
                    List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
                    String roles = (String) claims.get("role");
                    String[] split = roles.split(",");
                    for (String role : split) {
                        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role);
                        grantedAuthorities.add(grantedAuthority);
                    }
                    UserDetails userDetails = new User("username", "userInfo.getPassword()", grantedAuthorities);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        chain.doFilter(request, response);
    }
}
