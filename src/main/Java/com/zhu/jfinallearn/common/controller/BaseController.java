package com.zhu.jfinallearn.common.controller;

import com.jfinal.core.Controller;


/**
 * 基础控制器，方便获取登录信息
 *
 * 警告：由于 BaseController 中声明了 Account loginAccount 属性，
 *      所以不能使用 FastControllerFactory 这类回收 Controller 的用法
 *      在 jfinal 3.5 发布以后，可以通过继承 _clear_() 方法来清除属性中的值
 *      才能使用 FastControllerFactory
 *      用户自己的 Controller 也是同样的道理
 *
 * 注意：
 * 需要 LoginSessionInterceptor 配合，该拦截器使用
 * setAttr("loginAccount", ...) 事先注入了登录账户
 * 否则即便已经登录，该控制器也会认为没有登录
 *
 */
public class BaseController extends Controller {

}
