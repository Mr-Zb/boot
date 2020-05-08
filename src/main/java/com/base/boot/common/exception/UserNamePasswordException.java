package com.base.boot.common.exception;

import org.springframework.security.core.AuthenticationException;

import java.io.Serializable;

public class UserNamePasswordException extends AuthenticationException implements Serializable {
    private static final long serialVersionUID = 1L;

    public UserNamePasswordException(String message) {
        super(message);
    }

}
