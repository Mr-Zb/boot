package com.base.boot.common.exception;

import lombok.Data;

@Data
public class JwtException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String msg;
    private int code = 500;

    public JwtException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}