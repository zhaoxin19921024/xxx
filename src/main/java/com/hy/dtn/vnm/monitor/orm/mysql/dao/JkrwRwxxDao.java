package com.hy.dtn.vnm.monitor.orm.mysql.dao;


import com.hy.dtn.vnm.biz.bo.JkrwRwxxBo;
import com.hy.dtn.vnm.biz.vo.JkgcZbServerVo;
import com.hy.dtn.vnm.monitor.entity.JkrwRwxxObj;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JkrwRwxxDao {

    int deleteByPrimaryKey(String rwbs);

    int insert(JkrwRwxxObj record);

    int insertSelective(JkrwRwxxObj record);

    JkrwRwxxObj selectByPrimaryKey(String rwbs);

    int updateByPrimaryKeySelective(JkrwRwxxObj record);

    int updateByPrimaryKey(JkrwRwxxObj record);

    List<JkrwRwxxBo> getJkrwList(JkrwRwxxObj obj);

    int updateRwztByRwbs(JkgcZbServerVo vo);
}