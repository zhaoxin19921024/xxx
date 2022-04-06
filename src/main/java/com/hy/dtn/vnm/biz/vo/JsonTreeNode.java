package com.hy.dtn.vnm.biz.vo;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yjz
 * @version 1.0
 * @className JsonTreeNode
 * @date 2020/11/12 13:48
 * @description 树节点对象
 * @note 说明
 * 2020-11-19 yjz 新增显示顺序字段，用于后续前端拖拽修改，无法获取显示顺序
 */
@ApiModel(description = "树节点对象")
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@NoArgsConstructor
public class JsonTreeNode implements Serializable {
    /**
     * 序列号
     */
    private static final long serialVersionUID = 4175449037095378826L;

    @ApiModelProperty(value = "节点名称", name = "title", example = "测试节点")
    private String title = "";

    @ApiModelProperty(value = "节点对象ID", name = "key", example = "XXXXXXXXXXXXX")
    private String key = "";

    @ApiModelProperty(value = "层级码", name = "cjm", example = "001001")
    private String cjm = "";

    @ApiModelProperty(value = "显示顺序", name = "xssx", example = "1")
    private String xssx = "";

    @ApiModelProperty(value = "父级层级码", name = "fjcjm", example = "001")
    private String fjcjm = "";

    @ApiModelProperty(value = "是否为叶子节点", name = "isLeaf", example = "false")
    @JSONField(name = "isLeaf")
    private boolean isLeaf = false;

    @ApiModelProperty(value = "子节点", name = "children", example = "[]")
    private List<JsonTreeNode> children = new ArrayList<>();

    /**
     * @param title 标题
     * @param cjm   层级码
     * @param fjcjm 父级层级码
     * @description 生成实体对象
     * @methodName JsonTreeNode
     * @author yjz
     * @date 2021/03/12 9:38
     * @note 修改说明
     */
    public JsonTreeNode(String title, String cjm, String fjcjm) {
        this.title = title;
        this.key = IdUtil.simpleUUID();
        this.cjm = cjm;
        this.fjcjm = fjcjm;
        this.isLeaf = false;
    }
}
