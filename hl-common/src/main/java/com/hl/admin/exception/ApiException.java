package com.hl.admin.exception;


import com.hl.admin.result.IErrorCode;

/**
 * 自定义api异常
 */
public class ApiException extends RuntimeException {

    private IErrorCode errorCode;

    public ApiException(IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ApiException(String message) {
        super(message);
    }

    public IErrorCode getErrorCode() {
        return errorCode;
    }

}
