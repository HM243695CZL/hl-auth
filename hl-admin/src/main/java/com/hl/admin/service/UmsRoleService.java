package com.hl.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hl.model.dto.AuthMenuDto;
import com.hl.model.dto.RolePageDto;
import com.hl.model.ums.UmsRole;

import java.util.List;

public interface UmsRoleService extends IService<UmsRole> {

    Page<UmsRole> pageList(RolePageDto roleDTO);

    List<String> viewAuth(String id);

    /**
     * 分配权限
     * @param authMenuDto
     * @return
     */
    Boolean authMenu(AuthMenuDto authMenuDto);
}
