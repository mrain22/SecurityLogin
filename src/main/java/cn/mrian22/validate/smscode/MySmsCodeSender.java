package cn.mrian22.validate.smscode;

import org.springframework.stereotype.Component;

/**
 * @author 22
 */
@Component("mySmsCodeSender")
public class MySmsCodeSender implements SmsCodeSender {
    @Override
    public void send(String mobile, String code) {
        System.out.println("向手机号："+mobile+"，发送的验证码是："+code);
    }
}
