package com.hy.dtn.vnm.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yjz
 * @version 1.0
 * @className VoRoleAndFuncList
 * @date 2021/03/10 17:13
 * @description 描述
 * @note 说明
 */
@ApiModel(description = "返回前端的角色代码和功能代码")
@Data
public class VoRoleAndFuncList implements Serializable {
    /**
     * 序列号
     */
    private static final long serialVersionUID = 4091426697078207860L;

    @ApiModelProperty(value = "角色列表", name = "roleList")
    private List<String> roleList = new ArrayList<>();

    @ApiModelProperty(value = "功能列表", name = "funcList")
    private List<String> funcList = new ArrayList<>();

}
