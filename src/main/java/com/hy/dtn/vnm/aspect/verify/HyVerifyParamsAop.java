package com.hy.dtn.vnm.aspect.verify;

import com.hy.dtn.vnm.common.Result;
import com.hy.dtn.vnm.util.HyReflectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yjz
 * @version 1.0
 * @className HyVerifyParamsAop
 * @date 2020/11/05 15:39
 * @description 自定义参数校验的实现
 * @note /@order(1000) //使用order属性，设置该类在spring容器中的加载顺序
 * /@aspect //作用是把当前类标识为一个切面供容器读取
 * /@component
 */
@Order(1000)
@Aspect
@Component
@Slf4j
public class HyVerifyParamsAop {

    /**
     * 当前是否禁用
     */
    @Value("${verifyparams.enable}")
    private boolean enable;

    @Pointcut(" @annotation(com.hy.dtn.vnm.aspect.verify.HyVerifyParamsIntf)")
    public void verifyPointCut() {
    }

    @Before(value = "verifyPointCut()")
    public void check() {

    }

    @After(value = "verifyPointCut()")
    public void bye() {

    }

    /**
     * @param proceedingJoinPoint 切点
     * @param hyVerifyParamsIntf      设备切入点
     * @return java.lang.Object
     * @description 切面执行方法
     * @methodName process
     * @author yjz
     * @date 2021/01/12 11:07
     * @note 修改说明
     */
    @Around(value = "verifyPointCut()&&@annotation(hyVerifyParamsIntf)")
    public Object process(ProceedingJoinPoint proceedingJoinPoint, HyVerifyParamsIntf hyVerifyParamsIntf) throws Throwable {
        //获取方法的入参
        if (enable) {
            Object[] args = proceedingJoinPoint.getArgs();
            StringBuffer errMsg = new StringBuffer();
            //遍历参数
            for (Object obj : args) {
                //数据转换，统一调用toVerify方法,由bo去执行对应的参数。TODO:后续可以将注解中的操作类型，作为参数传递给参数校验方法
                List<Object> params = new ArrayList<>();
                //添加数据类型
                params.add(hyVerifyParamsIntf.type());
                //判断当前操作的库表类型
                params.add(hyVerifyParamsIntf.scannedArea());
                Object resultData = HyReflectUtil.executeMethod(obj, "toVerify", params);
                if (resultData == null) {
                    continue;
                }
                //判断返回数据类型的String是否为空，若数据不为空，则添加到错误信息中区
                if (StringUtils.isNotEmpty(resultData.toString())) {
                    errMsg.append(resultData.toString());
                }
            }
            //查看错误信息，若存在错误，则直接返回错误信息
            if (StringUtils.isNotEmpty(errMsg)) {
                return Result.error(errMsg.toString());
            }
        }
        //若校验通过，则正常继续执行
        return proceedingJoinPoint.proceed();
    }

}
