package com.hy.dtn.vnm.user.ctrl;

import com.hy.dtn.vnm.common.BaseController;
import com.hy.dtn.vnm.common.Result;
import com.hy.dtn.vnm.user.bo.BoDictItemObj;
import com.hy.dtn.vnm.user.serv.intf.DictServIntf;
import com.hy.dtn.vnm.user.vo.VoDictItemObj;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yjz
 * @version 1.0
 * @className DictCtrl
 * @date 2020/11/12 15:01
 * @description 数据字典
 * @note 说明
 */
@Slf4j
@Api(tags = "数据字典")
@RestController
@RequestMapping("/api/dict")
public class DictCtrl extends BaseController {

    @Resource
    private DictServIntf dictServ;


    @ApiOperation(value = "数据字典查询")
    @ApiImplicitParam(name = "zdbs", value = "字典标识", dataType = "String", paramType = "path", required = true)
    @GetMapping("/getDictObjById")
    public Result<List<VoDictItemObj>> getDictObjById(@RequestParam(value = "zdbs", required = false) String zdbs) {
        List<BoDictItemObj> boDictItem = dictServ.getDictItems(zdbs);
        //数据转换
        List<VoDictItemObj> voDictItemObjs = new ArrayList<>();
        for (BoDictItemObj boDictItemObj : boDictItem) {
            voDictItemObjs.add(new VoDictItemObj(boDictItemObj));
        }
        return Result.ok(voDictItemObjs);
    }


    @ApiOperation(value = "数据字典批量查询")
    @ApiImplicitParam(name = "ids", value = "字典标识", dataType = "String", paramType = "query", required = true, allowMultiple = true)
    @GetMapping("/getDictObjByIds")
    public Result<Map<String, List<BoDictItemObj>>> getDictObjByIds(@RequestParam List<String> ids) {
        return dictServ.getDictItemsByZdbsList(ids);
    }
}
