package com.hy.dtn.vnm.user.ctrl;

import com.hy.dtn.vnm.biz.vo.JsonTreeNode;
import com.hy.dtn.vnm.biz.vo.JsonTreeTable;
import com.hy.dtn.vnm.common.BaseController;
import com.hy.dtn.vnm.common.Result;
import com.hy.dtn.vnm.user.bo.BoRole;
import com.hy.dtn.vnm.user.serv.intf.FwsqServIntf;
import com.hy.dtn.vnm.user.vo.VoAddJsGnlb;
import com.hy.dtn.vnm.user.vo.VoRole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yjz
 * @version 1.0
 * @className FwsqCtrl
 * @date 2020/11/12 13:46
 * @description 角色功能管理
 * @note 说明
 */
@Slf4j
@Api(tags = "角色功能管理")
@RestController
@RequestMapping("/api/torole")
public class FwsqCtrl extends BaseController {

    @Resource
    private FwsqServIntf fwsqServ;

    /**
     * @methodName getFuncTree
     * @author yjz
     * @description 获取功能模块树
     * @date 2020/11/12 13:47
     * @returnParam java.lang.String
     * @note 修改说明
     */
    @ApiOperation(value = "获取功能模块树")
    @GetMapping("/getFuncTree")
    @ResponseBody
    public Result<JsonTreeNode> getFuncTree() {
        JsonTreeNode jsontreenode = fwsqServ.getFuncTree();
        return Result.ok(jsontreenode);
    }

    @ApiOperation(value = "获取功能模块树表格数据")
    @GetMapping("/getFuncTreeTable")
    @ResponseBody
    public Result<List<JsonTreeTable>> getFuncTreeTable() {
        return fwsqServ.getFuncTreeTable();
    }

    /**
     * @methodName getRoleList
     * @author yjz
     * @description 获取角色信息列表
     * @date 2020/11/12 13:52
     * @returnParam java.lang.String
     * @note 修改说明
     */
    @ApiOperation(value = "获取角色信息列表")
    @GetMapping("/getRoleList")
    @ResponseBody
    public Result<List<BoRole>> getRoleList() {
        List<BoRole> rolelist = fwsqServ.getRoleList();
        return Result.ok(rolelist);

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
    @ApiOperation(value = "获取角色信息")
    @ApiImplicitParam(name = "jsdm", value = "角色代码", dataType = "String", paramType = "query", required = true)
    @GetMapping("/getRoleInfo")
    @ResponseBody
    public Result<VoRole> getRoleInfo(@RequestParam(value = "jsdm", required = false) String jsdm) {
        BoRole roleDev = fwsqServ.getRoleInfo(jsdm);
        if (roleDev == null) {
            return Result.error();
        }
        //数据转换
        VoRole voRole = new VoRole(roleDev);
        return Result.ok(voRole);
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
    @ApiOperation(value = "获取角色功能列表")
    @ApiImplicitParam(name = "jsdm", value = "角色代码", dataType = "String", paramType = "query", required = true)
    @GetMapping("/getRoleFunc")
    @ResponseBody
    public Result<List<String>> getRoleFunc(@RequestParam(value = "jsdm", required = false) String jsdm) {
        List<String> fwsqList = fwsqServ.getRoleFunc(jsdm);
        return Result.ok(fwsqList);
    }

    /**
     * @param voAddJsGnlb 前台新增角色功能列表
     * @methodName commitRoleFunc
     * @author yjz
     * @description 提交角色的功能授权信息
     * @date 2020/11/12 13:57
     * @returnParam java.lang.String
     * @note 修改说明
     */
    @ApiOperation(value = "提交角色的功能授权信息")
    @ApiImplicitParam(name = "voAddJsGnlb", value = "前台新增角色功能列表", dataType = "VoAddJsGnlb", paramType = "body", required = true)
    @PostMapping("/commitRoleFunc")
    @ResponseBody
    public Result<String> commitRoleFunc(@RequestBody VoAddJsGnlb voAddJsGnlb) {
        boolean flag = fwsqServ.commitRoleFunc(voAddJsGnlb.getJsdm(), voAddJsGnlb.getGnlist());
        return Result.okOrFailed(flag);
    }

    /**
     * @param roledev 角色信息
     * @methodName addRoleInfo
     * @author yjz
     * @description 增加角色信息，返回赋予的角色代码
     * @date 2020/11/12 14:01
     * @returnParam java.lang.String
     * @note 修改说明
     */
    @ApiOperation(value = "增加角色信息")
    @ApiImplicitParam(name = "roledev", value = "角色信息", dataType = "VoRole", paramType = "body", required = true)
    @PostMapping("/addRoleInfo")
    @ResponseBody
    public Result<String> addRoleInfo(@RequestBody VoRole roledev) {
        BoRole boRole = roledev.toBo();
        String roleNum = fwsqServ.addRoleInfo(boRole);
        return Result.ok(roleNum);
    }


    /**
     * @param roledev 角色信息
     * @methodName updateRoleInfo
     * @author yjz
     * @description 修改角色信息
     * @date 2020/11/12 14:02
     * @returnParam java.lang.String
     * @note 修改说明
     */
    @ApiOperation(value = "修改角色信息")
    @ApiImplicitParam(name = "roledev", value = "角色信息", dataType = "VoRole", paramType = "body", required = true)
    @PostMapping("/updateRoleInfo")
    @ResponseBody
    public Result<String> updateRoleInfo(@RequestBody VoRole roledev) {
        BoRole boRole = roledev.toBo();
        boolean flag = fwsqServ.updateRoleInfo(boRole);
        return Result.okOrFailed(flag);
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
    @ApiOperation(value = "删除角色")
    @ApiImplicitParam(name = "jsdm", value = "角色代码", dataType = "String", paramType = "query", required = true)
    @GetMapping("/delRoleInfo")
    @ResponseBody
    public Result<String> delRoleInfo(@RequestParam(value = "jsdm") String jsdm) {
        boolean flag = fwsqServ.delRoleInfo(jsdm);
        return Result.okOrFailed(flag);
    }

    /**
     * @param yhid 用户id
     * @methodName getUserFuncTree
     * @author yjz
     * @description 获取用户的功能模块树
     * @date 2020/11/12 14:08
     * @returnParam com.hy.dtn.ops.common.Result<?>
     * @note 修改说明
     */
    @ApiOperation(value = "获取用户的功能模块树")
    @ApiImplicitParam(name = "yhid", value = "用户id", dataType = "String", paramType = "query", required = true)
    @GetMapping("/getUserFuncTree")
    @ResponseBody
    public Result<JsonTreeNode> getUserFuncTree(@RequestParam(value = "yhid", required = false) String yhid) {
        JsonTreeNode jsonTreeNode = fwsqServ.getUserFuncTree(yhid);
        return Result.ok(jsonTreeNode);
    }

}
