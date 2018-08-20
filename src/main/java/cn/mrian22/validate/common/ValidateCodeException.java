package cn.mrian22.validate.common;

import org.springframework.security.core.AuthenticationException;

/**
 * @author 22
 */
public class ValidateCodeException  extends AuthenticationException {
    public ValidateCodeException(String msg) {
        super(msg);
    }
}
