package com.hy.dtn.vnm.user.orm.mysql.dao;

import com.hy.dtn.vnm.user.orm.mysql.model.UserRoleObjKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * @author yjz
 * @version 1.0
 * @className UserRoleObjMapper
 * @date 2020/11/12 13:41
 * @description 用户角色操作mapper
 * @note 说明
 */
@Mapper
public interface UserRoleObjMapper {

    int deleteByPrimaryKey(UserRoleObjKey key);

    int insert(@Param("yhid") String yhid, @Param("jsdm") String jsdm);

    int insertByList(@Param("list") List<HashMap<String, Object>> list);

    int insertSelective(UserRoleObjKey record);

    List<String> getUserRoleList(@Param("yhid") String yhid);

    int delUserRole(@Param("yhid") String yhid);
}