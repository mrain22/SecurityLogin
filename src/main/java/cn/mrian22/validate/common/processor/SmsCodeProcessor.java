package cn.mrian22.validate.common.processor;

import cn.mrian22.validate.entity.SmsCode;
import cn.mrian22.validate.smscode.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

@Component("smsCodeProcessor")
public class SmsCodeProcessor extends AbstractValidateCodeProcessor<SmsCode> {

    /**
     * 短信验证码发送器
     */
    @Autowired
    private SmsCodeSender smsCodeSender;

    @Override
    protected String send(ServletWebRequest request, SmsCode validateCode) {
        String mobile = request.getParameter("mobile");
        smsCodeSender.send(mobile,validateCode.getCode());
        return "短信验证码发送完成，请注意查收！";
    }
}
