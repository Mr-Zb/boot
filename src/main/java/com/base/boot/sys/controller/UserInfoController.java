package com.base.boot.sys.controller;


import com.base.boot.common.VO.ResultVO;
import com.base.boot.sys.entity.UserInfo;
import com.base.boot.sys.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zbang
 * @since 2020-04-28
 */
@RestController
@RequestMapping("/sys/user")
public class UserInfoController {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @PostMapping("register")
    public Map<String, Object> register(@RequestBody UserInfo userInfo) {
        //TODO 校验参数
        String password = userInfo.getPassword();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode(password);
        userInfo.setPassword(encode);
        userInfoMapper.insert(userInfo);
        return ResultVO.success();
    }

}
