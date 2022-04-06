package com.hy.dtn.vnm.user.vo;

import com.hy.dtn.vnm.user.bo.BoRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yjz
 * @version 1.0
 * @className BoRole
 * @date 2020/11/11 17:51
 * @description 角色信息
 * @note 说明
 */
@Data
public class VoRole implements Serializable {
    /**
     * 序列号
     */
    private static final long serialVersionUID = -7302094164048010591L;

    @ApiModelProperty(value = "角色代码", name = "jsdm", example = "6ac9641944e1457ba61810eccef91091")
    private String jsdm;

    @ApiModelProperty(value = "角色名称", name = "jsmc", example = "测试数据")
    private String jsmc;

    @ApiModelProperty(value = "角色说明", name = "jssm", example = "测试XXXXXX数据")
    private String jssm;

    @ApiModelProperty(value = "显示顺序", name = "xssx", example = "1")
    private Integer xssx;

    @ApiModelProperty(value = "功能列表", name = "gnlist", example = "['001','001001','001002','001003','001004']")
    private List<String> gnlist = new ArrayList<String>();


    public VoRole() {

    }

    /**
     * @param obj
     * @methodName VoRole
     * @author yjz
     * @description Bo转Vo
     * @date 2020/11/12 15:33
     * @returnParam
     * @note 修改说明
     */
    public VoRole(BoRole obj) {
        this.setJsdm(obj.getJsdm());
        this.setJsmc(obj.getJsmc());
        this.setJssm(obj.getJssm());
        this.setXssx(obj.getXssx());
    }

    /**
     * @param
     * @methodName toBo
     * @author yjz
     * @description Vo转Bo
     * @date 2020/11/12 14:39
     * @returnParam com.hy.dtn.ops.user.bo.BoRole
     * @note 修改说明
     */
    public BoRole toBo() {
        BoRole obj = new BoRole();
        obj.setJsdm(this.getJsdm());
        obj.setJsmc(this.getJsmc());
        obj.setJssm(this.getJssm());
        obj.setXssx(this.getXssx());
        obj.setGnlist(this.getGnlist());
        return obj;
    }
}