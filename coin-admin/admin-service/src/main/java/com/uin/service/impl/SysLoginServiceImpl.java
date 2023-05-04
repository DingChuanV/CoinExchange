package com.uin.service.impl;

import com.uin.feign.OAuth2FeignClient;
import com.uin.model.Response.LoginResponse;
import com.uin.service.ISysMenuService;
import com.uin.service.SysLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author dingchuan
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SysLoginServiceImpl implements SysLoginService {

  private final ISysMenuService sysMenuService;
  private final OAuth2FeignClient oAuth2FeignClient;
  private String basicToken;
  @Override
  public LoginResponse login(String username, String password) {
    return null;
  }
}
