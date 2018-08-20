package cn.mrian22.validate.common.processor;

import cn.mrian22.validate.common.MyWebAttributes;
import cn.mrian22.validate.common.ValidateCodeGenerator;
import cn.mrian22.validate.entity.Code;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.IOException;
import java.util.Map;

/**
 * @author 22
 */
public abstract class AbstractValidateCodeProcessor<C extends Code> implements ValidateCodeProcessor {

    /**
     * 依赖查找
     * 收集系统中所有的 {@link ValidateCodeGenerator} 接口的实现。
     * 并以其名字作为key值
     */
    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGenerators;

    @Override
    public String create(ServletWebRequest webRequest) throws IOException {
        C validateCode = generate(webRequest);
//        将校验码保存到session中
        save(webRequest,validateCode);
//        返回前端或发送到手机
        String sendMessage = send(webRequest, validateCode);
        return sendMessage;
    }

    /**
     * 生成校验码
     */
    private C generate(ServletWebRequest servletWebRequest){
        String type = getProcessorType(servletWebRequest);
        ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(type+"CodeGenerator");
        C c = (C) validateCodeGenerator.generator(servletWebRequest);
        return c;
    }

    /**
     * 保存校验码
     */
    private void save(ServletWebRequest webRequest,C validateCode){
        webRequest.getRequest().getSession().setAttribute(MyWebAttributes.MY_CODE_SESSION,validateCode);
    }

    /**
     *发送校验码，由子类完成
     * 抽象方法。
     */
    protected  abstract String send(ServletWebRequest request,C validateCode) throws IOException;

    /**
     *根据请求的Url获取校验码的类型
     */
    private String getProcessorType(ServletWebRequest request){
        //得到URL字符串中/code/后面的字符。
        return StringUtils.substringAfter(request.getRequest().getRequestURI(),"/code/");
    }
}
