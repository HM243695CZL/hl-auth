package com.hl.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hl.model.dto.RolePageDto;
import com.hl.model.ums.UmsRole;

public interface UmsRoleService extends IService<UmsRole> {

    Page<UmsRole> pageList(RolePageDto roleDTO);
}
