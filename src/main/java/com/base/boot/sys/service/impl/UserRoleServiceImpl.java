package com.base.boot.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.boot.sys.entity.UserRole;
import com.base.boot.sys.mapper.UserRoleMapper;
import com.base.boot.sys.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zbang
 * @since 2020-04-29
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
