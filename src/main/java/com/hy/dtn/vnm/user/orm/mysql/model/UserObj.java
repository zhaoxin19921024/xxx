package com.hy.dtn.vnm.user.orm.mysql.model;

import com.hy.dtn.vnm.user.vo.VoUserSearch;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

/**
 * @author yjz
 * @version 1.0
 * @className UserObj
 * @date 2020/11/11 17:46
 * @description 用户obj
 * @note 说明
 */
@NoArgsConstructor
@Data
public class UserObj implements Serializable {
    /**
     * 序列号
     */
    private static final long serialVersionUID = 3000591089719757184L;

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
     * 登录密码
     */
    private String dlmm;

    /**
     * 出生年月日
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
     * @param voUserSearch 查询信息
     * @description Vo查询类转为Dto
     * @methodName UserObj
     * @author yjz
     * @date 2021/03/08 14:36
     * @note 修改说明
     */
    public UserObj(VoUserSearch voUserSearch) {
        this.yhxm = voUserSearch.getYhxm();
        this.sfz = voUserSearch.getSfz();
    }
}