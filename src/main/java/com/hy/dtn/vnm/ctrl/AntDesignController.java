package com.hy.dtn.vnm.ctrl;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author yjz
 * @version 1.0
 * @className AntDesignController
 * @date 2020/12/07 16:29
 * @description 解决前端刷新页面数据404的问题
 * @note 说明
 * 此注解 @ApiIgnore，swagger忽略当前接口描述信息
 */
@ApiIgnore
@Controller
public class AntDesignController implements ErrorController {

    /**
     * @methodName index
     * @author yjz
     * @description 错误页面前端重定向
     * @date 2020/12/07 16:54
     * @return  java.lang.String
     * @note 修改说明
     */
    @RequestMapping(value = "/error")
    public String index() {
        return "index";
    }

    /**
     * The return value from this method is not used; the property `server.error.path`
     * must be set to override the default error page path.
     *
     * @return the error path
     * @deprecated since 2.3.0 in favor of setting the property `server.error.path`
     */
    @Override
    public String getErrorPath() {
        return "/error";
    }
}
