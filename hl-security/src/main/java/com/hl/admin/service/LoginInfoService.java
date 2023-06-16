package com.hl.admin.service;

import com.hl.model.dto.InitMenuDto;
import com.hl.model.ums.UmsAdmin;

import java.util.List;

public interface LoginInfoService {

    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    UmsAdmin getUserInfoByUsername(String username);

    /**
     * 根据用户id获取用户菜单
     * @param id
     * @return
     */
    List<InitMenuDto> getMenuListByUserId(String id);
}
