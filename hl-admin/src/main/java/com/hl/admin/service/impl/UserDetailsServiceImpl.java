package com.hl.admin.service.impl;

import com.hl.admin.custom.CustomUser;
import com.hl.admin.exception.ApiException;
import com.hl.admin.service.UmsAdminService;
import com.hl.model.ums.UmsAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UmsAdminService adminService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UmsAdmin admin = adminService.getUserInfoByUsername(username);
        if (admin == null) {
            throw new ApiException("用户不存在");
        }
        return new CustomUser(admin, Collections.emptyList());
    }
}
