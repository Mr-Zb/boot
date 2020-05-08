package com.base.boot.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.base.boot.common.utils.PageUtils;
import com.base.boot.sys.entity.Role;

import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zbang
 * @since 2020-04-29
 */
public interface RoleService extends IService<Role> {

    PageUtils queryPage(Map<String, Object> params);
}
