package com.hy.dtn.vnm.util;

import com.hy.dtn.vnm.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.BatchExecutorException;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author yjz
 * @version 1.0
 * @className GlobalExceptionHandler
 * @date 2020/11/11 9:58
 * @description 全局的异常拦截器(带有RequestMapping注解上的拦截)
 * @note 说明
 */
@RestControllerAdvice
@Slf4j
@Order(-1)
public class GlobalExceptionHandler {

    /**
     * @param e
     * @return com.hy.dtn.ops.common.Result<?>
     * @methodName batchException
     * @author yjz
     * @description 方法描述
     * @date 2020/11/11
     * @note 修改说明
     */
    @ExceptionHandler(BatchExecutorException.class)
    public Result<?> batchException(BatchExecutorException e) {
        log.error("异常原因：{}", e.toString());
        return Result.error("数据库操作异常");
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result<?> SQLIntegrityException(SQLIntegrityConstraintViolationException e) {
        log.error("异常原因：{}", e.toString());
        return Result.error("数据库操作异常");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public Result<?> dataIntegrityViolationException(DataIntegrityViolationException e) {
        log.error("异常原因：{}", e.toString());
        return Result.error("数据库操作异常");
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public Result<?> httpServerErrorException(HttpServerErrorException e) {
        log.error("异常原因：{}", e.toString());
        return Result.error("集群服务调用异常");
    }

}
