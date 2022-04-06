package com.hy.dtn.vnm.user.orm.mysql.dao;


import com.hy.dtn.vnm.user.orm.mysql.model.RoleObj;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**  
 * @className RoleObjMapper
 * @author yjz
 * @date 2020/11/12 14:23
 * @version 1.0
 * @description 描述
 * @note 说明
 */
@Mapper
public interface RoleObjMapper {
    int deleteByPrimaryKey(String jsdm);

    int insert(RoleObj record);

    int insertSelective(RoleObj record);

    RoleObj selectByPrimaryKey(String jsdm);

    int updateByPrimaryKeySelective(RoleObj record);

    int updateByPrimaryKey(RoleObj record);

    List<RoleObj> getAllRoleList();

    Integer getMaxXssx();
}