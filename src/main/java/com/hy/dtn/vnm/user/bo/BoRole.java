package com.hy.dtn.vnm.user.bo;

import com.hy.dtn.vnm.user.orm.mysql.model.RoleObj;
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
public class BoRole implements Serializable {
    /**
     * 序列号
     */
    private static final long serialVersionUID = -7302094164048010591L;

    /**
     * 角色代码
     */
    private String jsdm;

    /**
     * 角色名称
     */

    private String jsmc;

    /**
     * 角色说明
     */
    private String jssm;

    /**
     * 显示顺序
     */
    private Integer xssx;

    /**
     * 功能列表
     */
    private List<String> gnlist = new ArrayList<String>();

    /**
     * @param obj
     * @methodName BoRole
     * @author yjz
     * @description Dto转Bo
     * @date 2020/11/12 16:40
     * @returnParam
     * @note 修改说明
     */
    public BoRole(RoleObj obj) {
        this.setJsdm(obj.getJsdm());
        this.setJsmc(obj.getJsmc());
        this.setJssm(obj.getJssm());
        this.setXssx(obj.getXssx());
    }

    /**
     * @methodName BoRole
     * @author yjz
     * @description 无参构造器
     * @date 2020/11/12 16:44
     * @returnParam
     * @note 修改说明
     */
    public BoRole() {
    }

    /**
     * @methodName toDto
     * @author yjz
     * @description Bo转Dto
     * @date 2020/11/12 16:42
     * @returnParam com.hy.dtn.ops.user.orm.mysql.model.RoleObj
     * @note 修改说明
     */
    public RoleObj toDto() {
        RoleObj obj = new RoleObj();
        obj.setJsdm(this.getJsdm());
        obj.setJsmc(this.getJsmc());
        obj.setJssm(this.getJssm());
        obj.setXssx(this.getXssx());
        return obj;
    }
}