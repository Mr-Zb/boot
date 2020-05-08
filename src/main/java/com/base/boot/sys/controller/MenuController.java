package com.base.boot.sys.controller;


import com.base.boot.common.VO.ResultVO;
import com.base.boot.sys.entity.Menu;
import com.base.boot.sys.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
@Api(tags = "菜单")
@RestController
@RequestMapping("/sys/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;
    //@PreAuthorize("principal.username.equals(#username)")

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/aa")
    public String ss() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "dd" + authentication;
    }

    @ApiOperation(value = "新增菜单或按钮")
    @PostMapping("save")
    public Map<String, Object> add(@RequestBody Menu menu) {
        menuService.save(menu);
        return ResultVO.success();
    }
}
