package com.base.boot.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.boot.sys.entity.UserInfo;
import com.base.boot.sys.mapper.UserInfoMapper;
import com.base.boot.sys.service.UserInfoService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zbang
 * @since 2020-04-28
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

}
