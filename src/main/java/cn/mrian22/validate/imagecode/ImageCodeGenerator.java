package cn.mrian22.validate.imagecode;

import cn.mrian22.validate.common.ValidateCodeGenerator;
import cn.mrian22.validate.entity.ImageCode;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import java.awt.image.BufferedImage;

/**
 * @author 22
 */
@Component("imageCodeGenerator")
public class ImageCodeGenerator implements ValidateCodeGenerator {
    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @Override
    public ImageCode generator(ServletWebRequest webRequest) {
        //生产验证码字符串
        String code = defaultKaptcha.createText();
        //使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
        BufferedImage image = defaultKaptcha.createImage(code);
        //这里设置的是60s过期,可做成可配置
        ImageCode imageCode = new ImageCode(image,code,60);
        //保存到session中
        webRequest.getRequest().getSession().setAttribute("imageCode",imageCode);
        return imageCode ;
    }
}
