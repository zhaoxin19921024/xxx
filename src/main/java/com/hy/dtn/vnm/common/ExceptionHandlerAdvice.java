package com.hy.dtn.vnm.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * 统一异常处理类
 * 
 * @author yjz
 * @version 1.0.0 2020-10-22
 */
@RestControllerAdvice
public class ExceptionHandlerAdvice {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);

    /**
     * 底层的全局异常处理方法
     *
     * @param e
     *            Throwable
     * @return 异常结果数据
     */
    @ExceptionHandler(Throwable.class)
    public Result<?> throwableErrorHandler(Throwable e) {
        logger.error(e.getMessage(), e);
        return Result.error(e.getMessage(), e);
    }

    /**
     * 默认的全局异常处理方法
     *
     * @param e
     *            Exception
     * @return 异常结果数据
     */
    @ExceptionHandler(Exception.class)
    public Result<?> defaultErrorHandler(Exception e) {
        logger.error(e.getMessage(), e);
        return Result.error(e.getMessage(), e);
    }

    /**
     * 运行时异常处理方法
     *
     * @param e
     *            RuntimeException
     * @return 异常结果数据
     */
    @ExceptionHandler(RuntimeException.class)
    public Result<?> runtimeExceptionHandler(RuntimeException e) {
        logger.error(e.getMessage(), e);
        return Result.error(e.getMessage(), e);
    }

    /**
     * Bean Validation字段验证的异常处理方法
     *
     * @param e
     *            MethodArgumentNotValidException
     * @return 异常结果数据
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        return Result.failed(e.getBindingResult().getFieldError().getDefaultMessage());
    }

    /**
     * Bean Validation字段验证的异常处理方法
     *
     * @param e BindException
     * @return 异常结果数据
     */
    @ExceptionHandler(BindException.class)
    public Result<?> bindExceptionHandler(BindException e) {
        return Result.failed(e.getBindingResult().getFieldError().getDefaultMessage());
    }

    /**
     * 上传文件的总大小超过maxUploadSize配置的大小时的异常处理方法
     *
     * @param e MaxUploadSizeExceededException
     * @return 异常结果数据
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Result<?> maxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        return Result.failed("上传文件总大小超过 " + (e.getMaxUploadSize() / 1024 / 1024) + "M 大小限制！");
    }
}
