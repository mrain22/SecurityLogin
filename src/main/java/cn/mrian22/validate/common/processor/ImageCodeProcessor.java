package cn.mrian22.validate.common.processor;

import cn.mrian22.validate.entity.ImageCode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import java.io.IOException;

@Component("imageCodeProcessor")
public class ImageCodeProcessor extends AbstractValidateCodeProcessor<ImageCode> {
    @Override
    protected String send(ServletWebRequest request, ImageCode validateCode) throws IOException {
        //以流的形式写回到前端
        ImageIO.write(validateCode.getImage(),"JPEG",request.getResponse().getOutputStream());
        return "图片刷新完成！";
    }
}
