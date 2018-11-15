package com.zhu.jfinallearn.index;

import com.jfinal.aop.Before;
import com.jfinal.aop.Inject;
import com.zhu.jfinallearn.common.controller.BaseController;
import com.zhu.jfinallearn.common.interceptor.AuthCacheClearInterceptor;
import com.zhu.jfinallearn.common.model.Download;
import com.zhu.jfinallearn.common.model.Feedback;
import com.zhu.jfinallearn.common.model.Project;
import com.zhu.jfinallearn.common.model.Share;
import com.zhu.jfinallearn.download.DownloadService;

import java.util.List;

public class IndexController extends BaseController {
    @Inject
    IndexService srv;
    public void index() {
        List<Project> projectList = srv.getProjectList();
        List<Share> shareList = srv.getShareList();
        List<Feedback> feedbackList = srv.getFeedbackList();
        List<Download> downloadList = DownloadService.me.getDownloadList();

        setAttr("projectList", projectList);
        setAttr("shareList", shareList);
        setAttr("feedbackList", feedbackList);
        setAttr("downloadList", downloadList);

        render("index.html");
    }

    @Before(AuthCacheClearInterceptor.class)
    public void clear() {
        srv.clearCache();
        redirect("/");
    }

}
