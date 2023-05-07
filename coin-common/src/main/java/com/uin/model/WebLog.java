package com.uin.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ELK web操作日志记录
 * @author dingchuan
 */
@Data
/**
 * 作用于类，覆盖默认的equals和hashcode方法，callSuper,是否调用父类的方法，默认为false。
 */
@EqualsAndHashCode(callSuper = false)
@Builder
public class WebLog {
  /**
   * 操作描述
   */
  private String description;

  /**
   * 操作用户
   */
  private String username;

  /**
   * 消耗时间
   */
  private Integer spendTime;

  /**
   * 根路径
   */
  private String basePath;

  /**
   * URI
   */
  private String uri;

  /**
   * URL
   */
  private String url;

  /**
   * 请求类型
   */
  private String method;

  /**
   * IP地址
   */
  private String ip;

  /**
   * 请求参数
   */
  private Object parameter;

  /**
   * 返回结果
   */
  private Object result;
}
