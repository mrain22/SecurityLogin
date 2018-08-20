package cn.mrian22.validate.smscode.authentication.config;

import cn.mrian22.validate.smscode.authentication.SmsAuthenticationFilter;
import cn.mrian22.validate.smscode.authentication.SmsAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * @author 22
 * 写完后需要在SecurityConfig中添加
 */
@Component
public class SmsAuthenticationConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain,HttpSecurity> {
    @Autowired
    private AuthenticationFailureHandler mrainAuthenticationFailureHandler;


    @Autowired
    private UserDetailsService userLoginService;
    @Override
    public void configure(HttpSecurity http) throws Exception {
        //配置过滤器
        SmsAuthenticationFilter smsAuthenticationFilter = new SmsAuthenticationFilter();
        //set进去AuthenticationManager
        smsAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        //将自己的错误处理器set进去
        smsAuthenticationFilter.setAuthenticationFailureHandler(mrainAuthenticationFailureHandler);
        //成功处理器，这里传入的默认的
        smsAuthenticationFilter.setAuthenticationSuccessHandler(new SavedRequestAwareAuthenticationSuccessHandler());


        //配置Provider
        SmsAuthenticationProvider smsAuthenticationProvider = new SmsAuthenticationProvider();
        //set进自己的UserDetailsService用于用户认证
        smsAuthenticationProvider.setUserDetailsService(userLoginService);

        //将自己写的Provider和Filter添加Security中
        http.authenticationProvider(smsAuthenticationProvider)
                .addFilterAfter(smsAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);

    }
}
