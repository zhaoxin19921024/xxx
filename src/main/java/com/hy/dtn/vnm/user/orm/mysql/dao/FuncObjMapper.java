package com.hy.dtn.vnm.user.orm.mysql.dao;

import com.hy.dtn.vnm.user.orm.mysql.model.FuncObj;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**  
 * @className FuncObjMapper
 * @author yjz
 * @date 2020/11/12 14:14
 * @version 1.0
 * @description 功能代码相关dao
 * @note 说明
 */
@Mapper
public interface FuncObjMapper {
    /**
     * @description 根据模块代码指定删除某条数据
     * @methodName deleteByPrimaryKey
     * @author yjz
     * @date 2021/03/09 10:18
     * @param mkdm 模块代码
     * @return int
     * @note 修改说明
     */
    int deleteByPrimaryKey(@Param("mkdm")String mkdm);

    /**
     * @description 单条数据信息
     * @methodName insert
     * @author yjz
     * @date 2021/03/09 10:19
     * @param record 待新增数据
     * @return int
     * @note 修改说明
     */
    int insert(FuncObj record);

    /**
     * @description 单条数据信息
     * @methodName insert
     * @author yjz
     * @date 2021/03/09 10:19
     * @param record 待新增数据
     * @return int
     * @note 修改说明
     */
    int insertSelective(FuncObj record);

    /**
     * @description 指定查询单条数据
     * @methodName selectByPrimaryKey
     * @author yjz
     * @date 2021/03/09 10:20
     * @param mkdm 模板代码
     * @return com.hy.dtn.uis.user.orm.mysql.model.FuncObj
     * @note 修改说明
     */
    FuncObj selectByPrimaryKey(@Param("mkdm")String mkdm);

    /**
     * @description 更新数据
     * @methodName updateByPrimaryKeySelective
     * @author yjz
     * @date 2021/03/09 10:21
     * @param record 待更新数据
     * @return int
     * @note 修改说明
     */
    int updateByPrimaryKeySelective(FuncObj record);

    /**
     * @description 更新数据
     * @methodName updateByPrimaryKey
     * @author yjz
     * @date 2021/03/09 10:21
     * @param record 待更新数据
     * @return int
     * @note 修改说明
     */
    int updateByPrimaryKey(FuncObj record);

    /**
     * @description 获取所有数据
     * @methodName getAllFunc
     * @author yjz
     * @date 2021/03/09 10:22
     * @return java.util.List<com.hy.dtn.uis.user.orm.mysql.model.FuncObj>
     * @note 修改说明
     */
    List<FuncObj> getAllFunc();

    /**
     * @description 根据用户id查询所有数据
     * @methodName getUserFunc
     * @author yjz
     * @date 2021/03/09 10:22
     * @param yhid 用户id
     * @return java.util.List<com.hy.dtn.uis.user.orm.mysql.model.FuncObj>
     * @note 修改说明
     */
    List<FuncObj> getUserFunc(@Param("yhid")String yhid);

    /**
     * @description 根据角色代码，查询相关信息
     * @methodName getRoleFunc
     * @author yjz
     * @date 2021/03/09 10:22
     * @param jsdm 角色代码
     * @return java.util.List<com.hy.dtn.uis.user.orm.mysql.model.FuncObj>
     * @note 修改说明
     */
    List<FuncObj> getRoleFunc(@Param("jsdm")String jsdm);
}