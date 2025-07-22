package org.example.user.manage.common.exception;

import lombok.Getter;

@Getter
public class BusinessException extends BaseException{

    private ExceptionEnum exceptionEnum;


    public BusinessException(ExceptionEnum exceptionEnum) {
        this.exceptionEnum = exceptionEnum;
    }

    public BusinessException(String msg, ExceptionEnum exceptionEnum) {
        super(msg);
        this.exceptionEnum = exceptionEnum;
    }
}
