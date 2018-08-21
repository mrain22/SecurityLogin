package cn.mrian22.controller.error;

import org.springframework.boot.autoconfigure.web.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

/**
 * @author 22
 *自定义的全局错误处理Controller
 */
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class MyGlobalErrorController extends AbstractErrorController {

    public MyGlobalErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }


    /**
     * 默认的/error，可做成可配置的
     * @return
     */
    @Override
    public String getErrorPath() {
        return "/error";
    }



    @RequestMapping
    public String globalPage(ServletWebRequest webRequest,Model model){
        //调用父类方法获取错误信息
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest.getRequest(), true);
        model.addAttribute("errMessage",errorAttributes);
        return "/globalError";
    }


}
