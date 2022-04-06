package com.hy.dtn.vnm.monitor.orm.mysql.dao;

import com.hy.dtn.vnm.monitor.entity.JkgcZbObj;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JkgcGcxxDao {

    int deleteByPrimaryKey(String gcbs);

    int insert(JkgcZbObj record);

    int insertSelective(JkgcZbObj record);

    JkgcZbObj selectByPrimaryKey(String gcbs);

    int updateByPrimaryKeySelective(JkgcZbObj record);

    int updateByPrimaryKey(JkgcZbObj record);

    List<JkgcZbObj> getJkgcList(JkgcZbObj record);
}