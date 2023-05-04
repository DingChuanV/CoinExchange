package com.uin.controller;

import com.uin.model.Response.LoginResponse;
import com.uin.service.SysLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dingchuan
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@Api(tags = "后台管理系统登录接口")
public class SysLoginController {
  private final SysLoginService sysLoginService;

  @PostMapping("/login")
  @ApiOperation(value = "后台管理系统登录接口")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "username", value = "用户名称"),
      @ApiImplicitParam(name = "password", value = "密码")
  })
  public LoginResponse login(@RequestParam(required = true) String username,
      @RequestParam(required = true) String password) {
    return sysLoginService.login(username,password);
  }

}
