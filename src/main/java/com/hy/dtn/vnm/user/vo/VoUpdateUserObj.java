package com.hy.dtn.vnm.user.vo;

import com.hy.dtn.vnm.user.bo.BoAllUserObj;
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
 * @className
 * @date 2020/11/12 9:20
 * @description 用户信息前端类
 * @note 说明
 */
@ApiModel(description = "用户信息前端类")
@Data
public class VoUpdateUserObj implements Serializable {
    /**
     * 序列号
     */
    private static final long serialVersionUID = 4121597322894285184L;

    @ApiModelProperty(value = "用户id", name = "yhid", example = "xxxxxxx")
    private String yhid;

    @ApiModelProperty(value = "用户姓名", name = "yhxm", example = "张三")
    private String yhxm;

    @ApiModelProperty(value = "出生年月", name = "csny", example = "19940102")
    private Date csny;

    @ApiModelProperty(value = "职务", name = "zw", example = "开发")
    private String zw;

    @ApiModelProperty(value = "身份证", name = "sfz", example = "321112333521421235")
    private String sfz;

    @ApiModelProperty(value = "性别", name = "xb", example = "1")
    private String xb;

    @ApiModelProperty(value = "联系电话", name = "lxdh", example = "13888451236")
    private String lxdh;

    /**
     * 授权功能列表
     */
    private List<String> funcs = new ArrayList<>();


    public VoUpdateUserObj() {

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
    public VoUpdateUserObj(UserObj user) {
        this.setYhid(user.getYhid());
        this.setYhxm(user.getYhxm());
        this.setCsny(user.getCsny());
        this.setLxdh(user.getLxdh());
        this.setSfz(user.getSfz());
        this.setXb(user.getXb());
        this.setZw(user.getZw());
    }
    
    /**
     * @methodName VoUserObj
     * @author yjz
     * @description Bo转Vo
     * @date 2020/11/12 11:36
     * @param userDev
     * @returnParam 
     * @note 修改说明
     */
    public VoUpdateUserObj(BoAllUserObj userDev) {
        this.setYhid(userDev.getYhid());
        this.setYhxm(userDev.getYhxm());
        this.setCsny(userDev.getCsny());
        this.setLxdh(userDev.getLxdh());
        this.setSfz(userDev.getSfz());
        this.setXb(userDev.getXb());
        this.setZw(userDev.getZw());
        this.setFuncs(userDev.getFuncs());
    }

    /**
     * @methodName toBo
     * @author yjz
     * @description Vo转Bo
     * @date 2020/11/12 11:25
     * @param
     * @returnParam com.hy.dtn.ops.user.bo.BoUserObj
     * @note 修改说明
     */
    public BoAllUserObj toBo() {
        BoAllUserObj obj = new BoAllUserObj();
        obj.setYhid(this.getYhid());
        obj.setYhxm(this.getYhxm());
        obj.setCsny(this.getCsny());
        obj.setLxdh(this.getLxdh());
        obj.setSfz(this.getSfz());
        obj.setXb(this.getXb());
        obj.setZw(this.getZw());
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
        obj.setCsny(this.getCsny());
        obj.setLxdh(this.getLxdh());
        obj.setSfz(this.getSfz());
        obj.setXb(this.getXb());
        obj.setZw(this.getZw());
        return obj;
    }
    

}
