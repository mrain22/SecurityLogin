package cn.mrian22.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 22
 */
@Controller
public class LoginController {

    @RequestMapping("/")
    public String mainPage(){
        return "index";
    }
    @RequestMapping("/login/userName")
    public String userNamePage(){
        return "login/usernamepassword";
    }
    @RequestMapping("/login/mobile")
    public String mobilePage(){
        return "login/mobile";
    }
    @RequestMapping("/login/sucess")
    public String sucessPage(){
        return "login/sucess";
    }
    @RequestMapping("/login/test")
    public String testPage(){
        return "login/test";
    }

}
