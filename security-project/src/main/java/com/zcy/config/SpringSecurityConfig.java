package com.zcy.config;

import com.zcy.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity//开启security服务
@EnableGlobalMethodSecurity(prePostEnabled = true)//开启全局security服务
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private DataSource dataSource;

    @Override
    public void configure(WebSecurity web) throws Exception {
        /**
         * 直接过滤掉该地址，即该地址不走 Spring Security 过滤器链
         */
        web.ignoring().antMatchers("/hello")
                // 设置拦截忽略文件夹，可以对静态资源放行
                .antMatchers("/css/**", "/js/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 如果有匿名的url填在下面
                .anyRequest().authenticated()
                .and()
                // 设置登录界面
                .formLogin().loginPage("/login")
                // 设置登录成功跳转界面
                .defaultSuccessUrl("/").permitAll()
                // 登陆失败url
                .failureUrl("/login/error")
                .and()
                // 退出登录
                .logout().permitAll()
                // 记住我/自动登录，自动在 Cookie 中保存一个名为 remember-me 的cookie，默认有效期为2周，其值是一个加密字符串
                .and().rememberMe()
                .tokenRepository(persistentTokenRepository())
                // 有效时间：单位s
                .tokenValiditySeconds(60)
                .userDetailsService(userDetailsService)
        ;


        // 关闭CDRF跨域
        http.csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().withUser("root").roles("root").password("$2a$10$kitT0M97FUL0lX2cpefUKuj3zIGLeSZNHE1ANLbK7zvTN8CRHCB/O");//123456
        auth.userDetailsService(userDetailsService)
//                .passwordEncoder(new PasswordEncoder() {
//                    @Override
//                    public String encode(CharSequence charSequence) {
//                        return charSequence.toString();
//                    }
//
//                    @Override
//                    public boolean matches(CharSequence charSequence, String s) {
//                        return s.equals(charSequence.toString());
//                    }
//                });
                .passwordEncoder(new BCryptPasswordEncoder()); // 加密
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        // 如果token表不存在，使用下面语句可以初始化该表；若存在，请注释掉这条语句，否则会报错。persistent_logins表
        //tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }
}
