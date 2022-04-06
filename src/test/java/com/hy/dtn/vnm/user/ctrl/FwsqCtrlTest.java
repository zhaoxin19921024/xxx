package com.hy.dtn.vnm.user.ctrl;

import cn.hutool.core.util.IdUtil;
import com.hy.dtn.vnm.VnmApplication;
import com.hy.dtn.vnm.common.Result;
import com.hy.dtn.vnm.user.vo.VoAddJsGnlb;
import com.hy.dtn.vnm.user.vo.VoRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yjz
 * @version 1.0
 * @className FwsqCtrlTest
 * @date 2020/11/19 16:26
 * @description 角色功能管理测试类
 * @note 说明
 */
@Slf4j
@SpringBootTest(classes = VnmApplication.class)
public class FwsqCtrlTest extends AbstractTestNGSpringContextTests {

    /**
     * 控制层注入
     */
    @Autowired
    private FwsqCtrl controller;

    /**
     * 定义角色对象信息
     */
    private final VoRole roledev = new VoRole();

    /**
     * @methodName testAddRoleInfo
     * @author yjz
     * @description 增加角色信息，返回赋予的角色代码
     * @date 2020/11/19 16:29
     * @returnParam void
     * @note 修改说明
     */
    @Test
    public void testAddRoleInfo() {
        String uuid = IdUtil.simpleUUID();
        roledev.setJsdm(uuid);
        roledev.setJsmc("单元测试数据" + uuid);
        roledev.setJssm("单元测试角色说明");
        roledev.setXssx(1);
        List<String> list = new ArrayList<>();
        list.add("001");
        list.add("001001");
        list.add("001002");
        list.add("001003");
        list.add("001004");
        roledev.setGnlist(list);

        Result<?> result = controller.addRoleInfo(roledev);
        log.info("============返回数据=============:{}", result);
        Assert.assertEquals(HttpStatus.OK.value(), (int) result.getCode());
    }

    /**
     * @methodName testGetFuncTree
     * @author yjz
     * @description 获取功能模块树
     * @date 2020/11/19 16:27
     * @returnParam void
     * @note 修改说明
     */
    @Test(priority = 1)
    public void testGetFuncTree() {
        Result<?> result = controller.getFuncTree();
        log.info("============返回数据=============:{}", result);
        Assert.assertEquals(HttpStatus.OK.value(), (int) result.getCode());
    }

    /**
     * @methodName testGetRoleList
     * @author yjz
     * @description 获取角色信息列表
     * @date 2020/11/19 16:27
     * @returnParam void
     * @note 修改说明
     */
    @Test(priority = 1)
    public void testGetRoleList() {
        Result<?> result = controller.getRoleList();
        log.info("============返回数据=============:{}", result);
        Assert.assertEquals(HttpStatus.OK.value(), (int) result.getCode());
    }

    /**
     * @methodName testGetRoleInfo
     * @author yjz
     * @description 获取角色信息
     * @date 2020/11/19 16:28
     * @returnParam void
     * @note 修改说明
     */
    @Test(priority = 1)
    public void testGetRoleInfo() {
        Result<?> result = controller.getRoleInfo(roledev.getJsdm());
        log.info("============返回数据=============:{}", result);
        Assert.assertEquals(HttpStatus.OK.value(), (int) result.getCode());
    }

    /**
     * @methodName testGetRoleFunc
     * @author yjz
     * @description 获取角色功能列表
     * @date 2020/11/19 16:35
     * @returnParam void
     * @note 修改说明
     */
    @Test(priority = 1)
    public void testGetRoleFunc() {
        Result<?> result = controller.getRoleFunc(roledev.getJsdm());
        log.info("============返回数据=============:{}", result);
        Assert.assertEquals(HttpStatus.OK.value(), (int) result.getCode());
    }

    /**
     * @methodName testCommitRoleFunc
     * @author yjz
     * @description 提交角色的功能授权信息
     * @date 2020/11/19 16:36
     * @returnParam void
     * @note 修改说明
     */
    @Test(priority = 1)
    public void testCommitRoleFunc() {
        VoAddJsGnlb voAddJsGnlb = new VoAddJsGnlb();
        voAddJsGnlb.setJsdm(roledev.getJsdm());
        List<String> list = new ArrayList<>();
        list.add("002");
        list.add("002001");
        list.add("002002");
        list.add("002003");
        list.add("002004");
        voAddJsGnlb.setGnlist(list);

        Result<?> result = controller.commitRoleFunc(voAddJsGnlb);
        log.info("============返回数据=============:{}", result);
        Assert.assertEquals(HttpStatus.OK.value(), (int) result.getCode());
    }

    /**
     * @methodName testUpdateRoleInfo
     * @author yjz
     * @description 修改角色信息
     * @date 2020/11/19 16:38
     * @returnParam void
     * @note 修改说明
     */
    @Test(priority = 1)
    public void testUpdateRoleInfo() {
        roledev.setJsmc("单元测试数据-修改");
        roledev.setJssm("单元测试角色说明-修改");
        roledev.setXssx(2);
        List<String> list = new ArrayList<>();
        list.add("001");
        list.add("001001");
        list.add("001002");
        list.add("001003");
        list.add("001004");
        roledev.setGnlist(list);

        Result<?> result = controller.updateRoleInfo(roledev);
        log.info("============返回数据=============:{}", result);
        Assert.assertEquals(HttpStatus.OK.value(), (int) result.getCode());
    }

    /**
     * @methodName testGetUserFuncTree
     * @author yjz
     * @description 获取用户的功能模块树
     * @date 2020/11/19 16:39
     * @returnParam void
     * @note 修改说明
     */
    @Test(priority = 1)
    public void testGetUserFuncTree() {
        Result<?> result = controller.getUserFuncTree("");
        log.info("============返回数据=============:{}", result);
        Assert.assertEquals(HttpStatus.OK.value(), (int) result.getCode());
    }

    /**
     * @methodName testDelRoleInfo
     * @author yjz
     * @description 删除角色
     * @date 2020/11/19 16:41
     * @returnParam void
     * @note 修改说明
     */
    @Test(priority = 2)
    public void testDelRoleInfo() {
        Result<?> result = controller.delRoleInfo(roledev.getJsdm());
        log.info("============返回数据=============:{}", result);
        Assert.assertEquals(HttpStatus.OK.value(), (int) result.getCode());
    }
}