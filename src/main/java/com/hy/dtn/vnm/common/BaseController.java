package com.hy.dtn.vnm.common;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用于其他Controller继承的基础Controller
 *
 * @author yjz
 * @version 1.0.0 2020-10-22
 */
public abstract class BaseController {
    @Resource
    protected HttpServletRequest request;

    @Resource
    protected HttpServletResponse response;
}
