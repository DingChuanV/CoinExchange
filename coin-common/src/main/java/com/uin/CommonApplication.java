package com.uin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author dingchuan
 */
@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
@EnableDiscoveryClient
public class CommonApplication {
  public static void main(String[] args) {
    SpringApplication.run(CommonApplication.class ,args);
  }
}
