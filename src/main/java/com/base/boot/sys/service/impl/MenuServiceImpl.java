package com.base.boot.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.boot.sys.entity.Menu;
import com.base.boot.sys.entity.RoleMenu;
import com.base.boot.sys.entity.UserRole;
import com.base.boot.sys.mapper.MenuMapper;
import com.base.boot.sys.mapper.RoleMenuMapper;
import com.base.boot.sys.mapper.UserRoleMapper;
import com.base.boot.sys.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zbang
 * @since 2020-04-29
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {


    @Autowired
    private RoleMenuMapper roleMenuMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> selectAllMenuByUserId(String userId) {
        QueryWrapper<UserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        UserRole userRole = userRoleMapper.selectOne(wrapper);
        List<String> list = new ArrayList<>();
        if (userRole != null) {
            QueryWrapper<RoleMenu> wrapper2 = new QueryWrapper<>();
            wrapper2.eq("role_id", userRole.getRoleId());
            List<RoleMenu> roleMenus = roleMenuMapper.selectList(wrapper2);
            roleMenus.forEach(rm -> {
                list.add(rm.getMenuId());
            });
            return menuMapper.selectBatchIds(list);
        } else {
            return null;
        }

    }

}
