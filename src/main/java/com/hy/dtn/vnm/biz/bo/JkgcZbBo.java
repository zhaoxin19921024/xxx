package com.hy.dtn.vnm.biz.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * t_jkgc_zb
 * @author 
 */
@Data
public class JkgcZbBo implements Serializable {
    /**
     * 工程标识
     */
    private String gcbs;

    /**
     * 工程名称
     */
    private String gcmc;

    /**
     * 创建时间
     */
    private Date cjsj;

    private static final long serialVersionUID = 1L;
}