package com.uin.common.service.impl;

import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.uin.common.model.WebLog;
import com.uin.common.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author dingchuan
 */
@Service
@Slf4j
public class TestServiceImpl implements TestService {
  @Cached(name = "com.uin.common.service.impl.TestServiceImpl:",key = "#username" ,cacheType = CacheType.BOTH)
  public WebLog get(String username) {
    WebLog webLog = WebLog.builder().username(username).result("ok").build();
    return webLog;
  }
}
