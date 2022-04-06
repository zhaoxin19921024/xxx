package com.hy.dtn.vnm.biz.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author yjz
 * @version 1.0
 * @className JsonTreeTable
 * @date 2021/03/08 15:56
 * @description 表格树节点对象
 * @note 说明
 */
@ApiModel(description = "表格树节点对象")
@NoArgsConstructor
@Data
public class JsonTreeTable implements Serializable {
    /**
     * 序列号
     */
    private static final long serialVersionUID = -4777819483149562079L;

    @ApiModelProperty(value = "节点对象ID", name = "key", example = "XXXXXXXXXXXXX")
    private String key = "";

    @ApiModelProperty(value = "节点名称", name = "title", example = "测试节点")
    private String text = "";

    @ApiModelProperty(value = "父节点数据", name = "parentKey", example = "父节点数据")
    private String parentKey = "";

}
