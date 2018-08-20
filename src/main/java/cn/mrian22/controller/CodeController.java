package cn.mrian22.controller;

import cn.mrian22.validate.common.MyResponse;
import cn.mrian22.validate.common.processor.ValidateCodeProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.IOException;
import java.util.Map;

/**
 * @author 22
 */
@Controller
@RequestMapping("/code")
public class CodeController {


    /**
     *    @Autowired
     *    private ValidateCodeGenerator validateCodeGenerator;
     * 1、当Spring容器中有多个ValidateCodeGenerator的实现类时，spring不知道想要调用那个，需要注明调用的名称如下
     *    @Component("imageCodeGenerator")
     *    @Component("smsCodeGenerator")
     * 2、如果在注册时未标明名称，则以类名为名称且首字母小写。
     * 3、 @Qualifier("imageCodeGenerator")
     *     @Autowired
     *     private ValidateCodeGenerator validateCodeGenerator;
     *     用@Qualifier("imageCodeGenerator")标注用的那个实现类
     */
//    @Autowired
//    private ValidateCodeGenerator imageCodeGenerator;
//    @Autowired
//    private ValidateCodeGenerator smsCodeGenerator;
//
//    @Autowired
//    private SmsCodeSender smsCodeSender;
//
//    /**
//     * @param webRequest 封装了request和response
//     * @throws IOException
//     * 最终将图片用ImageIO以流的形式返回
//     */
//    @GetMapping("/image")
//    public void imageCode(ServletWebRequest webRequest) throws IOException {
//        //调用生成器 生成图片验证码
//        ImageCode imageCode = (ImageCode) imageCodeGenerator.generator(webRequest);
//        //以流的形式写回到前端
//        ImageIO.write(imageCode.getImage(),"JPEG",webRequest.getResponse().getOutputStream());
//    }
//    @GetMapping("/sms")
//    @ResponseBody
//    public MyResponse smsCode(@RequestParam String mobile, ServletWebRequest webRequest){
//        //调用生成器，生成短信验证码
//        SmsCode smsCode = (SmsCode) smsCodeGenerator.generator(webRequest);
//        //调用发送器，将短信验证码发出
//        smsCodeSender.send(mobile,smsCode.getCode());
//        return new MyResponse("验证码发送成功！");
//    }

    //加工后的代码
    /**
     * 依赖查找
     * 收集系统中所有的 {@link ValidateCodeProcessor} 接口的实现。
     * 并以其名字作为key值
     */
    @Autowired
    private Map<String,ValidateCodeProcessor> validateCodeProcessorMap;

    @GetMapping("/{type}")
    @ResponseBody
    public MyResponse processor(@PathVariable String type, ServletWebRequest webRequest) throws IOException {
        String message = validateCodeProcessorMap.get(type + "CodeProcessor").create(webRequest);
        return new MyResponse(message);
    }
}
