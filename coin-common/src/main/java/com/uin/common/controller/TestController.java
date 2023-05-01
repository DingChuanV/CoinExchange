package com.uin.common.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uin.common.model.R;
import com.uin.common.model.WebLog;
import com.uin.common.service.TestService;
import io.swagger.annotations.*;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dingchuan
 */
@RestController
@Api(tags = "CoinCommon里面测试的接口")
@RequiredArgsConstructor
public class TestController {

  private final ObjectMapper objectMapper;
  private final TestService testService;
  private final RedisTemplate<String,Object> redisTemplate;
  @GetMapping("/common/test")
  @ApiOperation(value = "测试方法", authorizations = {@Authorization("Authorization")})
  @ApiImplicitParams({
      @ApiImplicitParam(name = "param", value = "参数1", dataType = "String", paramType = "query", example = "paramValue"),
      @ApiImplicitParam(name = "param1", value = "参数2", dataType = "String", paramType = "query", example = "paramValue")
  })
  public R<String> testMethod(String param, String param1) {
    return R.ok();
  }

  @GetMapping("/common/date")
  @ApiOperation(value = "日志格式化测试", authorizations = {@Authorization("Authorization")})
  public R<Date> testMethod() {
    return R.ok(new Date());
  }

  @GetMapping("/jetcache/test")
  @ApiOperation(value = "jetcache缓存的测试", authorizations = {@Authorization("Authorization")})
  public R<String> testJetCache(String username) {
    WebLog webLog = testService.get(username);
    System.out.println(webLog);
    return R.ok("OK") ;
  }
}
