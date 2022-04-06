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
public class JkgcZbVo implements Serializable {
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

    /**
     * 指标集合
     * @author zhaoxin
     * @date 2021/12/7 16:38
     */
    private List<String> zbbsList;


    private static final long serialVersionUID = 1L;


    /**
     * 类型转换
     * @author zhaoxin
     * @date 2021/12/7 14:25
     * @return com.hy.dtn.vnm.monitor.entity.JkgcZbObj
     */
    public JkgcZbObj toObj(){
        JkgcZbObj obj = new JkgcZbObj();
        obj.setGcbs(this.gcbs);
        obj.setGcmc(this.gcmc);
//        obj.getZbbsList().addAll(this.zbbsList);
        return obj;
    }
}