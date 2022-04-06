package com.hy.dtn.vnm.user.serv.impl;

import com.hy.dtn.vnm.biz.vo.JsonTreeNode;
import com.hy.dtn.vnm.biz.vo.JsonTreeTable;
import com.hy.dtn.vnm.common.Result;
import com.hy.dtn.vnm.user.bo.BoRole;
import com.hy.dtn.vnm.user.orm.mysql.dao.FuncObjMapper;
import com.hy.dtn.vnm.user.orm.mysql.dao.RoleFuncObjMapper;
import com.hy.dtn.vnm.user.orm.mysql.dao.RoleObjMapper;
import com.hy.dtn.vnm.user.orm.mysql.model.FuncObj;
import com.hy.dtn.vnm.user.orm.mysql.model.RoleObj;
import com.hy.dtn.vnm.user.serv.intf.FwsqServIntf;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author yjz
 * @version 1.0
 * @className FwsqServImpl
 * @date 2020/11/12 14:30
 * @description 描述
 * @note 说明
 */
@Service
public class FwsqServImpl implements FwsqServIntf {

    private static final int FUNC_CJM_STEP = 3;

    @Resource
    private RoleObjMapper roleObjMapper;

    @Resource
    private RoleFuncObjMapper roleFuncObjMapper;

    @Resource
    private FuncObjMapper funcObjMapper;

    /**
     * @param pnode 父节点
     * @param queue 查询列表
     * @return void
     * @description 方法描述
     * @methodName dealChildren
     * @author yjz
     * @date 2021/03/08 16:09
     * @note 修改说明
     */
    public static void dealChildren(JsonTreeNode pnode, Queue<FuncObj> queue) {
        String pcjm = pnode.getCjm();
        FuncObj cnode = queue.peek();
        while (null != cnode) {
            String ncjm = cnode.getMkdm();
            int ncjmlen = ncjm.length();
            String tPcjm = ncjm.substring(0, ncjmlen - FUNC_CJM_STEP);
            //若节点层级码不是父节点的子节点或者上一级与父节点不同，则退出,
            if (!pcjm.equals(tPcjm)) {
                return;
            }
            //若节点层级码上一级与父节点相同,则进行处理
            JsonTreeNode node = new JsonTreeNode();
            node.setKey(cnode.getMkdm());
            node.setTitle(cnode.getMkmc());
            node.setCjm(cnode.getMkdm());
            //将当前节点从队首删除
            queue.poll();
            //处理当前节点的子节点
            dealChildren(node, queue);
            pnode.getChildren().add(node);
            //对下一节点进行处理
            cnode = queue.peek();
        }
    }

    /**
     * @param funcList 功能列表
     * @return com.hy.dtn.ops.biz.vo.JsonTreeNode
     * @description 创建树
     * @methodName makeFuncTree
     * @author yjz
     * @date 2021/03/08 16:09
     * @note 修改说明
     */
    private JsonTreeNode makeFuncTree(List<FuncObj> funcList) {
        Queue<FuncObj> queue = new LinkedList<>(funcList);
        JsonTreeNode root = new JsonTreeNode();
        root.setKey("root");
        root.setCjm("");
        root.setTitle("系统应用功能");
        dealChildren(root, queue);
        return root;
    }

    /**
     * @methodName getFuncTree
     * @author yjz
     * @description 获取功能模块树
     * @date 2020/11/12 13:47
     * @returnParam java.lang.String
     * @note 修改说明
     */
    @Override
    public JsonTreeNode getFuncTree() {
        //获取到的功能模块列表是有序的/
        List<FuncObj> gnmklist = this.funcObjMapper.getAllFunc();
        //构造树结构
        return this.makeFuncTree(gnmklist);
    }

    /**
     * @param yhid 用户id
     * @methodName getUserFuncTree
     * @author yjz
     * @description 获取用户的功能模块树
     * @date 2020/11/12 14:07
     * @returnParam com.hy.dtn.ops.biz.vo.JsonTreeNode
     * @note 修改说明
     */
    @Override
    public JsonTreeNode getUserFuncTree(String yhid) {
        if (this.funcObjMapper == null) {
            return null;
        }
        //获取到的功能模块列表是有序的
        List<FuncObj> gnmklist = this.funcObjMapper.getUserFunc(yhid);
        //构造树结构
        return this.makeFuncTree(gnmklist);
    }

    /**
     * @methodName getRoleList
     * @author yjz
     * @description 获取角色信息列表
     * @date 2020/11/12 13:52
     * @returnParam java.lang.String
     * @note 修改说明
     */
    @Override
    public List<BoRole> getRoleList() {
        List<RoleObj> jslist = this.roleObjMapper.getAllRoleList();
        List<BoRole> reslist = new ArrayList<>();
        for (RoleObj obj : jslist) {
            reslist.add(new BoRole(obj));
        }
        return reslist;
    }

