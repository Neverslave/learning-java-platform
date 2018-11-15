package com.zhu.jfinallearn._admin.index;

import com.jfinal.aop.Inject;
import com.zhu.jfinallearn.common.controller.BaseController;

public class IndexController extends BaseController {
    @Inject
    IndexAdminService srv;

    public void index(){


        setAttr("accountProfile", srv.getAccountProfile());
        setAttr("projectProfile", srv.getProjectProfile());
        setAttr("shareProfile", srv.getShareProfile());
        setAttr("feedbackProfile", srv.getFeedbackProfile());
        setAttr("permissionProfile", srv.getPermissionProfile());

        render("index.html");
    }
}
