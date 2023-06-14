package com.hl.model.dto;

import com.hl.model.base.BasePageDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RolePageDto extends BasePageDto {

    @ApiModelProperty(value = "角色名称")
    private String name;
}
