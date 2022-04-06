package com.hy.dtn.vnm.user.orm.mysql.dao;

import com.hy.dtn.vnm.user.orm.mysql.model.UserObj;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserObjMapper {
    /**
     * @param yhid
     * @methodName deleteByPrimaryKey
     * @author yjz
     * @description 根据id指定删除
     * @date 2020/11/23 11:06
     * @returnParam int
     * @note 修改说明
     */
    int deleteByPrimaryKey(@Param("yhid") String yhid);

    int deleteByPrimaryKeyList(@Param("list") List<String> ids);

    /**
     * @param record
     * @methodName insert
     * @author yjz
     * @description 新增用户
     * @date 2020/11/23 11:06
     * @returnParam int
     * @note 修改说明
     */
    int insert(UserObj record);

    /**
     * @param record
     * @methodName insert
     * @author yjz
     * @description 新增用户
     * @date 2020/11/23 11:06
     * @returnParam int
     * @note 修改说明
     */
    int insertSelective(UserObj record);

    /**
     * @param yhid
     * @methodName insert
     * @author yjz
     * @description 用户查询
     * @date 2020/11/23 11:06
     * @returnParam int
     * @note 修改说明
     */
    UserObj selectByPrimaryKey(@Param("yhid") String yhid);

    /**
     * @param yhid, dlm
     * @methodName selectByPrimaryKeyAndDlm
     * @author yjz
     * @description 根据用户id或者登录名查询数据
     * @date 2020/11/19 18:00
     * @returnParam com.hy.dtn.ops.user.orm.mysql.model.UserObj
     * @note 修改说明
     */
    List<UserObj> selectByPrimaryKeyAndDlm(@Param("yhid") String yhid, @Param("dlm") String dlm);

    UserObj selectByDlm(@Param("dlm") String dlm);

    int updateByPrimaryKeySelective(UserObj record);

    /**
     * @param record 用户信息
     * @return int
     * @description 更新的用户信息
     * @methodName updateByPrimaryKey
     * @author yjz
     * @date 2021/03/08 14:35
     * @note 修改说明
     */
    int updateByPrimaryKey(UserObj record);

    /**
     * @param newpsd 新密码
     * @param yhid   用户id
     * @return int
     * @description 修改密码
     * @methodName changePsd
     * @author yjz
     * @date 2021/03/08 14:34
     * @note 修改说明
     */
    int changePsd(@Param("newpsd") String newpsd, @Param("yhid") String yhid);

    /**
     * @return java.util.List<com.hy.dtn.ops.user.orm.mysql.model.UserObj>
     * @description 原始获取所有用户信息
     * @methodName getAllUser
     * @author yjz
     * @date 2021/03/08 14:34
     * @note 修改说明
     */
    List<UserObj> getAllUser();

    /**
     * @param map 查询条件
     * @return java.util.List<com.hy.dtn.ops.user.orm.mysql.model.UserObj>
     * @description 分页条件查询
     * @methodName findXtXtyhByCondition
     * @author yjz
     * @date 2021/03/08 14:31
     * @note 修改说明
     */
    List<UserObj> findXtXtyhByCondition(Map<String, Object> map);
}