package cn.mrian22.validate.smscode;

/**
 * @author 22
 * 写这个接口方便写不同的发送器逻辑
 */
public interface SmsCodeSender {
    void send(String mobile,String code);
}
