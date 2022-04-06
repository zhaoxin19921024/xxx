package com.hy.dtn.vnm.rpc.mo;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yjz
 * @version 1.0
 * @className BoRestResObj
 * @date 2021/01/01 14:23
 * @description 向外部发送restful请求的返回结果对象
 * @note 说明
 */
@Data
public class BoRestResObj implements Serializable {
    /**
     * 序列号
     */
    private static final long serialVersionUID = 6006630824637842015L;

    /**
     * 操作结果，0为失败，1为成功
     */
    private int optres;

    /**
     * 若失败，则存储失败原因
     */
    private String msg;

    /**
     * 返回对象的json字符串,设置全部显示
     */
    @JsonInclude
    private String resObj;

    /**
     * @param resObj 参数值
     * @return void
     * @description 重写Set方法
     * @methodName setResObj
     * @author yjz
     * @date 2021/01/05 19:15
     * @note 修改说明
     */
    public void setResObj(Object resObj) {
        if (resObj instanceof String) {
            this.resObj = (String) resObj;
        } else {
            this.resObj = JSONObject.toJSONString(resObj, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);
        }
    }
}
