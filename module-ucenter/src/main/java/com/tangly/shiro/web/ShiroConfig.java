package com.tangly.shiro.web;//package com.tangly.config.shiro.web;
//
//import com.tangly.config.cache.ShiroRedisCacheManager;
//import com.tangly.config.shiro.jwt.JWTFilter;
//import com.tangly.config.shiro.web.RetryLimitHashedCredentialsMatcher;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.shiro.mgt.SecurityManager;
//import org.apache.shiro.spring.LifecycleBeanPostProcessor;
//import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
//import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
//import org.apache.shiro.web.mgt.CookieRememberMeManager;
//import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
//import org.apache.shiro.web.servlet.SimpleCookie;
//import org.crazycake.shiro.RedisCacheManager;
//import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.DependsOn;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.redis.core.RedisTemplate;
//
//import javax.servlet.Filter;
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//@Configuration
//@Slf4j
//public class ShiroConfig {
//
//
//    @Bean("shiroFilter")
//    public ShiroFilterFactoryBean factory(SecurityManager securityManager) {
//        System.out.println("ShiroConfiguration.shirFilter()");
//        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
//
//        // 添加自己的过滤器并且取名为jwt
//        Map<String, Filter> filterMap = new HashMap<>();
//        filterMap.put("jwt", new JWTFilter());
////        filterMap.put("web",new MyFormAuthenticationFilter());
//        shiroFilterFactoryBean.setFilters(filterMap);
//
//        shiroFilterFactoryBean.setSecurityManager(securityManager);
//
//        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
//        shiroFilterFactoryBean.setLoginUrl("/login");
//        // 登录成功后要跳转的链接
//        shiroFilterFactoryBean.setSuccessUrl("/index");
//
//        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
//        //配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
//        filterChainDefinitionMap.put("/logout", "logout");
//        //app的登录接口不要加权限验证
//        filterChainDefinitionMap.put("/jwtLogin", "anon");
//        //        filterChainDefinitionMap.put("/ajaxLogin", "anon");
//
//        //开放的静态资源
//        filterChainDefinitionMap.put("/attach/**", "anon");
//        filterChainDefinitionMap.put("/favicon.ico", "anon");//网站图标
//        filterChainDefinitionMap.put("/webjars", "anon");
//        filterChainDefinitionMap.put("/static/**", "anon");
//
//        //配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
//
//        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
//        filterChainDefinitionMap.put("/**", "jwt");
//
//
//        //未授权界面;
//        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
//
//        return shiroFilterFactoryBean;
//    }
//
//    @Bean
//    public DefaultWebSecurityManager securityManager(
//            WebAuthRealm webAuthRealm,
//            RedisCacheManager redisCacheManager
//            ) {
//        log.info("--------------shiro已经加载----------------");
//
//
//        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
//        securityManager.setRealm(webAuthRealm);
//
//        //注入缓存管理器;
//        //注意:开发时请先关闭，如不关闭热启动会报错
//        securityManager.setCacheManager(redisCacheManager);
//        securityManager.setRememberMeManager(rememberMeManager());
//        return securityManager;
//    }
//
//
//    /**
//     * cookie管理对象;
//     *
//     * @return
//     */
//    @Bean
//    public CookieRememberMeManager rememberMeManager() {
//        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
//        cookieRememberMeManager.setCookie(rememberMeCookie());
//
//        log.info("--------------rememberMeManager init---------------" + cookieRememberMeManager);
//        return cookieRememberMeManager;
//    }
//
//    /**
//     * cookie对象;
//     *
//     * @return
//     */
//    @Bean
//    public SimpleCookie rememberMeCookie() {
//        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
//        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
//        //<!-- 记住我cookie生效时间30天 ,单位秒;-->
//        simpleCookie.setMaxAge(259200);
//        log.info("--------------rememberMeCookie init---------------" + simpleCookie);
//        return simpleCookie;
//    }
//
//
//    @Bean
//    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(
//            @Qualifier("securityManager")SecurityManager securityManager
//    ) {
//        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
//        aasa.setSecurityManager(securityManager);
//        return new AuthorizationAttributeSourceAdvisor();
//    }
//
//    @Bean
//    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
//        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
//        daap.setProxyTargetClass(true);
//        return daap;
//    }
//
//    @Bean(name = "shiroRedisCacheManager")
//    @Primary
//    public ShiroRedisCacheManager shiroRedisCacheManager(
//            @Qualifier("redisTemplate") RedisTemplate redisTemplate) {
//        log.info("--------------redis cache init---------------");
//        ShiroRedisCacheManager slifeRedisCacheManager = new ShiroRedisCacheManager();
//        slifeRedisCacheManager.setRedisTemplate(redisTemplate);
//        log.info("--------------redis cache ---------------" + slifeRedisCacheManager);
//        return slifeRedisCacheManager;
//    }
//
//    @Bean
//    @Primary
//    public WebAuthRealm webAuthRealm(@Qualifier("shiroRedisCacheManager") ShiroRedisCacheManager shiroRedisCacheManager,
//                                    @Qualifier("retryLimitHashedCredentialsMatcher") RetryLimitHashedCredentialsMatcher retryLimitHashedCredentialsMatcher) {
//        WebAuthRealm webAuthRealm = new WebAuthRealm();
//        webAuthRealm.setCacheManager(shiroRedisCacheManager);
//
//        // 散列算法 和 散列次数 同PasswordHelper中的配置
//        retryLimitHashedCredentialsMatcher.setHashAlgorithmName("md5");
//        retryLimitHashedCredentialsMatcher.setHashIterations(2);
//        webAuthRealm.setCredentialsMatcher(retryLimitHashedCredentialsMatcher);
//        return webAuthRealm;
//    }
//
//    @Bean
//    public SimpleCookie sessionIdCookie() {
//        SimpleCookie sessionIdCookie = new SimpleCookie();
//        sessionIdCookie.setHttpOnly(true);
//        sessionIdCookie.setMaxAge(-1);
//        return sessionIdCookie;
//    }
//
//
//    /**
//     * AOP式方法级权限检查
//     */
//    @Bean
//    @DependsOn("lifecycleBeanPostProcessor")
//    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
//        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
//        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
//        return defaultAdvisorAutoProxyCreator;
//    }
//
//    @Bean
//    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
//        return new LifecycleBeanPostProcessor();
//    }
//
//    @Bean
//    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
//        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
//        advisor.setSecurityManager(securityManager);
//        return advisor;
//    }
//
//}