package cn.mrian22.validate.entity;

import java.time.LocalDateTime;

/**
 * @author 22
 * 短信验证码与父类一直，可不重新写实体类
 */
public class SmsCode extends Code {

    public SmsCode(String code, int expireIn) {
        super(code, expireIn);
    }
    public SmsCode(String code, LocalDateTime expireTime) {
        super(code, expireTime);
    }
}
