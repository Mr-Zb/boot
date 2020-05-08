package com.base.boot.sys.controller;


import com.base.boot.common.VO.ResultVO;
import com.base.boot.common.utils.PageUtils;
import com.base.boot.common.validator.ValidatorUtils;
import com.base.boot.sys.entity.Role;
import com.base.boot.sys.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zbang
 * @since 2020-04-29
 */
@Api(tags = "角色")
@RestController
@RequestMapping("/sys/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "角色分页列表")
    @PreAuthorize("hasRole('sys:role:list')")
    @PostMapping(path = "list")
    public Map<String, Object> list(@RequestParam Map<String, Object> params) {
        PageUtils page = roleService.queryPage(params);
        return ResultVO.success("page", page);
    }

    //@PreAuthorize("hasRole('sys:role:list')")
    @ApiOperation(value = "新增角色")
    @PostMapping("save")
    public Map<String, Object> add(@RequestBody Role role) {
        ValidatorUtils.validateEntity(role);
        roleService.save(role);
        return ResultVO.success();
    }
}
