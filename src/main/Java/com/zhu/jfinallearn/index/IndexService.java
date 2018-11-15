package com.zhu.jfinallearn.index;

import com.jfinal.plugin.activerecord.SqlPara;
import com.jfinal.plugin.ehcache.CacheKit;
import com.zhu.jfinallearn.common.model.Feedback;
import com.zhu.jfinallearn.common.model.Project;
import com.zhu.jfinallearn.common.model.Share;
import com.zhu.jfinallearn.common.safe.JsoupFilter;

import java.util.List;

/**
 * 首页业务，主要为了方便做缓存，以及排序逻辑
 */
public class IndexService {
    public static final IndexService me = new IndexService();
    private final String indexCacheName = "index";
    private Project projectDao = new Project().dao();
    private Share shareDao = new Share().dao();
    private Feedback feedbackDao = new Feedback().dao();

    public List<Project> getProjectList() {
        SqlPara sqlPara = projectDao.getSqlPara("index.getProjectList", Project.REPORT_BLOCK_NUM);
        List<Project> projectList =  projectDao.findByCache(indexCacheName, "projectList", sqlPara.getSql(), sqlPara.getPara());

        // 列表页显示 content 的摘要信息需要过滤为纯文本，去除所有标记
        JsoupFilter.filterArticleList(projectList, 50, 120);
        return projectList;
    }

    public List<Share> getShareList() {
        SqlPara sqlPara = shareDao.getSqlPara("index.getShareList", Share.REPORT_BLOCK_NUM);
        List<Share> shareList = shareDao.findByCache(indexCacheName, "shareList", sqlPara.getSql(), sqlPara.getPara());

        // 列表页显示 content 的摘要信息需要过滤为纯文本，去除所有标记
        JsoupFilter.filterArticleList(shareList, 50, 120);
        return shareList;
    }

    public List<Feedback> getFeedbackList() {
        SqlPara sqlPara = feedbackDao.getSqlPara("index.getFeedbackList", Feedback.REPORT_BLOCK_NUM);
        List<Feedback> feedbackList = feedbackDao.findByCache(indexCacheName, "feedbackList", sqlPara.getSql(), sqlPara.getPara());

        // 列表页显示 content 的摘要信息需要过滤为纯文本，去除所有标记
        JsoupFilter.filterArticleList(feedbackList, 50, 120);
        return feedbackList;
    }

    public void clearCache() {
        CacheKit.removeAll(indexCacheName);
    }



}




