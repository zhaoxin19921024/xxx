package com.hy.dtn.vnm.monitor.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * t_jkgc_zb
 * @author 
 */
@Data
public class JkgcZbObj implements Serializable {
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String cjsj;

    /**
     * 指标集合
     * @author zhaoxin
     * @date 2021/12/7 16:38
     */
    private List<String> zbbsList;

    private static final long serialVersionUID = 1L;
}