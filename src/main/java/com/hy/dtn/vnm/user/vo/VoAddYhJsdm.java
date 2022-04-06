package com.hy.dtn.vnm.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author yjz
 * @version 1.0
 * @className VoAddYhJsdm
 * @date 2020/11/17 15:36
 * @description 前端用户提交角色列表信息
 * @note 说明
 */
@ApiModel(description = "前端用户提交角色列表信息")
@Data
public class VoAddYhJsdm {

    @ApiModelProperty(value = "用户id", name = "yhid", example = "xxxxxxx")
    private String yhid;

    @ApiModelProperty(value = "角色代码列表", name = "jsdmlist")
    private List<String> jsdmlist;

}
