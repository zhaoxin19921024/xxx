package com.hy.dtn.vnm.user.serv.intf;


import com.hy.dtn.vnm.common.Result;
import com.hy.dtn.vnm.user.bo.BoDictItemObj;

import java.util.List;
import java.util.Map;

/**
 * @author yjz
 * @version 1.0
 * @className DictServIntf
 * @date 2020/11/12 14:47
 * @description 字典信息服务接口类
 * @note 说明
 */
public interface DictServIntf {

    /**
     * @param zdbs
     * @methodName getDictItems
     * @author yjz
     * @description 数据字典查询
     * @date 2020/11/12 15:03
     * @returnParam java.util.List<com.hy.dtn.ops.user.bo.BoDictItemObj>
     * @note 修改说明
     */
    List<BoDictItemObj> getDictItems(String zdbs);
    
    /**
     * @description 批量查询并返回
     * @methodName getDictItemsByZdbsList
     * @author yjz
     * @date 2021/03/11 20:02
     * @param zdbsList zdbsList
     * @return com.hy.dtn.ops.common.Result<java.util.Map<java.lang.String,java.util.List<com.hy.dtn.ops.user.bo.BoDictItemObj>>>
     * @note 修改说明
     */
    Result<Map<String,List<BoDictItemObj>>> getDictItemsByZdbsList(List<String> zdbsList);
}
