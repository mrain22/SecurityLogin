package cn.mrian22.validate.smscode.authentication;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author 22
 */
public class SmsAuthenticationProvider implements AuthenticationProvider {

    /**
     * 来获取用户信息，生成get/set方法
     */
    private UserDetailsService userDetailsService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsAuthenticationToken authenticationToken = (SmsAuthenticationToken) authentication;
        UserDetails userDetails = userDetailsService.loadUserByUsername((String) authenticationToken.getPrincipal());

        if (userDetails == null){
            throw new InternalAuthenticationServiceException("无法查到该用户！");
        }
//        如果读到用户信息，给SmsAuthenticationToken加上认证标志.
        /*
         *userDetails,用户信息
         * userDetails.getAuthorities() 用户权限
         * */
        SmsAuthenticationToken authenticationTokenResult = new SmsAuthenticationToken(userDetails,userDetails.getAuthorities());
//        将未认证的Token的Details  set到已认证的Token中去
        authenticationTokenResult.setDetails(authenticationToken.getDetails());

        return authenticationTokenResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
//        此处制定了支持处理那个Token。
        return SmsAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
