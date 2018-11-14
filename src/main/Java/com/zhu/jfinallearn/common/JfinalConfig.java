package com.zhu.jfinallearn.common;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.config.*;
import com.jfinal.json.MixedJsonFactory;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.cron4j.Cron4jPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.render.JsonRender;
import com.jfinal.template.Engine;
import com.zhu.jfinallearn._admin.auth.AdminAuthKit;
import com.zhu.jfinallearn._admin.common.AdminRoutes;
import com.zhu.jfinallearn.common.handler.UrlSeoHandler;
import com.zhu.jfinallearn.common.interceptor.LoginSessionInterceptor;
import com.zhu.jfinallearn.common.kit.DruidKit;
import com.zhu.jfinallearn.common.model._MappingKit;
import com.zhu.jfinallearn.login.LoginService;

import java.sql.Connection;

/*
* Jfinal的配置类
*
* */
public class JfinalConfig extends JFinalConfig {

    //先加载开发环境配置，再追加生产环境配置覆盖开发环境配置
    private static Prop prop = PropKit.use( "config.txt" )
            .appendIfExists( "config_pro.txt" );  //正式环境


    private WallFilter wallFilter; //druid 自带防止sql注入的拦截器


 /*
 * jFinal基础配置类 配置常量值
 * */
    public void configConstant(Constants me) {
        me.setDevMode(prop.getBoolean( "devMode",false ));//是否开发环境
        /**
         * 设置 Json 转换工厂实现类，目前支持：JFinalJsonFactory(默认)、JacksonFactory、FastJsonFactory
         * 分别支持 JFinalJson、Jackson、FastJson
         */
        me.setJsonFactory( MixedJsonFactory.me() ); //JFinalJson 与 FastJson 混合做 json 转换toJson 用 JFinalJson，parse 用 FastJson
        // 支持 Controller、Interceptor 之中使用 @Inject 注入业务层，并且自动实现 AOP
        me.setInjectDependency(true);
    }
   /**
   * 配置拆分路由
   *  路由拆分到 FrontRutes 与 AdminRoutes 之中配置的好处：
    * 1：可分别配置不同的 baseViewPath 与 Interceptor
    * 2：避免多人协同开发时，频繁修改此文件带来的版本冲突
    * 3：避免本文件中内容过多，拆分后可读性增强
    * 4：便于分模块管理路由
   *
   * */
    public void configRoute(Routes me) {
        me.add( new AdminRoutes() );
        me.add( new FrontRoutes() );



    }
    /**
     * 配置模板引擎，通常情况只需配置共享的模板函数
     */
    public void configEngine(Engine me) {
        //设置模板热加载
        me.setDevMode(prop.getBoolean("engineDevMode", false));
        //todo 配置模板指令



        // 添加角色、权限 shared method
        me.addSharedMethod(AdminAuthKit.class);

        me.addSharedFunction("/_view/common/_layout.html"); //布局属性
        me.addSharedFunction("/_view/common/_paginate.html"); //分页属性
    }

    /**
     * 抽取成独立的方法，便于 _Generator 中重用该方法，减少代码冗余
     */
    public static DruidPlugin getDruidPlugin() {
        return new DruidPlugin(prop.get("jdbcUrl"), prop.get("user"), prop.get("password").trim());
    }


    /**
     * 配置外部引擎 数据库等
     *
     * */
    public void configPlugin(Plugins me) {
        DruidPlugin druidPlugin = getDruidPlugin();
        wallFilter= new WallFilter();   //加强数据库安全
        druidPlugin.addFilter(wallFilter);
        druidPlugin.addFilter( new StatFilter() );//添加StatFiler 才会有统计数据
        me.add(druidPlugin);

        ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
        arp.setTransactionLevel( Connection.TRANSACTION_READ_COMMITTED );
        _MappingKit.mapping(arp); //添加自动生成的表映射
        // 强制指定复合主键的次序，避免不同的开发环境生成在 _MappingKit 中的复合主键次序不相同
        arp.setPrimaryKey("document", "mainMenu,subMenu");
        me.add( arp );

        arp.setShowSql(prop.getBoolean("devMode",false));
        arp.getEngine().setToClassPathSourceFactory();
        arp.addSqlTemplate("/sql/all_sqls.sql");
        me.add(new EhCachePlugin()); //缓存插件
        me.add(new Cron4jPlugin());
    }
    /**
     * 全局拦截器
     *
     * */
    public void configInterceptor(Interceptors me) {
       me.add(new LoginSessionInterceptor());

    }

    /**
     * 接管所有请求可以进行扩展
     * */
    public void configHandler(Handlers me) {
        me.add(DruidKit.getDruidStatViewHandler()); //Druid 页面统计功能
        me.add(new UrlSeoHandler()); //index、detail 两类 action 的 url seo


    }
    /**
     * 本方法会在Jfinal启动后调用
     * */
    public void afterJFinalStart(){
        //调用不带参的renderJson时,排除对loginAccount、remind 的json转换
        JsonRender.addExcludedAttrs(
                LoginService.loginAccountCacheName
        );

        //让druid 允许在sql中使用union
        // https://github.com/alibaba/druid/wiki/%E9%85%8D%E7%BD%AE-wallfilter
        wallFilter.getConfig().setSelectUnionCheck(false);
    }
}
