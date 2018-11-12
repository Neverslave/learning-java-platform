package com.zhu.jfinallearn.common.model;

import com.zhu.jfinallearn.common.model.base.BaseAccount;

/**
 * Account
 */
@SuppressWarnings("serial")
public class Account extends BaseAccount<Account> {
    public static final long serialVersionUID = 1L;
    public static final String AVATAR_NO_AVATAR = "x.jpg"; //刚注册使用的默认头像


    public static final int STATUS_LOCK_ID = -1;	// 锁定账号，无法做任何事情
    public static final int STATUS_REG = 0;			// 注册、未激活
    public static final int STATUS_OK = 1;			// 正常、已激活

    public boolean isStatusOk() {
        return getStatus() == STATUS_OK;
    }

    public boolean isStatusReg() {
        return getStatus() == STATUS_REG;
    }

    public boolean isStatusLockId() {
        return getStatus() == STATUS_LOCK_ID;
    }

  //todo 实现xss攻击过滤

    /**
     * 去除Account 字段中的敏感字段 密码和盐值
     * */
    public Account removeSensitiveInfo(){
        remove("password","salt");
        return this;
    }

	
}
