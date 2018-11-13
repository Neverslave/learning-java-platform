package com.zhu.jfinallearn.common;

import com.jfinal.config.Routes;
import com.zhu.jfinallearn.login.LoginController;

/**
 * 前台路由管理
 *
 * */
public class FrontRoutes extends Routes {
    public void config() {
        setBaseViewPath("/_view");
        //todo  添加控制器
        add("/login", LoginController.class);




    }
}
