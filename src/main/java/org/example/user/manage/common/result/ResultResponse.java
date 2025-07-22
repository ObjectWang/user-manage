package org.example.user.manage.common.result;




import org.example.user.manage.common.exception.ExceptionEnum;

import java.io.Serializable;

public class ResultResponse<T> implements Serializable {

    private static final long serialVersionUID=1L;

    private String code;
    private String message;
    private T data;

    public static <T> ResultResponse<T> success(){
        return  success(null);
    }

    public static <T> ResultResponse<T> success(T data){
        ResultResponse<T> rr=new ResultResponse<>();
        rr.setCode(ExceptionEnum.SUCCESS.getResultCode());
        rr.setMessage(ExceptionEnum.SUCCESS.getResultMsg());
        rr.setData(data);
        return rr;
    }

    public static <T> ResultResponse<T> success(String message,T data){
        ResultResponse<T> rr=new ResultResponse<>();
        rr.setCode(ExceptionEnum.SUCCESS.getResultCode());
        rr.setMessage(message);
        rr.setData(data);
        return rr;
    }

    public static <T> ResultResponse<T> success(String code,String message,T data){
        ResultResponse<T> rr=new ResultResponse<>();
        rr.setCode(code);
        rr.setMessage(message);
        rr.setData(data);
        return rr;
    }

    public static <T> ResultResponse<T> error(String code,String message){
        ResultResponse<T> rr=new ResultResponse<>();
        rr.setCode(code);
        rr.setMessage(message);
        rr.setData(null);
        return rr;
    }

    public static <T> ResultResponse<T> error(String message){
        ResultResponse<T> rr=new ResultResponse<>();
        rr.setCode("-1");
        rr.setMessage(message);
        rr.setData(null);
        return rr;
    }

    public static <T> ResultResponse<T> error(String code,String message, T data){
        ResultResponse<T> rr=new ResultResponse<>();
        rr.setCode(code);
        rr.setMessage(message);
        rr.setData(data);
        return rr;
    }

    public static <T> ResultResponse<T> error(ExceptionEnum exceptionEnum){
        ResultResponse<T> rr=new ResultResponse<>();
        rr.setCode(exceptionEnum.getResultCode());
        rr.setMessage(exceptionEnum.getResultMsg());
        return rr;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
