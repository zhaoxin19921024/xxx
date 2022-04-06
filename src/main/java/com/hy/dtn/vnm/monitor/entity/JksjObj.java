package com.hy.dtn.vnm.monitor.entity;

import io.swagger.models.auth.In;
import lombok.Data;

/**
 * @author zhaoxin
 * @date 2021年12月09日 14:22
 *
 * 监控数据实体
 */

@Data
public class JksjObj {

    // 时间
    private String time;

    // 数值
    private Float value;

    // 类型名称
    private String categoryName;

    // 类型标识
    private String categoryId;

}
