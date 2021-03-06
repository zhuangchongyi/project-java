package com.zcy.security.config;

import com.zcy.security.handler.CustomAuthenticationFailureHandler;
import com.zcy.security.handler.CustomAuthenticationSuccessHandler;
import com.zcy.security.handler.CustomLogoutSuccessHandler;
import com.zcy.security.permission.CustomPermissionEvaluator;
import com.zcy.security.provider.CustomAuthenticationProvider;
import com.zcy.security.service.CustomUserDetailsService;
import com.zcy.security.strategy.CustomExpiredSessionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity//开启security服务
@EnableGlobalMethodSecurity(prePostEnabled = true)//开启全局security服务
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> authenticationDetailsSource;
    @Autowired
    private CustomAuthenticationProvider authenticationProvider;
    @Autowired
    private CustomAuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private CustomAuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private CustomLogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Override
    public void configure(WebSecurity web) throws Exception {
        /**
         * 直接过滤掉该地址，即该地址不走 Spring Security 过滤器链
         */
        // 设置拦截忽略文件夹，可以对静态资源放行
        web.ignoring().antMatchers("/css/**", "/js/**");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.apply(smsCodeAuthenticationSecurityConfig) // 添加短信验证登录
                .and().authorizeRequests()
                // 如果有允许匿名的url，填在下面
                .antMatchers("/api/**").permitAll()
                .antMatchers("/sms/code").permitAll()
                .antMatchers("/getVerifyCode").permitAll()
                .antMatchers("/login/invalid").permitAll()
                .anyRequest().authenticated()
                .and()
                // 设置登录界面
                .formLogin().loginPage("/login")
                // 登陆失败url
                //.failureUrl("/login/error")
                // 登录成功url
                //.defaultSuccessUrl("/")
                //当设置successHandler()和failureHandler()来实现自定义认证成功、失败处理后，需要去除 failureUrl()和defaultSuccessUrl()的设置，否则无法生效。
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .permitAll()
                // 指定authenticationDetailsSource
                .authenticationDetailsSource(authenticationDetailsSource)
                .and()
                // 添加验证码过滤器
                //.addFilterBefore(new VerifyCodeFilter(), UsernamePasswordAuthenticationFilter.class)
                // 退出登录
                .logout()
                .logoutUrl("/signout")//默认的退出 Url 是 /logout
                .deleteCookies("JSESSIONID") //配置当退出时清除浏览器的 Cookie
                .logoutSuccessHandler(logoutSuccessHandler)//配置退出后处理的逻辑
                .permitAll()
                // 记住我/自动登录，自动在 Cookie 中保存一个名为 remember-me 的cookie，默认有效期为2周，其值是一个加密字符串
                //.and()
                //.rememberMe()
                //.tokenRepository(persistentTokenRepository())
                // 有效时间：单位s
                //.tokenValiditySeconds(60)
                //.userDetailsService(userDetailsService)
                //session超时处理方式: invalidSessionStrategy()和invalidSessionUrl() 二选一
                .and()
                .sessionManagement()
                // 指定跳转url
                .invalidSessionUrl("/login/invalid")
                //指定最大登录数
                .maximumSessions(1)
                // 当达到最大值时,是否保留已经登录的用户；为true，新用户无法登录；为 false，旧用户被踢出
                .maxSessionsPreventsLogin(false)
                // 当达到最大值时,旧用户被踢出后处理方法
                .expiredSessionStrategy(new CustomExpiredSessionStrategy())
                .sessionRegistry(sessionRegistry())
        ;


        // 关闭CDRF跨域
        http.csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //自定义安全校验
        auth.authenticationProvider(authenticationProvider);

        // 默认用户
        //auth.inMemoryAuthentication().withUser("root").roles("root").password("$2a$10$kitT0M97FUL0lX2cpefUKuj3zIGLeSZNHE1ANLbK7zvTN8CRHCB/O");//123456
//        auth.userDetailsService(userDetailsService)
//                .passwordEncoder(new BCryptPasswordEncoder()); // 加密

        //处理不抛出UsernameNotFoundException异常
//        auth.authenticationProvider(authenticationProvider());
    }

    /**
     * 默认不实现AuthenticationProvider时,无法抛出 UsernameNotFoundException
     * 走DaoAuthenticationProvider
     * 出异常AbstractUserDetailsAuthenticationProvider.hideUserNotFoundExceptions为true时会包装为BadCredentialsException异常
     * @return
     */
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setHideUserNotFoundExceptions(false);
//        provider.setUserDetailsService(userDetailsService);
//        provider.setPasswordEncoder(new BCryptPasswordEncoder());
//        return provider;
//    }

    /**
     * token持久化至数据库
     *
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        // 如果token表不存在，使用下面语句可以初始化该表；若存在，请注释掉这条语句，否则会报错。persistent_logins表
        //tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

    /**
     * 自定义PermissionEvaluator权限控制
     *
     * @return
     */
    @Bean
    public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setPermissionEvaluator(new CustomPermissionEvaluator());
        return handler;
    }

    /**
     * 注入SessionRegistry
     *
     * @return
     */
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
}
