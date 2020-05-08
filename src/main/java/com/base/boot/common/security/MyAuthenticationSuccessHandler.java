package com.base.boot.common.security;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.base.boot.common.VO.ResultVO;
import com.base.boot.sys.entity.Menu;
import com.base.boot.sys.entity.UserInfo;
import com.base.boot.sys.mapper.UserInfoMapper;
import com.base.boot.sys.service.MenuService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zbang
 */
@Component
public class MyAuthenticationSuccessHandler
        extends SavedRequestAwareAuthenticationSuccessHandler {
    /**
     * 在application配置文件中配置登陆的类型是JSON数据响应还是做页面响应
     */
    private static ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private MenuService menuMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException {
        //修改登陆时间
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("username", name);
        UserInfo userInfo = userInfoMapper.selectOne(wrapper);
        UpdateWrapper<UserInfo> wrapper2 = new UpdateWrapper<>();
        wrapper2.eq("username", name);
        userInfo.setUpdateTime(LocalDateTime.now());
        userInfoMapper.update(userInfo, wrapper2);
        //TODO 插入登陆记录
        //
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        List<Menu> menus = menuMapper.selectAllMenuByUserId(userInfo.getId());
        List<String> list = new ArrayList<>();
        if (menus != null) {
            menus.forEach(menu -> {
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_" + menu.getPerms());
                grantedAuthorities.add(grantedAuthority);
                list.add("ROLE_" + menu.getPerms());
            });

        } else {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_USER");
            grantedAuthorities.add(grantedAuthority);
            list.add("ROLE_USER");
        }
        //返回token
        String token = jwtHelper.generateJWT(userInfo.getId(), userInfo.getUsername(), org.apache.commons.lang.StringUtils.join(list.toArray(), ','));
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("token", token);
        response.getWriter().write(objectMapper.writeValueAsString(ResultVO.success()));
    }
}
