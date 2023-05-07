package com.uin.service;

import com.uin.model.Response.LoginResponse;

/**
 * @author dingchuan
 */
public interface SysLoginService {

  /**
   * 登录
   *
   * @param username
   * @param password
   * @return
   */
  LoginResponse login(String username, String password);
}
