package com.hy.dtn.vnm.common;

import com.hy.dtn.vnm.user.orm.mysql.dao.DictItemObjMapper;
import com.hy.dtn.vnm.user.orm.mysql.model.DictItemObj;
import com.hy.dtn.vnm.user.orm.mysql.model.DictItemObjKey;
import com.hy.dtn.vnm.util.SpringUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yjz
 * @version 1.0
 * @className SystemCommon
 * @date 2020/11/25 9:50
 * @description 系统公共参数类
 * @note 说明
 */
@Component
@Configuration
@Slf4j
public class SystemCommon {

    /**
     * 数据字典条目Dao层注入
     */
    @Resource
    DictItemObjMapper dictItemObjMapper;


    /**
     * 数据字典条目
     */
    @Getter
    public static Map<String, Map<String, String>> sjzdtmCommon = new ConcurrentHashMap<>();


    /**
     * @methodName initSjzdtmCommon
     * @author yjz
     * @description 初始化获取数据字典条目
     * @date 2020/12/03 12:02
     * @note 修改说明
     */
    @PostConstruct
    public void initSjzdtmCommon() {
        List<DictItemObj> dictItemObjList = dictItemObjMapper.selectAll();
        if (dictItemObjList != null) {
            for (DictItemObj dictItemObj : dictItemObjList) {
                if (sjzdtmCommon.get(dictItemObj.getZdbs()) != null) {
                    sjzdtmCommon.get(dictItemObj.getZdbs()).put(dictItemObj.getTmbs(), dictItemObj.getTmnr());
                } else {
                    Map<String, String> tmp = new ConcurrentHashMap<>(16);
                    tmp.put(dictItemObj.getTmbs(), dictItemObj.getTmnr());
                    sjzdtmCommon.put(dictItemObj.getZdbs(), tmp);
                }
            }
            log.info("-----------------字典表数据初始化完成-----------------");
        }
    }

    /**
     * @return java.lang.String
     * @methodName getSjzdtmCommonTmnr
     * @author yjz
     * @description 获取数据字典条目内容
     * @date 2020/12/03 14:05
     * @note 修改说明
     */
    public static String getSjzdtmCommonTmnr(String zdbs, String tmbs) {
        //查询数据，存在则返回对应数据
        if (sjzdtmCommon.get(zdbs) != null && sjzdtmCommon.get(zdbs).get(tmbs) != null) {
            return sjzdtmCommon.get(zdbs).get(tmbs);
        } else {
            //不存在，则查询数据库，并添加后返回
            return addToSjzdtmCommon(zdbs, tmbs);
        }
    }

    /**
     * @param zdbs, tmbs
     * @return java.lang.String
     * @methodName addToSjzdtmCommon
     * @author yjz
     * @description 添加到数据字典条目中
     * @date 2020/12/03 14:11
     * @note 修改说明
     */
    public static String addToSjzdtmCommon(String zdbs, String tmbs) {
        //通过反射注入Dao层类
        DictItemObjMapper dictItemObjMapper = SpringUtil.getBean(DictItemObjMapper.class);
        DictItemObjKey dictItemObjKey = new DictItemObjKey();
        dictItemObjKey.setZdbs(zdbs);
        dictItemObjKey.setTmbs(tmbs);
        DictItemObj dictItemObj = dictItemObjMapper.selectByPrimaryKey(dictItemObjKey);
        if (dictItemObj != null) {
            String tmnr = dictItemObj.getTmnr();
            if (sjzdtmCommon.get(zdbs) != null) {
                sjzdtmCommon.get(zdbs).put(tmbs, tmnr);
            } else {
                Map<String, String> tmp = new ConcurrentHashMap<>(16);
                tmp.put(tmbs, tmnr);
                sjzdtmCommon.put(zdbs, tmp);
            }
            return tmnr;
        }
        return null;
    }
}
