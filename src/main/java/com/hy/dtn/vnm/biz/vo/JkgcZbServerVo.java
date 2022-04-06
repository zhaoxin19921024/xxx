package com.hy.dtn.vnm.biz.vo;

import com.hy.dtn.vnm.monitor.entity.JkgcZbObj;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * t_jkgc_zb
 * @author 
 */
@Data
public class JkgcZbServerVo implements Serializable {
    /**
     * 工程标识
     */
    private String gcbs;

    /**
     * 開始或結束標志
     */
    private String startOrStop;

    /**
     * 任务标识集合
     */
    private List<String> taskList;



}