package com.hl.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hl.admin.constants.Constants;
import com.hl.admin.mapper.UmsAdminMapper;
import com.hl.admin.service.UmsAdminRoleService;
import com.hl.admin.service.UmsAdminService;
import com.hl.model.dto.AdminPageDto;
import com.hl.model.ums.UmsAdmin;
import com.hl.model.ums.UmsAdminRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UmsAdminServiceImpl extends ServiceImpl<UmsAdminMapper, UmsAdmin> implements UmsAdminService {


    @Autowired
    private UmsAdminRoleService adminRoleService;

    @Transactional
    @Override
    public Boolean create(UmsAdmin umsAdmin) {
        // 设置用户初始密码
        umsAdmin.setPassword(Constants.INIT_PASSWORD);
        boolean result = save(umsAdmin);
        List<UmsAdminRole> adminRoleList = setAdminAndRole(umsAdmin.getRoleIds(), umsAdmin.getId());
        adminRoleService.saveBatch(adminRoleList);
        return result;
    }

    @Override
    public Page<UmsAdmin> pageList(AdminPageDto pageDTO) {
        QueryWrapper<UmsAdmin> queryWrapper = new QueryWrapper<>();
        Page<UmsAdmin> page = new Page<>(pageDTO.getPageIndex(), pageDTO.getPageSize());
        return page(page, queryWrapper);
    }

    @Override
    public Boolean updateAdmin(UmsAdmin umsAdmin) {
        UmsAdmin admin = getById(umsAdmin.getId());
        umsAdmin.setPassword(admin.getPassword());
        boolean result = updateById(umsAdmin);
        // 清空当前用户所有角色
        QueryWrapper<UmsAdminRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UmsAdminRole::getAdminId, umsAdmin.getId());
        adminRoleService.remove(queryWrapper);

        List<UmsAdminRole> adminRoleList = setAdminAndRole(umsAdmin.getRoleIds(), umsAdmin.getId());
        adminRoleService.saveBatch(adminRoleList);
        return result;
    }

    @Override
    public UmsAdmin view(String id) {
        UmsAdmin admin = getById(id);
        // 查询用户所有的角色id
        List<String> roleIds = adminRoleService.list(new QueryWrapper<UmsAdminRole>().eq("admin_id", id))
                .stream().map(UmsAdminRole::getRoleId).collect(Collectors.toList());
        admin.setRoleIds(roleIds);
        return admin;
    }

    @Override
    public Boolean delete(String id) {
        // 清空当前用户的所有角色
        QueryWrapper<UmsAdminRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UmsAdminRole::getAdminId, id);
        adminRoleService.remove(queryWrapper);
        return removeById(id);
    }

    /**
     * 设置用户和角色的关联关系
     * @param roleIds
     * @param userId
     * @return
     */
    private List<UmsAdminRole> setAdminAndRole(List<String> roleIds, String userId) {
        List<UmsAdminRole> adminRoleList = new ArrayList<>();
        for (String roleId : roleIds) {
            UmsAdminRole adminRole = new UmsAdminRole();
            adminRole.setAdminId(userId);
            adminRole.setRoleId(roleId);
            adminRoleList.add(adminRole);
        }
        return adminRoleList;
    }
}
