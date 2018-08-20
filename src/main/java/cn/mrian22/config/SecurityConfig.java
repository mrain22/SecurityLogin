package cn.mrian22.config;

import cn.mrian22.validate.common.MrainAuthenticationFailureHandler;
import cn.mrian22.validate.imagecode.ImageCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author 22
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MrainAuthenticationFailureHandler mrainAuthenticationFailureHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 自己写的图片验证码过滤器
        ImageCodeFilter imageCodeFilter = new ImageCodeFilter();
        //将自己的错误处理set进去。
        imageCodeFilter.setFailureHandler(mrainAuthenticationFailureHandler);

        //将其加在UsernamePasswordAuthenticationFilter之前。
        http.addFilterBefore(imageCodeFilter,UsernamePasswordAuthenticationFilter.class);
        //表单登录
        http.formLogin()
                .loginPage("/login/userName")
                .loginProcessingUrl("/authentication/namepass")
                .defaultSuccessUrl("/login/sucess");
        // 路径认证相关
        http.authorizeRequests()
                .antMatchers("/",
                        "/login/userName", "/login/mobile",
                        "/webjars/**","/authentication/namepass","/code/*","/errorPage").permitAll()
                //其他地址的访问均需验证权限
                .anyRequest().authenticated();
        //记住我，这里只是security默认的记住我，感兴趣的可以将数据保存到数据库的记住我。
        http.rememberMe();
        //注销登录
        //访问 /logout 表示用户注销，并清空session
        http.logout().logoutSuccessUrl("/");
        // 关闭csrf
        http.csrf().disable();
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
