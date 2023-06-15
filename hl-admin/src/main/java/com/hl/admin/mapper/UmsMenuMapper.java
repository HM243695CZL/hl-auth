package com.hl.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hl.model.dto.InitMenuDto;
import com.hl.model.ums.UmsMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author hl243695czyn
 * @since 2023-05-29
 */
public interface UmsMenuMapper extends BaseMapper<UmsMenu> {

    /**
     * 根据用户id获取用户菜单
     * @param userId
     * @return
     */
    List<UmsMenu> findMenuListByUserId(@Param("userId") String userId);
}
