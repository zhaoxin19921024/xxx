package com.hy.dtn.vnm.user.orm.mysql.dao;

import com.hy.dtn.vnm.user.orm.mysql.model.RoleFuncObjKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yjz
 * @version 1.0
 * @className RoleFuncObjMapper
 * @date 2020/11/12 14:21
 * @description 描述
 * @note 说明
 */
@Mapper
public interface RoleFuncObjMapper {
    int deleteByPrimaryKey(RoleFuncObjKey key);

    int insert(@Param("jsdm") String jsdm, @Param("mkdm") String mkdm);

    int insertSelective(RoleFuncObjKey record);

    List<String> getFoleFuncList(@Param("jsdm") String jsdm);

    int deleteByJsdm(@Param("jsdm") String jsdm);
}