package com.hy.dtn.vnm.user.bo;

import com.hy.dtn.vnm.user.orm.mysql.model.UserObj;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yjz
 * @version 1.0
 * @className BoBaseUserObj
 * @date 2020/11/20 9:01
 * @description 描述
 * @note 说明
 */
@Data
public class BoBaseUserObj implements Serializable {
    /**
     * 序列号
     */
    private static final long serialVersionUID = 264851418219810705L;
    /**
     * 用户id
     */
    private String yhid;

    /**
     * 用户姓名
     */
    private String yhxm;

    /**
     * 登录名
     */
    private String dlm;

    /**
     * 出生年月
     */
    private Date csny;

    /**
     * 职务
     */
    private String zw;

    /**
     * 身份证
     */
    private String sfz;

    /**
     * 性别
     */
    private String xb;

    /**
     * 联系电话
     */
    private String lxdh;

    /**
     * 授权功能列表
     */
    private List<String> funcs = new ArrayList<>();

    /**
     * @methodName BoBaseUserObj
     * @author yjz
     * @description 无参构造器
     * @date 2020/11/20 9:15
     * @returnParam
     * @note 修改说明
     */
    public BoBaseUserObj() {
    }

    /**
     * @param user
     * @methodName BoBaseUserObj
     * @author yjz
     * @description 方法描述
     * @date 2020/11/20 9:16
     * @returnParam
     * @note 修改说明
     */
    public BoBaseUserObj(UserObj user) {
        this.setYhid(user.getYhid());
        this.setYhxm(user.getYhxm());
        this.setDlm(user.getDlm());
        this.setCsny(user.getCsny());
        this.setLxdh(user.getLxdh());
        this.setSfz(user.getSfz());
        this.setXb(user.getXb());
        this.setZw(user.getZw());
    }
}
