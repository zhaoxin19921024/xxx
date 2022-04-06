package com.hy.dtn.vnm.user.serv.impl;


import com.hy.dtn.vnm.common.Result;
import com.hy.dtn.vnm.user.bo.BoDictItemObj;
import com.hy.dtn.vnm.user.orm.mysql.dao.DictItemObjMapper;
import com.hy.dtn.vnm.user.orm.mysql.model.DictItemObj;
import com.hy.dtn.vnm.user.serv.intf.DictServIntf;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yjz
 * @version 1.0
 * @className DictServImpl
 * @date 2020/11/12 14:57
 * @description 字典信息服务接口实现类
 * @note 说明
 */
@Service
public class DictServImpl implements DictServIntf {

    @Resource
    private DictItemObjMapper mapper;

    @Override
    public List<BoDictItemObj> getDictItems(String zdbs) {
        List<DictItemObj> dictItems = this.mapper.selectByZdbs(zdbs);
        if (null == dictItems) {
            return new ArrayList<>();
        }
        List<BoDictItemObj> res = new ArrayList<>();
        for (DictItemObj itm : dictItems) {
            BoDictItemObj obj = new BoDictItemObj(itm);
            res.add(obj);
        }
        return res;
    }
    
    /**
     * @description 批量查询
     * @methodName getDictItemsByZdbsList
     * @author yjz
     * @date 2021/03/11 20:11
     * @param zdbsList 待查询列表
     * @return com.hy.dtn.ops.common.Result<java.util.Map<java.lang.String,java.util.List<com.hy.dtn.ops.user.bo.BoDictItemObj>>>
     * @note 修改说明
     */
    @Override
    public Result<Map<String, List<BoDictItemObj>>> getDictItemsByZdbsList(List<String> zdbsList) {
        List<DictItemObj> dictItemObjs = mapper.selectByZdbsList(zdbsList);
        Map<String, List<BoDictItemObj>> res = new HashMap<>();
        if (dictItemObjs != null) {
            for (DictItemObj dictItemObj : dictItemObjs) {
                res.computeIfAbsent(dictItemObj.getZdbs(), k -> new ArrayList<>());
                res.get(dictItemObj.getZdbs()).add(new BoDictItemObj(dictItemObj));
            }
        }
        return Result.ok(res);
    }

}
