package com.hy.dtn.vnm.biz.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * t_jkrw_jkzb
 * @author 
 */
@Data
public class JkrwJkzbBo implements Serializable {
    /**
     * 查看数据字典 zblx
     */
    private String zbbs;

    private String rwbs;

    private String zbmc;

    private static final long serialVersionUID = 1L;
}