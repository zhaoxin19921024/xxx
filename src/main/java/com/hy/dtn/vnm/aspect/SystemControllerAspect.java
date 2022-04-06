package com.hy.dtn.vnm.aspect;


import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.hy.dtn.vnm.aspect.systemdiag.SystemControllerLog;
import com.hy.dtn.vnm.common.CommConst;
import com.hy.dtn.vnm.common.Result;
import com.hy.dtn.vnm.orm.mysql.dao.XtCzrzDao;
import com.hy.dtn.vnm.orm.mysql.model.XtCzrz;
import com.hy.dtn.vnm.user.bo.BoAllUserObj;
import com.hy.dtn.vnm.util.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author yjz
 * @version 1.0
 * @className MessageValidation
 * @date 2020/11/02 11:49
 * @description 系统Controller类的统一切面
 * @note 说明
 */
@Aspect
@Component
@Slf4j
public class SystemControllerAspect {

    /**
     * 定义常量CODE
     */
    private static final String CODE = "code";

    /**
     * 定义常量MSG
     */
    private static final String MSG = "msg";

    /**
     * 注入系统操作日志Dao层
     */
    @Resource
    XtCzrzDao xtCzrzDao;

    @Resource
    MessageSource messageSource;

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
     * @return void
     * @methodName validation
     * @author yjz
     * @description 切点设置
     * @date 2020/11/02
     * @note 修改说明
     */
    @Pointcut("execution(* com.hy.dtn.vnm.*.ctrl..*.*(..) )")
    public void validation() {
    }

    /**
     * @param proceedingJoinPoint 切点
     * @return Object
     * @methodName controllerAround
     * @author yjz
     * @description Controller层的环绕增强
     * @date 2020/11/02
     * @note 修改说明
     */
    @Around(value = "validation() || controllerAspect()")
    public Object controllerAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //执行方法，并返回结果
        Object result = proceedingJoinPoint.proceed();
        if (result instanceof Result) {
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(result);
            //返回的操作信息
            String tempStr = "";
            //若返回码code值非200，则获取msg
            if (jsonObject.getInteger(CODE) != HttpStatus.OK.value()) {
                //获取错误信息
                String msg = jsonObject.getString(MSG);
                //若错误信息不为空
                if (StringUtils.isNotEmpty(msg)) {
                    //获取错误信息的配置文件中的数据
                    tempStr = messageSource.getMessage(msg, null, msg, LocaleContextHolder.getLocale());
                    //若返回的数据为空
                    if (StringUtils.isEmpty(tempStr)) {
                        //查询默认的返回错误信息
                        tempStr = messageSource.getMessage(msg, null, "操作失败，错误信息未定义！", LocaleContextHolder.getLocale());
                    }
                } else {
                    //查询默认的返回错误信息
                    tempStr = messageSource.getMessage(msg, null, "操作失败", LocaleContextHolder.getLocale());
                }
            } else if (jsonObject.getInteger(CODE) == HttpStatus.OK.value()) {
                //若返回码为200
                String msg = jsonObject.getString(MSG);
                //获取配置文件中定义的操作成功数据
                tempStr = messageSource.getMessage(msg, null, "操作成功", LocaleContextHolder.getLocale());
            }
            //若返回的操作信息不为空，则设置值
            if (StringUtils.isNotEmpty(tempStr)) {
                jsonObject.put(MSG, tempStr);
            }
            //统一转换为JSON
//            ObjectMapper objectMapper = new ObjectMapper();
            //设备JSON的过滤方式，现放入配置文件中
//            objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
//            Object getObj = jsonObject.get("data");
//            if (getObj != null) {
////                String data = objectMapper.writeValueAsString(getObj);
//                jsonObject.put("data", getObj);
//            }
            result = jsonObject.toJavaObject(Result.class);
            //保存日志
            systemControllerSaveDiag(proceedingJoinPoint, result);
        }
        return result;
    }

    /**
     * @param joinPoint, result
     * @methodName systemControllerSaveDiag
     * @author yjz
     * @description 保存日志
     * @date 2020/12/02 13:52
     * @returnParam void
     * @note 修改说明
     */
    public void systemControllerSaveDiag(ProceedingJoinPoint joinPoint, Object result) {
        //获取请求信息
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        //获取session
        HttpSession session = request.getSession();
        //读取session中的用户
        BoAllUserObj logUser = (BoAllUserObj) session.getAttribute(CommConst.NAME_LOGUSEROBJ);
        //获取ip地址
        String ip = IpUtils.getIpAddress(request);
        //获取请求URL
        String url = request.getRequestURI();
        //获取请求参数
        JSONObject controllerParams = getControllerParams(joinPoint);
        try {
            //定义日志对象
            XtCzrz xtCzrz = new XtCzrz();
            //设置操作id
            xtCzrz.setLid(IdUtil.simpleUUID());
            //获取注解中的description描述信息  getControllerMethodDescription(joinPoint)
            xtCzrz.setLx(url);
            //操作内容，请求数据
            xtCzrz.setNr(controllerParams.toString());
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
            log.error("===前置通知异常===异常信息：{}", e.getMessage());
        }
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
                    //存在对应的注解，则获取描述信息
                    if (null != method.getAnnotation(SystemControllerLog.class)) {
                        description = method.getAnnotation(SystemControllerLog.class).description();
                        break;
                    } else {
                        //不存在对应的注解，则返回空，用于后续判断
                        return null;
                    }
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
            if (!(value instanceof HttpServletRequest)
                    && !(value instanceof HttpServletResponse)
                    && !(value instanceof HttpSession)
                    //TODO:文件后续将单独处理
                    && !(value instanceof MultipartFile)
            ) {
                jsonObject.put(name, value);
            }
        }
        log.info("请求参数对象为{}", jsonObject);
        return jsonObject;
    }
}
