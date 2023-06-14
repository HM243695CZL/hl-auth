package com.hl.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.hl.model.ums.UmsMenu;


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
}
