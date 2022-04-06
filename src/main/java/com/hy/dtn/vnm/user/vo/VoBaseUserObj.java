package com.hy.dtn.vnm.user.vo;

import com.hy.dtn.vnm.user.bo.BoAllUserObj;
import com.hy.dtn.vnm.user.bo.BoBaseUserObj;
import com.hy.dtn.vnm.user.orm.mysql.model.UserObj;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yjz
 * @version 1.0
 * @className VoBaseUserObj
 * @date 2020/11/19 18:20
 * @description 基础的前端用户信息类
 * @note 说明
 */
@ApiModel(description = "基础的前端用户信息类")
@Data
public class VoBaseUserObj implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = -5534782203259767987L;

    @ApiModelProperty(value = "用户id", name = "yhid", example = "xxxxxxx")
    private String yhid;

    @ApiModelProperty(value = "用户姓名", name = "yhxm", example = "张三")
    private String yhxm;

    @ApiModelProperty(value = "登录名", name = "dlm", example = "测试账号")
    private String dlm;

    @ApiModelProperty(value = "出生年月", name = "csny", example = "1994-01-02")
    private Date csny;

    @ApiModelProperty(value = "职务", name = "zw", example = "开发")
    private String zw;

    @ApiModelProperty(value = "身份证", name = "sfz", example = "321112333521421235")
    private String sfz;

    @ApiModelProperty(value = "性别", name = "xb", example = "1")
    private String xb;

    @ApiModelProperty(value = "联系电话", name = "lxdh", example = "13888451236")
    private String lxdh;

    @ApiModelProperty(value = "授权功能列表", name = "funcs", example = "[001,001001]")
    private List<String> funcs = new ArrayList<>();

    /**
     * @methodName VoBaseUserObj
     * @author yjz
     * @description 无参构造器
     * @date 2020/11/20 8:58
     * @returnParam
     * @note 修改说明
     */
    public VoBaseUserObj() {
    }

    /**
     * @param boAllUserObj
     * @methodName VoBaseUserObj
     * @author yjz
     * @description Bo转Vo
     * @date 2020/11/20 9:00
     * @returnParam
     * @note 修改说明
     */
    public VoBaseUserObj(BoAllUserObj boAllUserObj) {
        this.setYhid(boAllUserObj.getYhid());
        this.setYhxm(boAllUserObj.getYhxm());
        this.setDlm(boAllUserObj.getDlm());
        this.setCsny(boAllUserObj.getCsny());
        this.setLxdh(boAllUserObj.getLxdh());
        this.setSfz(boAllUserObj.getSfz());
        this.setXb(boAllUserObj.getXb());
        this.setZw(boAllUserObj.getZw());
        this.setFuncs(boAllUserObj.getFuncs());
    }

    /**
     * @param boBaseUserObj
     * @methodName VoBaseUserObj
     * @author yjz
     * @description 方法描述
     * @date 2020/11/20 9:17
     * @returnParam
     * @note 修改说明
     */
    public VoBaseUserObj(BoBaseUserObj boBaseUserObj) {
        this.setYhid(boBaseUserObj.getYhid());
        this.setYhxm(boBaseUserObj.getYhxm());
        this.setDlm(boBaseUserObj.getDlm());
        this.setCsny(boBaseUserObj.getCsny());
        this.setLxdh(boBaseUserObj.getLxdh());
        this.setSfz(boBaseUserObj.getSfz());
        this.setXb(boBaseUserObj.getXb());
        this.setZw(boBaseUserObj.getZw());
        this.setFuncs(boBaseUserObj.getFuncs());
    }

    /**
     * @param userObj DTO数据
     * @description Dto转Bo
     * @methodName VoBaseUserObj
     * @author yjz
     * @date 2021/03/08 14:41
     * @note 修改说明
     */
    public VoBaseUserObj(UserObj userObj) {
        this.yhid = userObj.getYhid();
        this.yhxm = userObj.getYhxm();
        this.dlm = userObj.getDlm();
        this.csny = userObj.getCsny();
        this.zw = userObj.getZw();
        this.sfz = userObj.getSfz();
        this.xb = userObj.getXb();
        this.lxdh = userObj.getLxdh();

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
    public BoAllUserObj toBo() {
        BoAllUserObj obj = new BoAllUserObj();
        obj.setYhid(this.getYhid());
        obj.setYhxm(this.getYhxm());
        obj.setDlm(this.getDlm());
        obj.setCsny(this.getCsny());
        obj.setLxdh(this.getLxdh());
        obj.setSfz(this.getSfz());
        obj.setXb(this.getXb());
        obj.setZw(this.getZw());
        return obj;
    }
}
