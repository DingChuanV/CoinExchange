package com.uin.config;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.context.annotation.Configuration;

/**
 * @author dingchuan
 */
@Configuration
@EnableCreateCacheAnnotation
@EnableMethodCache(basePackages = "com.uin.service.impl")
public class JetcacheConfig {

}
