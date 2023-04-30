package com.uin.common.aop.advice;

import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.uin.common.model.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author dingchuan
 */
@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

  /**
   * 内部API调用的异常
   *
   * @param e
   * @return
   */
  @ExceptionHandler(value = ApiException.class)
  public R handle(ApiException e) {
    if (e.getErrorCode() != null) {
      return R.fail(e.getErrorCode());
    }
    return R.fail(e.getMessage());
  }

  /**
   * 参数验证异常处理
   *
   * @param e
   * @return
   */
  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  public R methodArgumentNotValidException(MethodArgumentNotValidException e) {
    BindingResult bindingResult = e.getBindingResult();
    String message = null;
    if (bindingResult.hasErrors()) {
      FieldError fieldError = bindingResult.getFieldError();
      if (fieldError != null) {
        message = fieldError.getField() + fieldError.getDefaultMessage();
      }
    }
    return R.fail(message);
  }

  /**
   * 对象内部使用Validate 没有校验成功的异常
   *
   * @return
   */
  @ExceptionHandler(value = BindException.class)
  public R handleValidException(BindException e) {
    BindingResult bindingResult = e.getBindingResult();
    String message = null;
    if (bindingResult.hasErrors()) {
      FieldError fieldError = bindingResult.getFieldError();
      if (fieldError != null) {
        message = fieldError.getField() + fieldError.getDefaultMessage();
      }
    }
    return R.fail(message);

  }
}
