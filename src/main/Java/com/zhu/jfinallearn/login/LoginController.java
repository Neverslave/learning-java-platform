package com.zhu.jfinallearn.login;

import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.aop.Inject;
import com.jfinal.core.ActionKey;
import com.jfinal.kit.Ret;
import com.zhu.jfinallearn.common.controller.BaseController;
import com.zhu.jfinallearn.common.kit.IpKit;

public class LoginController extends BaseController {
    @Inject
    LoginService srv;

    /**
     * 显示登录界面
     *
     * */
    public void index(){
        keepPara("returnUrl"); //保持住returnUrl这个参数，以便登录后指向页面
        render("index.html");

    }

    /***
     * 登录
     */
    @Before(LoginValidator.class)
    public void doLogin(){
        boolean keepLogin = getParaToBoolean("keepLogin", false);
        String loginIp = IpKit.getRealIp(getRequest());
        Ret ret = srv.login(getPara("userName"), getPara("password"), keepLogin, loginIp);
        if (ret.isOk()) {
            String sessionId = ret.getStr(LoginService.sessionIdName);
            int maxAgeInSeconds = ret.getInt("maxAgeInSeconds");
            setCookie(LoginService.sessionIdName, sessionId, maxAgeInSeconds, true);
            setAttr(LoginService.loginAccountCacheName, ret.get(LoginService.loginAccountCacheName));
            ret.set("returnUrl", getPara("returnUrl", "/"));    // 如果 returnUrl 存在则跳过去，否则跳去首页
        }
        renderJson(ret);
    }

    /**
     * 退出登录
     */
    @Clear
    @ActionKey("/logout")
    public void logout() {
        srv.logout(getCookie(LoginService.sessionIdName));
        removeCookie(LoginService.sessionIdName);
        redirect("/");
    }

    /***
     * 渲染验证码
     *
    * */
    public void captcha() {
        renderCaptcha();
    }

}






