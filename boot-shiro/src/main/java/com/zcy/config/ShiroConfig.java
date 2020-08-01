package com.zcy.config;

import com.zcy.shiro.CustomShiroRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    /**
     * 自定义校验
     * @return
     */
    @Bean
    public CustomShiroRealm customShiroRealm() {
        return new CustomShiroRealm();
    }

    /**
     * 权限管理，配置主要是Realm的管理认证
     *
     * @return
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(customShiroRealm());
        return manager;
    }

    /**
     * Filter工厂，设置对应的过滤条件和跳转条件
     *
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager());

        Map<String, String> filtersMap = new HashMap<>();
        // 登出
        filtersMap.put("/logout", "logout");
        // swagger
        filtersMap.put("/swagger**/**", "anon");
        filtersMap.put("/webjars/**", "anon");
        filtersMap.put("/v2/**", "anon");
        // 对所有用户认证
        filtersMap.put("/**", "authc");
        // 登录
        factoryBean.setLoginUrl("/login");
        // 首页
        factoryBean.setSuccessUrl("/index");
        // 错误页面，认证不通过跳转
        factoryBean.setUnauthorizedUrl("/error");
        factoryBean.setFilterChainDefinitionMap(filtersMap);
        return factoryBean;
    }

    /**
     * 加入注解的使用，不加入这个注解不生效
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor attributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor attributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        attributeSourceAdvisor.setSecurityManager(securityManager());
        return attributeSourceAdvisor;
    }

}
