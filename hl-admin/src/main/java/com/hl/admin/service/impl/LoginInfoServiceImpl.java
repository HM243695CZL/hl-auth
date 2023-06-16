package com.hl.admin.service.impl;

import com.hl.admin.service.LoginInfoService;
import com.hl.admin.service.UmsAdminService;
import com.hl.admin.service.UmsMenuService;
import com.hl.model.dto.InitMenuDto;
import com.hl.model.ums.UmsAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginInfoServiceImpl implements LoginInfoService {

    @Autowired
    private UmsAdminService adminService;

    @Autowired
    private UmsMenuService menuService;

    @Override
    public UmsAdmin getUserInfoByUsername(String username) {
        return adminService.getUserInfoByUsername(username);
    }

    @Override
    public List<InitMenuDto> getMenuListByUserId(String id) {
        return menuService.getMenuListByUserId(id);
    }
}
