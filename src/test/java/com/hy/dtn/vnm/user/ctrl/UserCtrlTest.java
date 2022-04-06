package com.hy.dtn.vnm.user.ctrl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONObject;
import com.hy.dtn.vnm.VnmApplication;
import com.hy.dtn.vnm.common.Result;
import com.hy.dtn.vnm.user.bo.BoLogUserObj;
import com.hy.dtn.vnm.common.CommConst;
import com.hy.dtn.vnm.user.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yjz
 * @version 1.0
 * @className UserCtrlTest
 * @date 2020/11/18 14:16
 * @description 用户功能管理测试功能
 * @note 说明
 */
@Slf4j
@SpringBootTest(classes = VnmApplication.class)
public class UserCtrlTest extends AbstractTestNGSpringContextTests {

    /**
     * Controller层注入
     */
    @Autowired
    private UserCtrl controller;

    /**
     * 初始化赋值Session
     */
    private final MockHttpSession mockHttpSession = new MockHttpSession();

    /**
     * 注册的用户信息
     */
    private final VoAllUserObj voAllUserObj = new VoAllUserObj();

    /**
     * @param ctx
     * @methodName setUp
     * @author yjz
     * @description 方法执行前操作
     * @date 2020/11/18 17:07
     * @returnParam void
     * @note 修改说明
     */
    @BeforeMethod
    public void setUp(ITestContext ctx) {
    }

    @AfterMethod
    public void tearDown() {
    }


    /**
     * @methodName testAddUserInfo
     * @author yjz
     * @description 新增用户信息
     * @date 2020/11/19 10:31
     * @returnParam void
     * @note 修改说明
     */
    @Test
    public void testAddUserInfo() {
        voAllUserObj.setCsny(Date.valueOf("1994-01-02"));
        String uuid = IdUtil.simpleUUID();
        voAllUserObj.setDlm("单元测试账号" + uuid);
        voAllUserObj.setDlmm("111111");
        voAllUserObj.setLxdh("13888451236");
        voAllUserObj.setSfz("321112333521421250");
        voAllUserObj.setXb("1");
        voAllUserObj.setYhid(uuid);
        voAllUserObj.setYhxm("单元测试");
        voAllUserObj.setZw("南京华鹞");
        //功能列表
        List<String> list = new ArrayList<>();
        list.add("001");
        list.add("001001");
        list.add("001002");
        list.add("001003");
        list.add("001004");
        voAllUserObj.setFuncs(list);

        Result<?> result = controller.addUserInfo(voAllUserObj);
        log.info("============返回数据=============:{}", result);
        Assert.assertEquals(HttpStatus.OK.value(), (int) result.getCode());
    }

    /**
     * @methodName testLogin
     * @author yjz
     * @description 用户登录
     * @date 2020/11/18 14:45
     * @returnParam void
     * @note 修改说明
     */
    @Test(priority = 1)
    public void testLogin(ITestContext ctx) {
        VoLogin voLogin = new VoLogin();
        voLogin.setDlm(voAllUserObj.getDlm());
        voLogin.setDlmm(voAllUserObj.getDlmm());
        Result<?> result = controller.login(voLogin, mockHttpSession);
        log.info("============返回数据=============:{}", result);
        Assert.assertEquals(HttpStatus.OK.value(), (int) result.getCode());
        if (result.getCode() == HttpStatus.OK.value()) {
            BoLogUserObj logUser = ((JSONObject) result.getData()).toBean(BoLogUserObj.class);
            //设置session
            mockHttpSession.setAttribute(CommConst.NAME_LOGUSEROBJ, logUser);
            ctx.setAttribute("mockHttpSession", mockHttpSession);
        }
    }

    /**
     * @methodName testGetlogininfo
     * @author yjz
     * @description 登录用户查询
     * @date 2020/11/18 17:07
     * @returnParam void
     * @note 修改说明
     */
    @Test(priority = 2)
    public void testGetlogininfo() {
        Result<?> result = controller.getlogininfo(mockHttpSession);
        log.info("============返回数据=============:{}", result);
        Assert.assertEquals(HttpStatus.OK.value(), (int) result.getCode());
    }

