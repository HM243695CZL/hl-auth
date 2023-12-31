package com.hl.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hl.admin.log.LogAnnotation;
import com.hl.admin.result.CommonPage;
import com.hl.admin.result.CommonResult;
import com.hl.admin.service.UmsAdminService;
import com.hl.admin.service.UmsMenuService;
import com.hl.model.dto.AdminPageDto;
import com.hl.model.dto.AllocationRoleDto;
import com.hl.model.dto.InitMenuDto;
import com.hl.model.dto.LoginParamDto;
import com.hl.model.ums.UmsAdmin;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 管理员表 前端控制器
 * </p>
 *
 * @author hl243695czyn
 * @since 2023-05-29
 */
@RestController
@RequestMapping("/admin/admin")
@Api(tags = "用户管理", description = "用户管理")
public class UmsAdminController {

    @Autowired
    private UmsAdminService umsAdminService;

    @Autowired
    private UmsMenuService menuService;


    // 分页
    @LogAnnotation
    @ApiOperation("分页查询")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public CommonResult page(@RequestBody AdminPageDto pageDTO) {
        Page<UmsAdmin> adminList = umsAdminService.pageList(pageDTO);
        return CommonResult.success(CommonPage.restPage(adminList));
    }

    // 新增
    @LogAnnotation
    @ApiOperation("新增用户")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public CommonResult save(@Valid @RequestBody UmsAdmin umsAdmin){
        return CommonResult.success(umsAdminService.create(umsAdmin));
    }

    // 更新
    @LogAnnotation
    @ApiOperation("更新用户")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public CommonResult update(@RequestBody UmsAdmin umsAdmin){
        return CommonResult.success(umsAdminService.updateAdmin(umsAdmin));
    }

    // 删除
    @LogAnnotation
    @ApiOperation("删除用户")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public CommonResult delete(@PathVariable String id){
        return CommonResult.success( umsAdminService.delete(id));
    }

    // 获取全部
    @LogAnnotation
    @ApiOperation("获取全部用户")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult list(){
        return CommonResult.success(umsAdminService.list());
    }

    // 查看
    @LogAnnotation
    @ApiOperation("查看用户")
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public CommonResult findOne(@PathVariable String id){
        return CommonResult.success(umsAdminService.view(id));
    }

    // 分配角色
    @LogAnnotation
    @ApiOperation("分配角色")
    @RequestMapping(value = "/allocationRole", method = RequestMethod.POST)
    public CommonResult allocationRole(@RequestBody AllocationRoleDto allocationRoleDto) {
        return CommonResult.success(umsAdminService.allocationRole(allocationRoleDto));
    }

    // 登录
    @LogAnnotation
    @ApiOperation("登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public CommonResult login(@RequestBody LoginParamDto loginParamDto, HttpServletRequest request) {
        String token = umsAdminService.login(loginParamDto, request);
        UmsAdmin userInfo = umsAdminService.getUserInfoByUsername(loginParamDto.getUsername());
        List<InitMenuDto> menuList = menuService.getMenuListByUserId(userInfo.getId());
        HashMap<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("userInfo", userInfo);
        tokenMap.put("menuList", menuList);
        return CommonResult.success(tokenMap);
    }


}

