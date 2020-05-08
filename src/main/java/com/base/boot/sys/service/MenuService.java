package com.base.boot.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.base.boot.sys.entity.Menu;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zbang
 * @since 2020-04-29
 */
public interface MenuService extends IService<Menu> {

    public List<Menu> selectAllMenuByUserId(String userId);
}
