package com.base.boot.common.security;


import org.springframework.security.access.expression.DenyAllPermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 注册自定义权限验证规则，本类不需要在CustomSecurityConfig里面被@Bean，已经帮我们自动做了，
 * 如果再加入的话，就会报错。下面这段看看就行了，不用加入使用。
 *
 * @author zbang
 */
//@Component
public class MyPermissionEvaluator extends DenyAllPermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication, Object targetUrl, Object permission) {
        // 获得loadUserByUsername()方法的结果
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        // 遍历用户所有角色
        for (GrantedAuthority authority : authorities) {
            String roleName = authority.getAuthority();
            if (roleName.equals(permission)) {
                return true;
            }
        }
        return false;
    }
}

