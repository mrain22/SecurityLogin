package cn.mrian22.validate.imagecode;

import cn.mrian22.validate.common.ValidateCodeException;
import cn.mrian22.validate.entity.ImageCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
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
public class ImageCodeFilter extends OncePerRequestFilter {
    private AuthenticationFailureHandler failureHandler;
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        if(StringUtils.equals("/authentication/namepass",httpServletRequest.getRequestURI())
                && StringUtils.equalsIgnoreCase(httpServletRequest.getMethod(),"post")){
            try {
                validate(new ServletWebRequest(httpServletRequest));
            } catch (ValidateCodeException e) {
                //调用自己定义的登录失败处理器进行处理。这里需要在SecurityConfig里set进来。
                failureHandler.onAuthenticationFailure(httpServletRequest,httpServletResponse,e);
                //出错返回，不向下执行
                return;
            }
        }
        //如果不符合以上的要求或验证通过，则调用下一个过滤器
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

    private void validate(ServletWebRequest webRequest) throws ServletRequestBindingException {
        //拿到session.保存的时候是以ImageCode的格式保存的
        ImageCode imageCode = (ImageCode) webRequest.getRequest().getSession().getAttribute("imageCode");
        //获取请求里的验证码，获取前台传过来的验证码
        String requestCode = ServletRequestUtils.getStringParameter(webRequest.getRequest(), "imageCode");
        // 清理验证码session
        webRequest.getRequest().getSession().removeAttribute("imageCode");

        if (StringUtils.isBlank(requestCode)){
            throw new ValidateCodeException("验证码不能为空");
        }
        if (imageCode == null){
            throw new ValidateCodeException("验证码不存在");
        }
        if (imageCode.isExpried()){
            throw new ValidateCodeException("验证码已过期！");
        }
        if (!(StringUtils.equalsIgnoreCase(imageCode.getCode(),requestCode))){
            throw new ValidateCodeException("验证码错误");
        }
    }

    public AuthenticationFailureHandler getFailureHandler() {
        return failureHandler;
    }

    public void setFailureHandler(AuthenticationFailureHandler failureHandler) {
        this.failureHandler = failureHandler;
    }
}
