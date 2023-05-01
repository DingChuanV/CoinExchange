package com.uin.common.service;

import com.uin.common.model.WebLog;

/**
 * @author dingchuan
 */
public interface TestService {
  /**
   * 通过username 查询weblog
   *
   */
  WebLog get(String username) ;

}
