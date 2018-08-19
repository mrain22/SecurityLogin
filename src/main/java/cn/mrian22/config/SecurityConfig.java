package cn.mrian22.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author 22
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //表单登录
        http.formLogin()
                .loginPage("/login/userName")
                .loginProcessingUrl("/authentication/namepass")
                .defaultSuccessUrl("/login/sucess");
//        路径认证相关
        http.authorizeRequests()
                .antMatchers("/",
                        "/login/userName", "/login/mobile",
                        "/webjars/**","/authentication/namepass").permitAll()
                //其他地址的访问均需验证权限
                .anyRequest().authenticated();
//        记住我
        http.rememberMe();
//        注销登录
         //访问 /logout 表示用户注销，并清空session
        http.logout().logoutSuccessUrl("/");
//       关闭csrf
        http.csrf().disable();

        /**
         * "/webjars/**"下的所有请求都不拦截
         */
    }

    /**
     * 定义认证规则
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        super.configure(auth);
        //这里为了方便就直接将用户写到内存中，没有查数据库
        auth.inMemoryAuthentication().withUser("1").password("1").roles("user");

    }
}
