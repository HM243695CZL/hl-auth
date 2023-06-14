package com.hl.admin.controller;

import com.hl.admin.service.UmsRoleService;
import com.hl.model.ums.UmsRole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author hl243695czyn
 * @since 2023-05-29
 */
@RestController
@RequestMapping("/admin/role")
@Api(tags = "角色管理", description = "角色管理")
public class UmsRoleController {

    @Autowired
    private UmsRoleService umsRoleService;


    // 获取全部
    @ApiOperation("获取全部角色")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<UmsRole> list(){
        return umsRoleService.list();
    }


}

