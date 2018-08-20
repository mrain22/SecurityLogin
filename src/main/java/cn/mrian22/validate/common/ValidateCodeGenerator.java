package cn.mrian22.validate.common;

import cn.mrian22.validate.entity.Code;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author 22
 * 校验码生成器接口
 */
public interface ValidateCodeGenerator {
    Code generator(ServletWebRequest webRequest);
}
