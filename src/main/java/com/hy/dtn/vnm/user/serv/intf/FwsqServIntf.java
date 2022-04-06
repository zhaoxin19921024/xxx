package com.hy.dtn.vnm.user.serv.intf;

import com.hy.dtn.vnm.biz.vo.JsonTreeTable;
import com.hy.dtn.vnm.common.Result;
import com.hy.dtn.vnm.user.bo.BoRole;
import com.hy.dtn.vnm.biz.vo.JsonTreeNode;

import java.util.List;

/**
 * @author yjz
 * @version 1.0
 * @className FwsqServIntf
 * @date 2020/11/12 14:10
 * @description 访问授权服务接口
 * @note 说明
 */
public interface FwsqServIntf {
    /**
     * @param
     * @methodName getFuncTree
     * @author yjz
     * @description 获取功能模块树
     * @date 2020/11/12 13:47
     * @returnParam JsonTreeNode
     * @note 修改说明
     */
    JsonTreeNode getFuncTree();

    /**
     * @param yhid
     * @methodName getUserFuncTree
     * @author yjz
     * @description 获取用户的功能模块树
     * @date 2020/11/12 14:07
     * @returnParam com.hy.dtn.ops.biz.vo.JsonTreeNode
     * @note 修改说明
     */
    JsonTreeNode getUserFuncTree(String yhid);

    /**
     * @param
     * @methodName getRoleList
     * @author yjz
     * @description 获取角色信息列表
     * @date 2020/11/12 13:52
     * @returnParam java.lang.String
     * @note 修改说明
     */
    List<BoRole> getRoleList();

    /**
     * @param jsdm
     * @methodName getRoleFunc
     * @author yjz
     * @description 获取角色功能列表
     * @date 2020/11/12 13:56
     * @returnParam java.lang.String
     * @note 修改说明
     */
    List<String> getRoleFunc(String jsdm);

    /**
     * @param jsdm
     * @methodName getRoleInfo
     * @author yjz
     * @description 获取角色信息
     * @date 2020/11/12 13:53
     * @returnParam java.lang.String
     * @note 修改说明
     */
    BoRole getRoleInfo(String jsdm);

    /**
     * @param obj
     * @methodName addRoleInfo
     * @author yjz
     * @description 增加角色信息，返回赋予的角色代码
     * @date 2020/11/12 14:01
     * @returnParam java.lang.String
     * @note 修改说明
     */
    String addRoleInfo(BoRole obj);

    /**
     * @param obj
     * @methodName updateRoleInfo
     * @author yjz
     * @description 修改角色信息
     * @date 2020/11/12 14:02
     * @returnParam java.lang.String
     * @note 修改说明
     */
    boolean updateRoleInfo(BoRole obj);

    /**
     * @param jsdm
     * @methodName delRoleInfo
     * @author yjz
     * @description 删除角色
     * @date 2020/11/12 14:04
     * @returnParam java.lang.String
     * @note 修改说明
     */
    boolean delRoleInfo(String jsdm);

    /**
     * @param jsdm, mkdmlist
     * @methodName commitRoleFunc
     * @author yjz
     * @description 提交角色的功能授权信息
     * @date 2020/11/12 13:57
     * @returnParam java.lang.String
     * @note 修改说明
     */
    boolean commitRoleFunc(String jsdm, List<String> mkdmlist);

    /**
     * @description 查询功能树表格
     * @methodName getFuncTreeTable
     * @author yjz
     * @date 2021/03/08 16:07
     * @return com.hy.dtn.ops.common.Result<java.util.List<com.hy.dtn.ops.biz.vo.JsonTreeTable>>
     * @note 修改说明
     */
    Result<List<JsonTreeTable>> getFuncTreeTable();
}
