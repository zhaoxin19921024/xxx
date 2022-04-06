package com.hy.dtn.vnm.biz.vo;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author yjz
 * @version 1.0
 * @className VoPageInfo
 * @date 2020/12/03 11:20
 * @description 返回前台的自定义分页信息
 * @note 说明
 */
@ApiModel(description = "返回前台的自定义分页信息")
@Data
public class VoPageInfo<T> implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = -4301735260819626829L;

    @ApiModelProperty(value = "当前页", name = "current", example = "1")
    private int current;

    @ApiModelProperty(value = "每页的数量", name = "pageSize", example = "10")
    private int pageSize;

    @ApiModelProperty(value = "总记录数", name = "total", example = "1000")
    private long total;

    @ApiModelProperty(value = "结果集", name = "list")
    private List<T> list;

    /**
     * @methodName VoPageInfo
     * @author yjz
     * @description 无参构造器
     * @date 2020/12/03 11:45
     * @returnParam
     * @note 修改说明
     */
    public VoPageInfo() {
    }

    /**
     * @param pageInfo, list
     * @methodName VoPageInfo
     * @author yjz
     * @description PageInfo转为VoPageInfo，其中替换List为新的list
     * @date 2020/12/03 11:44
     * @returnParam
     * @note 修改说明
     */
    public VoPageInfo(PageInfo pageInfo, List<T> list) {
        this.list = list;
        this.total = pageInfo.getTotal();
        this.current = pageInfo.getPageNum();
        this.pageSize = pageInfo.getPageSize();
    }

}
