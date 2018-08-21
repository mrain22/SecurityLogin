package cn.mrian22.validate.common;

import cn.mrian22.validate.entity.Code;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 22
 * 写完该类，需要将此类加到SecurityConfig配置类中。
 */
@Component("validateCodeFilter")
public class ValidateCodeFilter extends OncePerRequestFilter {

    @Autowired
    private AuthenticationFailureHandler mrainAuthenticationFailureHandler;
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        //所有要拦截的需要验证码验证的路径，可以设置成可配置的，这里就简单写一下
        String[] urls = {"/authentication/namepass","/authentication/mobile"};
        boolean isTrue = false;
        for (String url : urls){
            //判断当前路径是不是需要验证码验证的路径
            if (StringUtils.equals(url,httpServletRequest.getRequestURI())){
                isTrue = true;
                break;
            }
        }
        if(isTrue){
            try {
                validate(new ServletWebRequest(httpServletRequest));
            } catch (ValidateCodeException e) {
                //调用自己定义的登录失败处理器进行处理。
                mrainAuthenticationFailureHandler.onAuthenticationFailure(httpServletRequest,httpServletResponse,e);
                //出错返回，不向下执行
                return;
            }
        }
        //如果不符合以上的要求或验证通过，则调用下一个过滤器
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

    private void validate(ServletWebRequest webRequest) throws ServletRequestBindingException {
        //拿到session.保存的时候是以Code的格式保存的
        //在这里不论是图片验证码还是短息验证码都是Code的子类，这里将保存在session的强转成Code，用里面的是否过期。
        Code code = (Code) webRequest.getRequest().getSession().getAttribute(MyWebAttributes.MY_CODE_SESSION);
        //获取请求里的验证码，获取前台传过来的验证码
        String requestCode = ServletRequestUtils.getStringParameter(webRequest.getRequest(), "code");
        // 清理验证码session
        webRequest.getRequest().getSession().removeAttribute(MyWebAttributes.MY_CODE_SESSION);

        if (StringUtils.isBlank(requestCode)){
            throw new ValidateCodeException("验证码不能为空");
        }
        if (code == null){
            throw new ValidateCodeException("验证码不存在");
        }
        if (code.isExpried()){
            throw new ValidateCodeException("验证码已过期！");
        }
        if (!(StringUtils.equalsIgnoreCase(code.getCode(),requestCode))){
            throw new ValidateCodeException("验证码错误");
        }
    }
}
