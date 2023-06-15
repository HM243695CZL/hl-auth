package com.hl.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.hl.model.dto.InitMenuDto;
import com.hl.model.ums.UmsMenu;

import java.util.List;


/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author hl243695czyn
 * @since 2023-05-29
 */
public interface UmsMenuService extends IService<UmsMenu> {

    Boolean delete(String id);

    /**
     * 根据用户id获取用户菜单
     * @param id
     * @return
     */
    List<InitMenuDto> getMenuListByUserId(String id);
}
