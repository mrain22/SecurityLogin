package cn.mrian22.validate.common.processor;

import org.springframework.web.context.request.ServletWebRequest;

import java.io.IOException;

/**
 * @author 22
 * 校验码处理器，封装不同校验码的处理逻辑
 */
public interface ValidateCodeProcessor {
    String create(ServletWebRequest webRequest) throws IOException;
}
