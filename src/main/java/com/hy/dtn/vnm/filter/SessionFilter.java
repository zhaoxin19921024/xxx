package com.hy.dtn.vnm.filter;

import com.hy.dtn.vnm.common.CommConst;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author yjz
 * @version 1.0
 * @className SessionFilter
 * @date 2020/11/20 9:48
 * @description session的拦截器
 * @note 说明
 */
@Slf4j
//@Order(1)
//@Component
public class SessionFilter extends BaseFilter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);
        String url = request.getRequestURI();

        log.info("=========当前filter拦截的url========{}", url);
        boolean needFilter = isNeedFilter(url);
        //不需要过滤直接传给下一个过滤器
        if (!needFilter) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            //存在数据，则直接传入下一个过滤器
            if (session != null && session.getAttribute(CommConst.NAME_LOGUSEROBJ) != null) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                //重定向到登录页
                response.sendRedirect(request.getContextPath() + "/#/login");
            }
        }
    }

    @Override
    public void destroy() {

    }

    public boolean isNeedFilter(String url) {
        for (String includeUrl : noLoginUrl) {
            if (url.contains(includeUrl) || "/".equals(url)) {
                return false;
            }

        }
        return true;
    }
}
