package com.hl.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hl.admin.exception.ApiException;
import com.hl.admin.mapper.UmsMenuMapper;
import com.hl.admin.service.UmsMenuService;
import com.hl.admin.service.UmsRoleMenuService;
import com.hl.model.ums.UmsMenu;
import com.hl.model.ums.UmsRoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UmsMenuServiceImpl extends ServiceImpl<UmsMenuMapper, UmsMenu> implements UmsMenuService {

    @Autowired
    private UmsRoleMenuService roleMenuService;

    @Transactional
    @Override
    public Boolean delete(String id) {
        // 如果存在子菜单，则不能删除
        QueryWrapper<UmsMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UmsMenu::getPid, id);
        List<UmsMenu> list = list(queryWrapper);
        if (list.size() > 0) {
            throw new ApiException("当前菜单存在子菜单，不能删除");
        }
        // 清空菜单对应的所有角色
        QueryWrapper<UmsRoleMenu> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UmsRoleMenu::getMenuId, id);
        roleMenuService.remove(wrapper);
        return removeById(id);
    }
}
