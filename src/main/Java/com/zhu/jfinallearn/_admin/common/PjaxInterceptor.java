package com.zhu.jfinallearn._admin.common;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
/**
 *
 * 设置pjax标志
 * */
public class PjaxInterceptor implements Interceptor {
    public void intercept(Invocation inv) {
        try{
            inv.invoke();
        }finally {
            Controller c =inv.getController();
            boolean isPjax = "true".equalsIgnoreCase(c.getHeader("'x-PJAX"));
            c.setAttr("isPjax",isPjax);
        }
    }
}
