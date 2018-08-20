package cn.mrian22.validate.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 22
 * 自己写的授权失败处理器
 */
@Component("mrainAuthenticationFailureHandler")
public class MrainAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {


    /**
     * ObjectMapper这个类是java中jackson提供的，主要是用来把对象转换成为一个json字符串返回到前端,
     */
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
          //1、json形式返回
//        //服务器内部异常
//        response.setStatus(500);
//        //设置返回类型
//        response.setContentType("application/json;charset=UTF-8");
//        //将错误信息写入
//        response.getWriter().write(objectMapper.writeValueAsString(exception.getMessage()));
        //2、跳转到错误页面
        //将错误信息保存到session中
        request.getSession().setAttribute(MyWebAttributes.MY_EXCEPTION_SESSION,exception);
        //getRequestDispatcher使用的是post方式的请求
        request.getRequestDispatcher("/errorPage")
                .forward(request, response);
    }

}
