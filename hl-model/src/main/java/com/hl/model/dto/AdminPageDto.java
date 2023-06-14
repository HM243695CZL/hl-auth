package com.hl.model.dto;

import com.hl.model.base.BasePageDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AdminPageDto extends BasePageDto {

    @ApiModelProperty(value = "用户名称")
    private String username;
}
