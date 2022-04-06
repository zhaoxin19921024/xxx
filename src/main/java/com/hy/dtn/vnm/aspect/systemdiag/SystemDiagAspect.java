package com.hy.dtn.vnm.aspect.systemdiag;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.hy.dtn.vnm.common.CommConst;
import com.hy.dtn.vnm.common.Result;
import com.hy.dtn.vnm.orm.mysql.dao.XtCzrzDao;
import com.hy.dtn.vnm.orm.mysql.model.XtCzrz;
import com.hy.dtn.vnm.user.bo.BoAllUserObj;
import com.hy.dtn.vnm.util.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author yjz
 * @version 1.0
 * @className SystemDiagAspect
 * @date 2020/12/01 15:41
 * @description 系统级别的日志切面
 * @note 说明
 * 接口校验功能，暂时移除
 * //@Order(100)
 * //@Aspect
 * //@Component
 * //@SuppressWarnings("all")
 */
@Slf4j
public class SystemDiagAspect {

    /**
     * 定义常量CODE
     */
    private static final String CODE="code";

    /**
     * 注入系统操作日志Dao层
     */
    @Resource
    XtCzrzDao xtCzrzDao;


    /**
     * @methodName controllerAspect
     * @author yjz
     * @description Controller层切点
     * @date 2020/12/01 16:10
     * @returnParam void
     * @note 修改说明
     */
    @Pointcut("@annotation(com.hy.dtn.vnm.aspect.systemdiag.SystemControllerLog)")
    public void controllerAspect() {

    }

    /**
     * @methodName serviceAspect
     * @author yjz
     * @description Service层切点
     * @date 2020/12/01 16:12
     * @returnParam void
     * @note 修改说明
     */
    @Pointcut("@annotation(com.hy.dtn.vnm.aspect.systemdiag.SystemServiceLog)")
    public void serviceAspect() {

    }


    /**
     * @param joinPoint 切点
     * @methodName doAfterController
     * @author yjz
     * @description 环绕切面，对Controller进行日志管理
     * @date 2020/12/01 17:06
     * @returnParam void
     * @note 修改说明
     */
    @Around(value = "controllerAspect()")
    public Object doAfterController(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取请求信息
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        //获取session
        HttpSession session = request.getSession();
        //读取session中的用户
        BoAllUserObj logUser = (BoAllUserObj) session.getAttribute(CommConst.NAME_LOGUSEROBJ);
        //获取ip地址
        String ip = IpUtils.getIpAddress(request);
        //获取请求参数
        JSONObject controllerParams = getControllerParams(joinPoint);
        //执行方法，并返回结果
        Object result = joinPoint.proceed();
        try {
            //定义日志对象
            XtCzrz xtCzrz = new XtCzrz();
            //设置操作id
            xtCzrz.setLid(IdUtil.simpleUUID());
            //获取注解中的description描述信息
            xtCzrz.setLx(getControllerMethodDescription(joinPoint));
            //操作内容，请求数据
            xtCzrz.setNr(controllerParams.toJSONString());
            //若当前已登录
            if (null != logUser) {
                xtCzrz.setUid(logUser.getYhid());
                xtCzrz.setXm(logUser.getYhxm());
            } else {
                xtCzrz.setUid("0");
                xtCzrz.setXm("测试账号");
            }
            if (result instanceof Result) {
                JSONObject object = (JSONObject) JSONObject.toJSON(result);
                if (object.getInteger(CODE) == HttpStatus.OK.value()) {
                    xtCzrz.setJg(1);
                } else {
                    xtCzrz.setJg(0);
                    xtCzrz.setSbyy(object.getString("msg"));
                }
            }
            //插入数据
            xtCzrzDao.addXtCzrz(xtCzrz);
            log.info("===当前请求的对象信息为==={}", xtCzrz);
        } catch (Exception e) {
            log.error("===环绕切面异常===异常信息：{}", e.getMessage());
        }
        return result;
    }

    /**
     * @param joinPoint 切点
     * @methodName getControllerMethodDescription
     * @author yjz
     * @description 获取当前Controller层方法的描述信息
     * @date 2020/12/01 16:42
     * @returnParam java.lang.String
     * @note 修改说明
     */
    public static String getControllerMethodDescription(JoinPoint joinPoint) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class<?> targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class<?>[] classes = method.getParameterTypes();
                if (classes.length == arguments.length) {
                    description = method.getAnnotation(SystemControllerLog.class).description();
                    break;
                }
            }
        }
        return description;
    }

    /**
     * @param joinPoint 切点
     * @methodName getControllerParams
     * @author yjz
     * @description 获取Controller的请求参数
     * @date 2020/12/01 17:09
     * @returnParam com.alibaba.fastjson.JSONObject
     * @note 修改说明
     */
    public static JSONObject getControllerParams(ProceedingJoinPoint joinPoint) {
        //请求参数
        JSONObject jsonObject = new JSONObject();

        Object[] args = joinPoint.getArgs();
        //获取参数名称和值
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String[] paramsName = methodSignature.getParameterNames();
        for (int i = 0; i < paramsName.length; i++) {
            if (i >= args.length) {
                break;
            }
            Object value = args[i];
            String name = paramsName[i];
            //去除以下三种类型的数据
            if (!(value instanceof HttpServletRequest) && !(value instanceof HttpServletResponse) && !(value instanceof HttpSession)) {
                jsonObject.put(name, value);
            }
        }
        log.info("请求参数对象为：{}", jsonObject);
        return jsonObject;
    }
}
