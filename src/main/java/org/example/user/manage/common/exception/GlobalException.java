package org.example.user.manage.common.exception;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.example.user.manage.common.result.ResultResponse;
import org.hibernate.validator.internal.engine.path.NodeImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.websocket.SessionException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalException {

    /**
     * 业务异常
     *
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public ResultResponse handleBusinessException(BusinessException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址:{}, 发生业务异常: ", requestURI, e);
        return ResultResponse.error(e.getExceptionEnum().getResultCode(), e.getExceptionEnum().getResultMsg());
    }

    /**
     * 认证异常
     *
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(AuthorizeException.class)
    public ResultResponse handleAuthorizeException(AuthorizeException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址:{}, 发生认证异常: ", requestURI, e);
        return ResultResponse.error(ExceptionEnum.AUTHENTICATION_FAILED);
    }

    /**
     * 请求方式异常
     *
     * @param e       异常
     * @param request 请求
     * @return 结果封装
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResultResponse handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址:{}, 不支持{}请求, ", requestURI, e.getMethod(), e);
        return ResultResponse.error(ExceptionEnum.METHOD_NOT_ALLOWED);
    }

    /**
     * 参数校验异常
     *
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(BindException.class)
    public ResultResponse handleBindException(BindException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        Map<String, String> res = new LinkedHashMap<>();
        BindingResult bindingResult = e.getBindingResult();
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        for (ObjectError item : allErrors) {
            if (item instanceof FieldError) {
                String field = ((FieldError) item).getField();
                String defaultMessage = ((FieldError) item).getDefaultMessage();
                res.put(field, defaultMessage);
            }
        }
        log.error("请求地址:{}, 发生参数校验异常:{}, ", requestURI, res, e);
        // return ResultResponse.error("参数校验异常" + e.getBindingResult().getAllErrors().get(0).getDefaultMessage());

        String msg = e.getBindingResult().getFieldError().getDefaultMessage().toString();
        if (StringUtils.isNotEmpty(msg)) {
            return ResultResponse.error(msg);
        }
        return ResultResponse.error("参数校验异常");
    }

    /**
     * 参数校验异常
     *
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResultResponse handleConstraintViolationException(ConstraintViolationException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        Map<String, String> res = new LinkedHashMap<>();
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        for (ConstraintViolation item : constraintViolations) {
            String messageTemplate = item.getMessageTemplate();
            PathImpl propertyPath = (PathImpl) item.getPropertyPath();
            NodeImpl leafNode = propertyPath.getLeafNode();
            res.put(leafNode.asString(), messageTemplate);
        }
        log.error("请求地址:{}, 发生参数校验异常:{}, ", requestURI, res, e);

        return ResultResponse.error(constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(";")));
    }
    
    /**
     * 运行异常
     *
     * @param e       异常
     * @param request 请求
     * @return 结果封装
     */
    @ExceptionHandler(RuntimeException.class)
    public ResultResponse handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址:{}, 发生未知异常:", requestURI, e);
        return ResultResponse.error(ExceptionEnum.INTERNAL_SERVER_ERROR);
    }
    

    /**
     * 未知系统异常
     *
     * @param e       异常
     * @param request 请求
     * @return 结果封装
     */
    @ExceptionHandler(Exception.class)
    public ResultResponse handleException(Exception e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址:{}, 发生系统异常:", requestURI, e);
        return ResultResponse.error(ExceptionEnum.UNKNOW_ERROR);
    }

}
