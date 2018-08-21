package cn.mrian22.config;

import cn.mrian22.validate.common.MrainAuthenticationFailureHandler;
import cn.mrian22.validate.common.ValidateCodeFilter;
import cn.mrian22.validate.smscode.authentication.config.SmsAuthenticationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author 22
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 自己写的验证错误处理器
     */
    @Autowired
    private MrainAuthenticationFailureHandler mrainAuthenticationFailureHandler;

    /**
     * 短信验证码验证配置
     */
    @Autowired
    private SmsAuthenticationConfig smsAuthenticationConfig;

    /**
     * 自己写的图片验证码过滤器
     */
    @Autowired
    private ValidateCodeFilter validateCodeFilter;



    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //将其加在UsernamePasswordAuthenticationFilter之前。
        http.addFilterBefore(validateCodeFilter,UsernamePasswordAuthenticationFilter.class);

        //将自己的短信验证码配置加进来
        http.apply(smsAuthenticationConfig);

        //表单登录
        http.formLogin()
                .loginPage("/login/userName")
                .loginProcessingUrl("/authentication/namepass")
                .failureHandler(mrainAuthenticationFailureHandler)
                .defaultSuccessUrl("/login/sucess");
        // 路径认证相关
        http.authorizeRequests()
                .antMatchers("/",
                        "/login/userName", "/login/mobile",
                        "/webjars/**","/authentication/*","/authentication/mobile","/code/*","/errorPage").permitAll()
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

//    /**
//     * 定义认证规则
//     * @param auth
//     * @throws Exception
//     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////        super.configure(auth);
//        //这里为了方便就直接将用户写到内存中，没有查数据库
//        auth.inMemoryAuthentication().withUser("1").password("1").roles("user");
//
//    }

    //     密码加盐加密
    @Bean
    public PasswordEncoder passwordEncoder (){
        //Spring自带的每次会随机生成盐值，即使密码相同，加密后也不同
        return  new BCryptPasswordEncoder();
    }
}
