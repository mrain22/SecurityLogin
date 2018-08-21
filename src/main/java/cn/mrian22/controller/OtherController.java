package cn.mrian22.controller;

import cn.mrian22.validate.common.MyWebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class OtherController {

    @PostMapping("/errorPage")
    public String errorPage(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Model model) {
        //session中拿到错误信息
        model.addAttribute("errMessage", httpServletRequest.getSession().getAttribute(MyWebAttributes.MY_EXCEPTION_SESSION));
        return "error";
    }
}
