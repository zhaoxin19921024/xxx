package com.hy.dtn.vnm.biz.vo;

import cn.hutool.core.util.ArrayUtil;
import com.hy.dtn.vnm.monitor.entity.JkgcZbtxObj;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author zhaoxin
 * @date 2021/12/7 14:12
 */
@Data
public class JkgcZbtxVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 图形标识
     */
    private String txbs;

    /**
     *图形名称
     */
    private String txmc;

    /**
     * 工程标识
     */
    private String gcbs;

    /**
     * 指标标识
     */
    private Integer zbbs;

    private List<String> rwbsList;

    /**
     * rwlx
     */
    private Integer rwlx;

    public JkgcZbtxObj toObj(){
        JkgcZbtxObj obj = new JkgcZbtxObj();
        obj.setTxbs(this.txbs);
        obj.setTxmc(this.txmc);
        obj.setGcbs(this.gcbs);
        obj.setRwlx(this.rwlx);
        obj.setZbbs(this.zbbs);
        if(ArrayUtil.isNotEmpty(this.rwbsList)){
            obj.getRwbsList().addAll(this.rwbsList);
        }
        return obj;
    }

}