    /**
     * @param jsdm 角色代码
     * @methodName getRoleInfo
     * @author yjz
     * @description 获取角色信息
     * @date 2020/11/12 13:53
     * @returnParam java.lang.String
     * @note 修改说明
     */
    @Override
    public BoRole getRoleInfo(String jsdm) {
        RoleObj role = this.roleObjMapper.selectByPrimaryKey(jsdm);
        if (null == role) {
            return null;
        }
        BoRole brole = new BoRole(role);
        //获取授权功能列表
        List<String> gnlist = this.roleFuncObjMapper.getFoleFuncList(role.getJsdm());
        if (gnlist != null && gnlist.size() != 0) {
            brole.setGnlist(gnlist);
        }
        return brole;
    }


    /**
     * @param obj 新增角色信息
     * @methodName addRoleInfo
     * @author yjz
     * @description 事务, 返回赋予的角色代码
     * @date 2020/11/12 14:34
     * @returnParam java.lang.String
     * @note 修改说明
     */
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class, Exception.class})
    public String addRoleInfo(BoRole obj) {
        RoleObj role = obj.toDto();
        //若显示顺序不存在，则重新计算
        if (role.getXssx() == null) {
            int xssx = 1;
            Integer oldxssx = this.roleObjMapper.getMaxXssx();
            if (null != oldxssx) {
                xssx = oldxssx + 1;
            }
            role.setXssx(xssx);
        }
        this.roleObjMapper.insert(role);
        //处理授权功能列表
        this.roleFuncObjMapper.deleteByJsdm(role.getJsdm());
        //新增
        if (obj.getGnlist() != null) {
            for (String mkdm : obj.getGnlist()) {
                this.roleFuncObjMapper.insert(role.getJsdm(), mkdm);
            }
        }
        return role.getJsdm();
    }

    /**
     * @param obj 更新角色信息
     * @methodName updateRoleInfo
     * @author yjz
     * @description 事务
     * @date 2020/11/12 14:34
     * @returnParam boolean
     * @note 修改说明
     */
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class, Exception.class})
    public boolean updateRoleInfo(BoRole obj) {
        RoleObj role = obj.toDto();
        this.roleObjMapper.updateByPrimaryKey(role);
        //处理授权功能列表
        String jsdm = obj.getJsdm();
        this.roleFuncObjMapper.deleteByJsdm(jsdm);
        //新增
        if (obj.getGnlist() != null) {
            for (String mkdm : obj.getGnlist()) {
                this.roleFuncObjMapper.insert(jsdm, mkdm);
            }
        }
        return true;
    }

    /**
     * @param jsdm 角色代码
     * @methodName delRoleInfo
     * @author yjz
     * @description 删除角色
     * @date 2020/11/12 14:04
     * @returnParam java.lang.String
     * @note 修改说明
     */
    @Override
    public boolean delRoleInfo(String jsdm) {
        return 1 == this.roleObjMapper.deleteByPrimaryKey(jsdm);
    }

    /**
     * @param jsdm 角色代码
     * @methodName getRoleFunc
     * @author yjz
     * @description 获取角色功能列表
     * @date 2020/11/12 13:56
     * @returnParam java.lang.String
     * @note 修改说明
     */
    @Override
    public List<String> getRoleFunc(String jsdm) {
        List<FuncObj> flist = this.funcObjMapper.getRoleFunc(jsdm);
        if (null == flist) {
            return null;
        }
        List<String> reslist = new ArrayList<>();
        for (FuncObj func : flist) {
            reslist.add(func.getMkdm());
        }
        return reslist;
    }

    /**
     * @param jsdm     角色代码
     * @param mkdmlist 授权代码
     * @methodName commitRoleFunc
     * @author yjz
     * @description 提交角色的功能授权信息
     * @date 2020/11/12 13:57
     * @returnParam java.lang.String
     * @note 修改说明
     */
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class, Exception.class})
    public boolean commitRoleFunc(String jsdm, List<String> mkdmlist) {
        if (StringUtils.isEmpty(jsdm) || null == mkdmlist) {
            return false;
        }
        //先删除原有数据，再新增
        roleFuncObjMapper.deleteByJsdm(jsdm);
        for (String mkdm : mkdmlist) {
            this.roleFuncObjMapper.insert(jsdm, mkdm);
        }
        return true;
    }

    /**
     * @return com.hy.dtn.ops.common.Result<java.util.List < com.hy.dtn.ops.biz.vo.JsonTreeTable>>
     * @description 查询功能树表格
     * @methodName getFuncTreeTable
     * @author yjz
     * @date 2021/03/08 16:07
     * @note 修改说明
     */
    @Override
    public Result<List<JsonTreeTable>> getFuncTreeTable() {
        //获取到的功能模块列表是有序的/
        List<FuncObj> gnmklist = this.funcObjMapper.getAllFunc();
        //返回数据
        List<JsonTreeTable> result = new ArrayList<>();
        for (FuncObj funcObj : gnmklist) {
            JsonTreeTable jsonTreeTable = new JsonTreeTable();
            jsonTreeTable.setKey(funcObj.getMkdm());
            jsonTreeTable.setText(funcObj.getMkmc());
            //大于三位，则有父级
            if (StringUtils.isNotEmpty(funcObj.getMkdm()) && funcObj.getMkdm().length() > FUNC_CJM_STEP) {
                jsonTreeTable.setParentKey(funcObj.getMkdm().substring(0, funcObj.getMkdm().length() - FUNC_CJM_STEP));
            }
            result.add(jsonTreeTable);
        }
        return Result.ok(result);
    }
}
