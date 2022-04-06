package com.hy.dtn.vnm.monitor.orm.mysql.dao;


import com.hy.dtn.vnm.monitor.entity.JkgcZbtxObj;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JkgcZbtxDao {

    int deleteByPrimaryKey(String txbs);

    int insert(JkgcZbtxObj record);

    int insertSelective(JkgcZbtxObj record);

    JkgcZbtxObj selectByPrimaryKey(String zbbs);

    int updateByPrimaryKeySelective(JkgcZbtxObj record);

    int updateByPrimaryKey(JkgcZbtxObj record);

    List<JkgcZbtxObj> selectByGcbs(String gcbs);
}