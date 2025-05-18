package ve312.com.filoseries.config;

import org.springframework.security.core.AuthenticationException;

public class InactiveUserException extends AuthenticationException {
    public InactiveUserException(String msg) {
        super(msg);
    }
}
