package com.hl.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 设置角色对应菜单权限的DTO
 */
@Data
public class AuthMenuDto {

    @ApiModelProperty(value = "菜单id数组")
    private List<String> menuIds;

    @ApiModelProperty(value = "角色id")
    private String id;
}
