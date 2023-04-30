package com.uin.common.aop.pointcut;

import org.aspectj.lang.annotation.Pointcut;

/**
 * @author dingchuan
 */
public class ControllerPointCut {

  /**
   * 切点
   */
  @Pointcut("execution( * com.uin.*.controller.*.*(..))")
  public void weblog(){
  }
}