    /**
     * @methodName testGetAllUse
     * @author yjz
     * @description 获取用户列表
     * @date 2020/11/19 10:23
     * @returnParam void
     * @note 修改说明
     */
    @Test(priority = 2)
    public void testGetAllUse() {
        VoUserSearch voUserSearch = new VoUserSearch();
        Result<?> result = controller.getAllUse(voUserSearch);
        log.info("============返回数据=============:{}", result);
        Assert.assertEquals(HttpStatus.OK.value(), (int) result.getCode());
    }

    /**
     * @methodName testGetUserInfo
     * @author yjz
     * @description 获取用户信息
     * @date 2020/11/19 10:31
     * @returnParam void
     * @note 修改说明
     */
    @Test(priority = 2)
    public void testGetUserInfo() {
        String yhid = voAllUserObj.getYhid();
        Result<?> result = controller.getUserInfo(yhid);
        log.info("============返回数据=============:{}", result);
        Assert.assertEquals(HttpStatus.OK.value(), (int) result.getCode());
    }

    /**
     * @methodName testGetUserRoleList
     * @author yjz
     * @description 获取用户的角色列表
     * @date 2020/11/19 15:57
     * @returnParam void
     * @note 修改说明
     */
    @Test(priority = 2)
    public void testGetUserRoleList() {
        String yhid = voAllUserObj.getYhid();
        Result<?> result = controller.getUserRoleList(yhid);
        log.info("============返回数据=============:{}", result);
        Assert.assertEquals(HttpStatus.OK.value(), (int) result.getCode());
    }


    /**
     * @methodName testUpdateUserInfo
     * @author yjz
     * @description 修改用户信息
     * @date 2020/11/19 15:58
     * @returnParam void
     * @note 修改说明
     */
    @Test(priority = 2)
    public void testUpdateUserInfo() {
        voAllUserObj.setYhxm("单元测试修改");
        voAllUserObj.setCsny(Date.valueOf("2020-11-19"));
        voAllUserObj.setZw("开发测试");
        voAllUserObj.setSfz("111111111111111111");
        voAllUserObj.setXb("0");
        voAllUserObj.setLxdh("11111111111");
        Result<?> result = controller.updateUserInfo(voAllUserObj);
        log.info("============返回数据=============:{}", result);
        Assert.assertEquals(HttpStatus.OK.value(), (int) result.getCode());
    }

    /**
     * @methodName testCommitUserRoles
     * @author yjz
     * @description 方法描述
     * @date 2020/11/19 16:04
     * @returnParam void
     * @note 修改说明
     */
    @Test(priority = 2)
    public void testCommitUserRoles() {
        VoAddYhJsdm voAddYhJsdm = new VoAddYhJsdm();
        voAddYhJsdm.setYhid(voAllUserObj.getYhid());
        voAddYhJsdm.setJsdmlist(new ArrayList<>());
        Result<?> result = controller.commitUserRoles(voAddYhJsdm);
        log.info("============返回数据=============:{}", result);
        Assert.assertEquals(HttpStatus.OK.value(), (int) result.getCode());
    }

    /**
     * @methodName testChangePassword
     * @author yjz
     * @description 修改密码
     * @date 2020/11/19 16:06
     * @returnParam void
     * @note 修改说明
     */
    @Test(priority = 2)
    public void testChangePassword(ITestContext ctx) {
        VoChangePsd voChangePsd = new VoChangePsd();
        voChangePsd.setYhid(voAllUserObj.getYhid());
        voChangePsd.setDlmm("222222");
        Result<?> result = controller.changePassword(voChangePsd, mockHttpSession);
        log.info("============返回数据=============:{}", result);
        Assert.assertEquals(HttpStatus.OK.value(), (int) result.getCode());
        if (result.getCode() == HttpStatus.OK.value()) {
            voAllUserObj.setDlmm(voChangePsd.getDlmm());
            //测试重新登录
            this.testLogin(ctx);
        }
    }

    /**
     * @methodName testDelUserInfo
     * @author yjz
     * @description 删除用户
     * @date 2020/11/19 16:04
     * @returnParam void
     * @note 修改说明
     */
    @Test(priority = 3)
    public void testDelUserInfo() {
        List<String> ids = new ArrayList<>();
        ids.add(voAllUserObj.getYhid());
        Result<?> result = controller.delUserInfoByIds(ids, mockHttpSession);
        log.info("============返回数据=============:{}", result);
        Assert.assertEquals(HttpStatus.OK.value(), (int) result.getCode());
    }
}