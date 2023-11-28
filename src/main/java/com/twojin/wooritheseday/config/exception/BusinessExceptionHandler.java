package com.twojin.wooritheseday.config.exception;

import com.twojin.wooritheseday.common.codes.ErrorCode;
import lombok.Builder;


/**
 * https://jaehoney.tistory.com/275
 */
public class BusinessExceptionHandler extends RuntimeException{
    private ErrorCode errorCode;
    private String message;

    @Builder
    public BusinessExceptionHandler(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    @Builder
    public BusinessExceptionHandler(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
