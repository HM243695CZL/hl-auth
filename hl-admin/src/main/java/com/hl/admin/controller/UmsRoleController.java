package com.hl.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hl.admin.result.CommonPage;
import com.hl.admin.result.CommonResult;
import com.hl.admin.service.UmsRoleService;
import com.hl.model.dto.RolePageDto;
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

    // 分页
    @ApiOperation("分页查询")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public CommonResult page(@RequestBody RolePageDto roleDTO) {
        Page<UmsRole> roleList = umsRoleService.pageList(roleDTO);
        return CommonResult.success(CommonPage.restPage(roleList));
    }


    // 获取全部
    @ApiOperation("获取全部角色")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult list(){
        return CommonResult.success(umsRoleService.list());
    }


}

