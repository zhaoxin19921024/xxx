package com.hy.dtn.vnm.monitor.orm.mysql.dao;


import com.hy.dtn.vnm.monitor.entity.JkrwJkzbObj;
import com.hy.dtn.vnm.monitor.entity.JkrwRwxxObj;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface JkrwJkzbDao {

    int deleteByPrimaryKey(String rwbs);

    int insert(JkrwRwxxObj record);

    int insertSelective(JkrwJkzbObj record);
}