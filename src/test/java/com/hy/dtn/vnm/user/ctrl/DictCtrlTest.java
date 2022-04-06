package com.hy.dtn.vnm.user.ctrl;

import com.hy.dtn.vnm.VnmApplication;
import com.hy.dtn.vnm.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author yjz
 * @version 1.0
 * @className DictCtrl
 * @description description
 * @note note
 * @since 11月 18, 2020
 */
@Slf4j
@SpringBootTest(classes = VnmApplication.class)
public class DictCtrlTest extends AbstractTestNGSpringContextTests {


    /**
     * Controller层必须注入，否则会报空指针异常
     */
    @Autowired
    private DictCtrl controller;


    /**
     * @methodName testGetDictObjById
     * @author yjz
     * @description 数据字典查询测试方法
     * @date 2020/11/18 11:33
     * @returnParam void
     * @note 修改说明
     */
    @Test
    public void testGetDictObjById() {
        //字典标识
        String zdbs = "LLMX";
        Result<?> result = controller.getDictObjById(zdbs);
        log.info("============返回数据=============:{}", result);
        Assert.assertEquals(HttpStatus.OK.value(), (int) result.getCode());
    }


}
