package com.hl.admin.controller;


import com.hl.admin.log.LogAnnotation;
import com.hl.admin.result.CommonResult;
import com.hl.admin.service.UmsMenuService;
import com.hl.model.ums.UmsMenu;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.*;


import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author hl243695czyn
 * @since 2023-05-29
 */
@RestController
@RequestMapping("/admin/menu")
@Api(tags = "菜单管理", description = "菜单管理")
public class UmsMenuController {

    @Autowired
    private UmsMenuService umsMenuService;

    // 新增
    @LogAnnotation
    @ApiOperation("新增菜单")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public CommonResult save(@RequestBody UmsMenu umsMenu){
        return CommonResult.success(umsMenuService.save(umsMenu));
    }

    // 更新
    @LogAnnotation
    @ApiOperation("更新菜单")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public CommonResult update(@RequestBody UmsMenu umsMenu){
        return CommonResult.success(umsMenuService.updateById(umsMenu));
    }

    // 删除
    @LogAnnotation
    @ApiOperation("删除菜单")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public CommonResult delete(@PathVariable String id){
        return CommonResult.success( umsMenuService.delete(id));
    }

    // 获取全部
    @LogAnnotation
    @ApiOperation("获取全部菜单")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult list(){
        return CommonResult.success(umsMenuService.getMenuList());
    }

    // 查看
    @LogAnnotation
    @ApiOperation("查看菜单")
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public CommonResult findOne(@PathVariable String id){
        return CommonResult.success(umsMenuService.getById(id));
    }

}

