package com.hy.dtn.vnm.monitor.orm.mysql.dao;

import com.hy.dtn.vnm.monitor.entity.JkgcZbrwObj;
import com.hy.dtn.vnm.monitor.entity.JkgcZbtxObj;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface JkgcZbrwDao {

    int deleteByPrimaryKey(JkgcZbrwObj key);

    int insert(JkgcZbtxObj record);

    int insertSelective(JkgcZbrwObj record);
}