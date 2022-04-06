package com.hy.dtn.vnm.orm.mysql.dao;

import com.hy.dtn.vnm.orm.mysql.model.XtCzrz;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yjz
 * @version 1.0
 * @className XtCzrzDao
 * @date 2020/12/01 16:06
 * @description 系统操作日志接口
 * @note 说明
 */
@Mapper
public interface XtCzrzDao {

    /**
     * @param xtCzrz
     * @methodName addXtCzrz
     * @author yjz
     * @description 单条插入系统操作日志
     * @date 2020/12/01 16:08
     * @returnParam int
     * @note 修改说明
     */
    int addXtCzrz(XtCzrz xtCzrz);

}
