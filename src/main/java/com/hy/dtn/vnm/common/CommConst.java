package com.hy.dtn.vnm.common;

/**
 * @author yjz
 * @version 1.0
 * @className
 * @date 2020/11/25 9:34
 * @description 常量对象
 * @note 说明
 */
public class CommConst {

    /**
     * 网络模板缓存数据
     */
    public static final String WLMB_CACHE = "NetTemplateCache";

    /**
     * 应用场景缓存数据
     */
    public static final String SCENE_CACHE = "SceneCache";

    /**
     * 网络模板
     */
    public static final String WLMB = "netTemplate";

    /**
     * 应用场景
     */
    public static final String SCENE = "scene";

    /**
     * 1：启动
     */
    public static final int STATUS_APPNETTASK_START = 1;

    /**
     * 0：未启动
     */
    public static final int STATUS_APPNETTASK_STOP = 0;

    /**
     * 0：操作成功
     */
    public static final int STATUS_OPT_SUCC = 1;
    /**
     * 1：操作失败
     */
    public static final int STATUS_OPT_FAIL = 0;

    /**
     * 状态机的参数值：0：编辑；1：部署中，2：部署成功；3：部署失败；4：启动中；:5：启动运行；6：启动失败；7：停止运行中，8：停止运行失败；9：删除部署中；10：删除部署失败
     */
    /**
     * 0：编辑
     */
    public static final int STATUS_APPNETDEPLOY_NO = 0;
    /**
     * 1：部署中
     */
    public static final int STATUS_APPNETDEPLOY_DEPLOYING = 1;
    /**
     * 2：部署成功
     */
    public static final int STATUS_APPNETDEPLOY_DEPLOYED = 2;
    /**
     * 3：部署失败
     */
    public static final int STATUS_APPNETDEPLOY_DEPLOYFAILED = 3;
    /**
     * 4：启动中
     */
    public static final int STATUS_APPNETDEPLOY_RUNING = 4;
    /**
     * 5：启动运行
     */
    public static final int STATUS_APPNETDEPLOY_RUNNED = 5;
    /**
     * 6：启动失败
     */
    public static final int STATUS_APPNETDEPLOY_RUNFAILED = 6;
    /**
     * 7：停止运行中
     */
    public static final int STATUS_APPNETDEPLOY_STOPING = 7;
    /**
     * 8：停止运行失败
     */
    public static final int STATUS_APPNETDEPLOY_STOPFAILED = 8;
    /**
     * 9：删除部署中
     */
    public static final int STATUS_APPNETDEPLOY_DELING = 9;
    /**
     * 10：删除部署失败
     */
    public static final int STATUS_APPNETDEPLOY_DELFAILED = 10;

    /**
     * 开关开启
     */
    public static final int SWITCH_ON = 1;
    /**
     * 开关关闭
     */
    public static final int SWITCH_OFF = 0;

    /**
     * 登录用户信息对象指代名
     */
    public static final String NAME_LOGUSEROBJ = "LOGUSER";

    /**
     * @description 计算初始密码,sfz为身份证号码，取其后六位
     * @methodName getInitPsd
     * @author yjz
     * @date 2021/03/01 17:08
     * @param sfz
     * @return java.lang.String
     * @note 修改说明
     */
    public static final String getInitPsd(String sfz) {
        return "111111";
    }

}
	