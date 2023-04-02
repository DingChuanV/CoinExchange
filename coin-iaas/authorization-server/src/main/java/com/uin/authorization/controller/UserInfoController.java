package com.uin.authorization.controller;

import java.security.Principal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserInfoController {

  /**
   * 授权服务器带着用户请求的token去redis服务器验证用户，redis服务器给返回的一个User对象
   *
   * @return
   */
  @GetMapping("/getPrincipal")
  public Principal getPrincipal(Principal principal) {
    // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    // Object principal1 = authentication.getPrincipal();
    return principal;
  }
}
