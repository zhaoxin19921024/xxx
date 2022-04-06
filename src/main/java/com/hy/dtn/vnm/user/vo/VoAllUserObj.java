package com.hy.dtn.vnm.user.vo;

import com.hy.dtn.vnm.user.bo.BoAllUserObj;
import com.hy.dtn.vnm.user.orm.mysql.model.UserObj;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yjz
 * @version 1.0
 * @className
 * @date 2020/11/12 9:20
 * @description 用户信息前端类
 * @note 说明
 */
@ApiModel(description = "用户信息前端类")
@Data
public class VoAllUserObj extends VoBaseUserObj {
    /**
     * 序列号
     */
    private static final long serialVersionUID = 4121597322894285184L;


    @ApiModelProperty(value = "登录密码", name = "dlmm", example = "1a1a1a1aa11")
    private String dlmm;




    public VoAllUserObj() {

    }

    /**
     * @param user
     * @methodName BoUserObj
     * @author yjz
     * @description Bo数据转Vo数据
     * @date 2020/11/12
     * @returnParam
     * @note 修改说明
     */
    public VoAllUserObj(UserObj user) {
        this.setYhid(user.getYhid());
        this.setYhxm(user.getYhxm());
        this.setDlm(user.getDlm());
        this.setCsny(user.getCsny());
        this.setLxdh(user.getLxdh());
        this.setSfz(user.getSfz());
        this.setXb(user.getXb());
        this.setZw(user.getZw());
    }

    /**
     * @param userDev
     * @methodName VoUserObj
     * @author yjz
     * @description Bo转Vo
     * @date 2020/11/12 11:36
     * @returnParam
     * @note 修改说明
     */
    public VoAllUserObj(BoAllUserObj userDev) {
        this.setYhid(userDev.getYhid());
        this.setYhxm(userDev.getYhxm());
        this.setDlm(userDev.getDlm());
        this.setCsny(userDev.getCsny());
        this.setLxdh(userDev.getLxdh());
        this.setSfz(userDev.getSfz());
        this.setXb(userDev.getXb());
        this.setZw(userDev.getZw());
        this.setFuncs(userDev.getFuncs());
    }

    /**
     * @param
     * @methodName toBo
     * @author yjz
     * @description Vo转Bo
     * @date 2020/11/12 11:25
     * @returnParam com.hy.dtn.ops.user.bo.BoUserObj
     * @note 修改说明
     */
    @Override
    public BoAllUserObj toBo() {
        BoAllUserObj obj = super.toBo();
        obj.setDlmm(this.getDlmm());
        return obj;
    }


    /**
     * @param
     * @methodName toDto
     * @author yjz
     * @description Bo数据转为Dto数据
     * @date 2020/11/12
     * @returnParam com.hy.dtn.ops.user.orm.mysql.model.UserObj
     * @note 修改说明
     */
    public UserObj toDto() {
        UserObj obj = new UserObj();
        obj.setYhid(this.getYhid());
        obj.setYhxm(this.getYhxm());
        obj.setDlm(this.getDlm());
        obj.setDlmm(this.getDlmm());
        obj.setCsny(this.getCsny());
        obj.setLxdh(this.getLxdh());
        obj.setSfz(this.getSfz());
        obj.setXb(this.getXb());
        obj.setZw(this.getZw());
        return obj;
    }


}
