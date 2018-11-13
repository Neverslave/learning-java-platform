package com.zhu.jfinallearn.login;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

/**
 * ajax 登录参数验证
 */
public class LoginValidator extends Validator {
    // todo
    protected void validate(Controller c) {
        setShortCircuit(true); //设置短路校验，有错误直接跳出
        validateRequired("userName", "userNameMsg", "邮箱不能为空");
        validateEmail("userName", "userNameMsg", "邮箱格式不正确");

        validateRequired("password", "passwordMsg", "密码不能为空");
        validateCaptcha("captcha", "captchaMsg", "验证码不正确");

    }

    protected void handleError(Controller c) {
        c.renderJson();

    }

}
