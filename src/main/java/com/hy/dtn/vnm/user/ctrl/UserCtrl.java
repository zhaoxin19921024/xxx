package com.hy.dtn.vnm.user.ctrl;

import cn.hutool.json.JSONObject;
import com.hy.dtn.vnm.biz.vo.VoPageInfo;
import com.hy.dtn.vnm.common.BaseController;
import com.hy.dtn.vnm.common.CommConst;
import com.hy.dtn.vnm.common.Result;
import com.hy.dtn.vnm.user.bo.BoAllUserObj;
import com.hy.dtn.vnm.user.bo.BoBaseUserObj;
import com.hy.dtn.vnm.user.serv.intf.UserServIntf;
import com.hy.dtn.vnm.user.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yjz
 * @version 1.0
 * @className UserCtrl
 * @date 2020/11/11 17:53
 * @description 用户管理
 * @note 说明
 */
@Api(tags = "用户功能管理")
@RestController
@RequestMapping(value = {"/api/tologin"})
@Slf4j
public class UserCtrl extends BaseController {

    /**
     * 注入用户service接口
     */
    @Resource
    private UserServIntf userServ;


    @ApiOperation(value = "用户登录")
    @ApiImplicitParam(name = "voLogin", value = "登录信息", dataType = "VoLogin", paramType = "body", required = true)
    @PostMapping("/login")
    public Result<VoBaseUserObj> login(@RequestBody VoLogin voLogin, HttpSession session) {
        BoAllUserObj boAllUserObj = userServ.login(voLogin.getDlm(), voLogin.getDlmm(), session);
        if (boAllUserObj != null) {
            //数据转换
            VoBaseUserObj voBaseUserObj = new VoAllUserObj(boAllUserObj);
            return Result.ok("user.result.login.success", voBaseUserObj);
        } else {
            return Result.error("user.result.login.error");
        }
    }

    @ApiOperation(value = "用户退出登录")
    @GetMapping("/logout")
    public Result<VoBaseUserObj> logout(HttpSession session) {
        //删除session信息
        session.removeAttribute(CommConst.NAME_LOGUSEROBJ);
        return Result.ok();
    }

    @ApiOperation(value = "登录用户查询")
    @GetMapping("/getlogininfo")
    public Result<VoAllBaseUserObj> getlogininfo(HttpSession session) {
        BoAllUserObj logUser = userServ.getLogUser(session);
        if (logUser == null) {
            return Result.error("user.result.notLogin");
        }
        //bo转vo
        VoAllBaseUserObj voBaseUserObj = new VoAllBaseUserObj(logUser);
        //获取到的功能模块列表是有序的
        List<String> routeList = userServ.getUserRoute(voBaseUserObj.getYhid());
        for (String s : routeList) {
            voBaseUserObj.getMenuList().add(new JSONObject(s));
        }
        return Result.ok(voBaseUserObj);
    }

    @ApiOperation(value = "获取用户列表")
    @ApiImplicitParam(name = "voUserSearch", value = "分页查询条件", dataType = "VoUserSearch", paramType = "body", required = true)
    @PostMapping("/getAllUse")
    public Result<VoPageInfo<VoBaseUserObj>> getAllUse(@RequestBody VoUserSearch voUserSearch) {
        return userServ.getAllUse(voUserSearch);
    }

    @ApiOperation(value = "获取用户信息")
    @ApiImplicitParam(name = "yhid", value = "用户id", dataType = "String", paramType = "query", required = true)
    @GetMapping("/getUserInfo")
    public Result<VoBaseUserObj> getUserInfo(@RequestParam(value = "yhid") String yhid) {
        BoBaseUserObj userDev = userServ.getUserInfo(yhid);
        //数据转换
        VoBaseUserObj voBaseUserObj = new VoBaseUserObj(userDev);
        return Result.ok(voBaseUserObj);
    }

    @ApiOperation(value = "获取用户的角色列表以及功能代码列表")
    @ApiImplicitParam(name = "yhid", value = "用户id", dataType = "String", paramType = "query", required = true)
    @GetMapping("/getUserRoleList")
    public Result<VoRoleAndFuncList> getUserRoleList(@RequestParam(value = "yhid", required = false) String yhid) {
        return userServ.getUserRoleList(yhid);
    }

