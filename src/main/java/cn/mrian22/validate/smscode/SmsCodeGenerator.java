package cn.mrian22.validate.smscode;

import cn.mrian22.validate.common.ValidateCodeGenerator;
import cn.mrian22.validate.entity.SmsCode;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author 22
 */
@Component("smsCodeGenerator")
public class SmsCodeGenerator implements ValidateCodeGenerator {
    @Override
    public SmsCode generator(ServletWebRequest webRequest) {
        // 生成6位短信验证码
        String code = RandomStringUtils.randomNumeric(6);
        //这里设置的是60s过期,可做成可配置
        SmsCode smsCode = new SmsCode(code,60);
//        //保存到session中
//        webRequest.getRequest().getSession().setAttribute("smsCode",smsCode);
        return smsCode;
    }
}
