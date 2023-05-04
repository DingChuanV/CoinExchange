package com.uin.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
import com.baomidou.mybatisplus.extension.incrementer.H2KeyGenerator;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dingchuan
 */
@Configuration
public class MybatisPlusConfig {
  /**
   * 分页插件
   *
   * @return
   */
  @Bean
  public PaginationInterceptor paginationInterceptor() {
    PaginationInterceptor interceptor = new PaginationInterceptor();
    interceptor.setDbType(DbType.MYSQL);
    return interceptor;
  }

  /**
   * 删除时乐观锁的实现
   *
   * @return
   */
  @Bean
  public OptimisticLockerInterceptor optimisticLockerInterceptor() {
    return new OptimisticLockerInterceptor();
  }

  /**
   * 分布式id的自增策略
   *
   * @return
   */
  @Bean
  public IKeyGenerator iKeyGenerator() {
    return new H2KeyGenerator();
  }
}