    @ApiOperation(value = "新增用户信息")
    @ApiImplicitParam(name = "userdev", value = "用户信息", dataType = "VoAllUserObj", paramType = "body", required = true)
    @PostMapping("/addUserInfo")
    public Result<String> addUserInfo(@RequestBody VoAllUserObj userdev) {
        //数据转换，将Vo转为BO
        BoAllUserObj boAllUserObj = userdev.toBo();
        return userServ.addUserInfo(boAllUserObj);
    }

    @ApiOperation(value = "修改用户信息")
    @ApiImplicitParam(name = "userdev", value = "用户信息", dataType = "VoBaseUserObj", paramType = "body", required = true)
    @PostMapping("/updateUserInfo")
    public Result<String> updateUserInfo(@RequestBody VoBaseUserObj userdev) {
        //数据转换，将Vo转为BO
        BoAllUserObj boAllUserObj = userdev.toBo();
        boolean flag = userServ.updateUserInfo(boAllUserObj);
        return Result.okOrFailed(flag);
    }

    @ApiOperation(value = "单个删除用户")
    @ApiImplicitParam(name = "id", value = "待删除的用户id", paramType = "query", dataType = "String", required = true)
    @GetMapping("/delUserInfoById")
    public Result<String> delUserInfoById(@RequestParam("id") String id, HttpSession session) {
        //获取session中的用户信息
        BoAllUserObj logUser = (BoAllUserObj) session.getAttribute(CommConst.NAME_LOGUSEROBJ);
        //若请求参数为空，则直接返回错误信息
        if (!StringUtils.isEmpty(id)) {
            //session中不存在用户信息，则返回错误信息
            if (logUser != null && !id.equals(logUser.getYhid())) {
                List<String> ids = new ArrayList<>();
                ids.add(id);
                boolean flag = userServ.delUserInfo(ids);
                return Result.okOrFailed(flag);
            } else if (logUser == null) {
                return Result.error("user.result.notLogin");
            } else {
                //删除的用户中，不能包含自身数据
                return Result.error("user.result.delUserInfo.delSelf");
            }
        }
        return Result.error("request.params.null");
    }

    @ApiOperation(value = "批量删除用户")
    @ApiImplicitParam(name = "ids", value = "待删除的用户id列表数据", paramType = "query", dataType = "String", allowMultiple = true, required = true)
    @PostMapping("/delUserInfoByIds")
    public Result<String> delUserInfoByIds(@RequestParam("ids") List<String> ids, HttpSession session) {
        //获取session中的用户信息
        BoAllUserObj logUser = (BoAllUserObj) session.getAttribute(CommConst.NAME_LOGUSEROBJ);
        //若请求参数为空，则直接返回错误信息
        if (ids != null && !ids.isEmpty()) {
            //session中不存在用户信息，则返回错误信息
            if (logUser != null && !ids.contains(logUser.getYhid())) {
                boolean flag = userServ.delUserInfo(ids);
                return Result.okOrFailed(flag);
            } else if (logUser == null) {
                return Result.error("user.result.notLogin");
            } else {
                //删除的用户中，不能包含自身数据
                return Result.error("user.result.delUserInfo.delSelf");
            }
        }
        return Result.error("request.params.null");
    }

    @ApiOperation(value = "提交用户授权角色信息")
    @ApiImplicitParam(name = "voAddYhJsdm", value = "前端用户提交角色列表信息", dataType = "VoAddYhJsdm", paramType = "body", required = true)
    @PostMapping("/commitUserRoles")
    public Result<String> commitUserRoles(@RequestBody VoAddYhJsdm voAddYhJsdm) {
        //TODO:参数校验，后续统一处理
        boolean flag = userServ.commitUserRoles(voAddYhJsdm.getYhid(), voAddYhJsdm.getJsdmlist());
        return Result.okOrFailed(flag);
    }

    @ApiOperation(value = "修改密码")
    @ApiImplicitParam(name = "voChangePsd", value = "修改信息", dataType = "VoChangePsd", paramType = "body", required = true)
    @PostMapping("/changePassword")
    public Result<String> changePassword(@RequestBody VoChangePsd voChangePsd, HttpSession session) {
        //判断当前Session中，是否为当前登录用户
        BoAllUserObj logUser = (BoAllUserObj) session.getAttribute(CommConst.NAME_LOGUSEROBJ);
        if (logUser != null && logUser.getYhid().equals(voChangePsd.getYhid())) {
            boolean flag = userServ.changePassword(voChangePsd.getYhid(), voChangePsd.getDlmm());
            return Result.okOrFailed(flag);
        } else {
            return Result.error("user.result.changePassword.notLogin");
        }
    }

}
