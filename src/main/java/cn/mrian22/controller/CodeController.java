package cn.mrian22.controller;

import cn.mrian22.validate.common.ValidateCodeGenerator;
import cn.mrian22.validate.entity.ImageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * @author 22
 */
@Controller
@RequestMapping("/code")
public class CodeController {

    @Autowired
    private ValidateCodeGenerator validateCodeGenerator;

    /**
     * @param webRequest 封装了request和response
     * @throws IOException
     * 最终将图片用ImageIO以流的形式返回
     */
    @GetMapping("/image")
    public void imageCode(ServletWebRequest webRequest) throws IOException {
        ImageCode imageCode = (ImageCode) validateCodeGenerator.generator(webRequest);
        ImageIO.write(imageCode.getImage(),"JPEG",webRequest.getResponse().getOutputStream());
    }


}
