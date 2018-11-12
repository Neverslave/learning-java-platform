package com.zhu.jfinallearn.common.account;


import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.ehcache.CacheKit;
import com.zhu.jfinallearn.common.model.Account;

/**
 * 账户业务
 * */
@SuppressWarnings("rawtypes") //忽略传参时需要传递泛型参数的警告
public class AccountService {
    public static final AccountService me = new AccountService();
    private Account dao = new Account().dao(); //dao 仅能进行查询 不可承载数据private final  String allAccountCacheName = "allAccounts";/**
    private final String allAccountsCacheName = "allAccounts";
    /**
     * 更新账户头像
     * */
    public void updateAccountAvatar(int accountId , String relativePathFileName){
        Db.update("update account set avatar = ? where id = ? limit 1",relativePathFileName,accountId);
        clearCache(accountId);
    }

    public Account getByNickName(String nickName,String columns){
        if(StrKit.isBlank(nickName)){
            return null;
        }
        return  dao.findFirst("select"+columns+"from account where nickName = ? and status = ? limit 1",Account.STATUS_OK);

    }
    /**
     * 通过id 获取Account对象，只能获取未被block 的account
     * */
    public Account getUsefulById(int accountId){
        Account account = getById(accountId);

        return account.isStatusOk() ? account : null;

    }

    /**
     * 优先从缓存中获取 account 对象，可获取到被 block 的 account
     */
    public Account getById(int accountId){
        Account account = CacheKit.get(allAccountsCacheName,accountId);
        if(account == null){
            //考虑到可能查询所有状态的账户，先放开status判断
            account = dao.findFirst("select * from account where id = ? limit 1",accountId);
            if(account !=null){
                account.removeSensitiveInfo(); //去除敏感字段
                CacheKit.put(allAccountsCacheName,accountId,account); //放入缓存中
            }
        }

        return account ;

    }







    /**
    * 通过accountId 清除Ecache 中的缓存
    * */
    public  void clearCache(int accountId){

        CacheKit.remove(allAccountsCacheName,accountId);
    }

}
