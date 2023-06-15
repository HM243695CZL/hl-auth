package com.hl.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hl.admin.mapper.UmsRoleMapper;
import com.hl.admin.service.UmsRoleMenuService;
import com.hl.admin.service.UmsRoleService;
import com.hl.model.dto.AuthMenuDto;
import com.hl.model.dto.RolePageDto;
import com.hl.model.ums.UmsRole;
import com.hl.model.ums.UmsRoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UmsRoleServiceImpl extends ServiceImpl<UmsRoleMapper, UmsRole> implements UmsRoleService {

    @Autowired
    private UmsRoleMenuService roleMenuService;

    @Override
    public Page<UmsRole> pageList(RolePageDto roleDTO) {
        Page<UmsRole> page = new Page<>(roleDTO.getPageIndex(), roleDTO.getPageSize());
        QueryWrapper<UmsRole> queryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotEmpty(roleDTO.getName())) {
            queryWrapper.lambda().like(UmsRole::getName, roleDTO.getName());
        }
        return page(page, queryWrapper);
    }

    /**
     * 获取已分配的权限
     * @param id
     * @return
     */
    @Override
    public List<String> viewAuth(String id) {
        QueryWrapper<UmsRoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UmsRoleMenu::getRoleId, id);
        return roleMenuService.list(queryWrapper.select("menu_id")).stream().map(UmsRoleMenu::getMenuId).collect(Collectors.toList());
    }

    /**
     * 分配权限
     * @param authMenuDto
     * @return
     */
    @Transactional
    @Override
    public Boolean authMenu(AuthMenuDto authMenuDto) {
        // 先清空之前的授权
        QueryWrapper<UmsRoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UmsRoleMenu::getRoleId, authMenuDto.getId());
        roleMenuService.remove(queryWrapper);

        List<UmsRoleMenu> roleMenuList = setRoleAndMenuRelation(authMenuDto.getMenuIds(), authMenuDto.getId());
        return roleMenuService.saveBatch(roleMenuList);
    }

    /**
     * 设置角色和菜单的关联关系
     * @param menuIds
     * @param roleId
     * @return
     */
    private List<UmsRoleMenu> setRoleAndMenuRelation(List<String> menuIds, String roleId) {
        ArrayList<UmsRoleMenu> roleMenuList = new ArrayList<>();
        for (String menuId : menuIds) {
            UmsRoleMenu roleMenu = new UmsRoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenuList.add(roleMenu);
        }
        return roleMenuList;
    }
}
