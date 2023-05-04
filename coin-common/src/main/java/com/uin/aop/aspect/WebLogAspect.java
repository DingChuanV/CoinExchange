package com.uin.aop.aspect;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.alibaba.fastjson.JSON;
import com.uin.model.WebLog;
import io.swagger.annotations.ApiOperation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author dingchuan
 */
@Aspect
@Component
@Slf4j
@Order(1)
public class WebLogAspect {

  /**
   * 日志记录，环绕通知：方法执行前和方法执行后
   *
   * @param proceedingJoinPoint
   * @return
   */
  @Around(value = "com.uin.aop.pointcut.ControllerPointCut.weblog()")
  public Object recordWebLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    Object o = null;
    // 计时器
    StopWatch stopWatch = new StopWatch();
    stopWatch.start();
    //Object[] args = proceedingJoinPoint.getArgs();
    /*List<Object> argsList = new ArrayList<>();
    for (int i = 0; i < args.length; i++) {
      // 如果参数类型是请求和响应的http，则不需要拼接【这两个参数，使用JSON.toJSONString()转换会抛异常】
      if (args[i] instanceof HttpServletRequest || args[i] instanceof HttpServletResponse) {
        continue;
      }
      argsList.add(args[i]);
    }*/
    o = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
    stopWatch.stop();
    // 获取请求上下文
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    // 获取登录的用户名
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    // 获取方法
    MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
    Method method = signature.getMethod();
    // 获取方法的ApiOperation注解
    ApiOperation annotation = method.getAnnotation(ApiOperation.class);
    // 获取目标对象的类型名称
    String className = proceedingJoinPoint.getTarget().getClass().getName();
    // 获取请求url的地址
    String requestUrl = request.getRequestURL().toString();
    WebLog webLog = WebLog.builder()
        .basePath(StrUtil.removeSuffix(requestUrl, URLUtil.url(requestUrl).getPath()))
        .description(annotation == null ? "no desc" : annotation.value())
        .ip(request.getRemoteAddr())
        .parameter(getMethodParameter(method, proceedingJoinPoint.getArgs()))
        .method(className + "." + method.getName())
        .result(o)
        .spendTime((int) stopWatch.getTotalTimeMillis())
        .uri(request.getRequestURI())
        .url(request.getRequestURL().toString())
        .username(authentication == null ? "anonymous" : authentication.getName())
        .build();
    log.info(JSON.toJSONString(webLog, true));
    return o;
  }

  /**
   * 获取方法参数
   *
   * @param method
   * @param args
   * @return
   */
  private Object getMethodParameter(Method method, Object[] args) {
    LocalVariableTableParameterNameDiscoverer localVariableTableParameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
    // 方法的形参的名称
    String[] parameterNames = localVariableTableParameterNameDiscoverer.getParameterNames(method);
    HashMap<String, Object> map = new HashMap<>();
    Parameter[] parameters = method.getParameters();
    if (args != null) {
      for (int i = 0; i < parameters.length; i++) {
        assert parameterNames != null;
        map.put(parameterNames[i], args[i] == null ? "" : JSON.toJSONString(args[i]));
      }
    }
    return map;
  }
}
