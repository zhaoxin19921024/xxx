package com.hy.dtn.vnm.common;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 分页查询参数封装，用于继承
 *
 * @author yjz
 * @version 1.0.0 2020-10-22
 */
@Data
public abstract class PagingCondition implements Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = -5826914803574438815L;

    @ApiModelProperty(value = "每页显示多少条记录", example = "20")
    private int pageSize = 20;

    @ApiModelProperty(value = "当前页", example = "1")
    private int current = 1;

    @ApiModelProperty(value = "排序字段")
    private String sortField;

    @ApiModelProperty(value = "排序方式，只能是ASC或DESC", name = "sortOrder", example = "ASC")
    private String sortOrder = "ASC";


    /**
     * 设置每页显示多少条记录
     *
     * @param pageSize 每页显示多少条记录
     */
    public void setPageSize(int pageSize) {
        if (pageSize > 0) {
            this.pageSize = pageSize;
        }
    }

    /**
     * 设置当前页
     *
     * @param current 当前页
     */
    public void setPage(int current) {
        if (current > 0) {
            this.current = current;
        }
    }

    /**
     * 创建简单分页模型
     */
    public <T> Page<T> buildPage() {
        return new Page<>(current, pageSize);
    }
}