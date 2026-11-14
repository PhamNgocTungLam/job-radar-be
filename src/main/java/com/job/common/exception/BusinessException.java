package com.job.common.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;
    private final List<String> details;

    public BusinessException(ErrorCode errorCode) {
        this(errorCode, errorCode.getDefaultMessage(), List.of());
    }

    public BusinessException(ErrorCode errorCode, String message) {
        this(errorCode, message, List.of());
    }

    public BusinessException(ErrorCode errorCode, String message, List<String> details) {
        super(message);
        this.errorCode = errorCode;
        this.details = details;
    }
}
