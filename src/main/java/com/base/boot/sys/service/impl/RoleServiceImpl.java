package com.base.boot.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.boot.common.utils.PageUtils;
import com.base.boot.common.utils.Query;
import com.base.boot.sys.entity.Role;
import com.base.boot.sys.mapper.RoleMapper;
import com.base.boot.sys.service.RoleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zbang
 * @since 2020-04-29
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String roleName = (String) params.get("roleName");
        IPage<Role> page = this.page(new Query<Role>().getPage(params),
                new QueryWrapper<Role>().like(StringUtils.isNotBlank(roleName), "role_name", roleName));
        return new PageUtils(page);
    }

}
