package com.zhu.jfinallearn._admin.common;

import com.jfinal.config.Routes;
import com.zhu.jfinallearn._admin.auth.AdminAuthInterceptor;
import com.zhu.jfinallearn._admin.controller.IndexAdminController;

/**
 * 后台管理路由
 * 注意：自 jfinal 3.0 开始，baseViewPath 改为在 Routes 中独立配置
 *      并且支持 Routes 级别的 Interceptor，这类拦截器将拦截所有
 *      在此 Routes 中添加的 Controller，行为上相当于 class 级别的拦截器
 *      Routes 级别的拦截器特别适用于后台管理这样的需要统一控制权限的场景
 *      减少了代码冗余
 */
public class AdminRoutes extends Routes {
    public void config() {
        addInterceptor(new AdminAuthInterceptor());
        addInterceptor(new PjaxInterceptor());
        setBaseViewPath("/_view/_admin");


        add("/admin", IndexAdminController.class,"/index");


    }
}
