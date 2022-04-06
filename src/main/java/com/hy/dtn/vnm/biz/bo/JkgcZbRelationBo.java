package com.hy.dtn.vnm.biz.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @author zhaoxin
 * @date 2021/12/7 14:12
 */
@Data
public class JkgcZbRelationBo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 指标标识
     */
    private String zbbs;

    /**
     * 工程标识
     */
    private String gcbs;


}