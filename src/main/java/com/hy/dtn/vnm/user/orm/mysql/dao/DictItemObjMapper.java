package com.hy.dtn.vnm.user.orm.mysql.dao;

import com.hy.dtn.vnm.user.orm.mysql.model.DictItemObj;
import com.hy.dtn.vnm.user.orm.mysql.model.DictItemObjKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yjz
 * @version 1.0
 * @className DictItemObjMapper
 * @date 2020/11/12 15:11
 * @description 描述
 * @note 说明
 */
@Mapper
public interface DictItemObjMapper {

    /**
     * @methodName selectAll
     * @author yjz
     * @description 查询所有数据
     * @date 2020/12/03 12:05
     * @returnParam java.util.List<com.hy.dtn.ops.user.orm.mysql.model.DictItemObj>
     * @note 修改说明
     */
    List<DictItemObj> selectAll();

    int deleteByPrimaryKey(DictItemObjKey key);

    int insert(DictItemObj record);

    int insertSelective(DictItemObj record);

    DictItemObj selectByPrimaryKey(DictItemObjKey key);

    List<DictItemObj> selectByZdbs(@Param("zdbs") String zdbs);
    
    /**
     * @description 根据关键字批量查询
     * @methodName selectByZdbsList
     * @author yjz
     * @date 2021/03/11 20:03
     * @param zdbsList
     * @return java.util.List<com.hy.dtn.ops.user.orm.mysql.model.DictItemObj>
     * @note 修改说明
     */
    List<DictItemObj> selectByZdbsList(@Param("list") List<String> zdbsList);

    int updateByPrimaryKeySelective(DictItemObj record);

    int updateByPrimaryKey(DictItemObj record);
}