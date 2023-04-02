package com.uin.authorization.config;

import com.alibaba.fastjson.JSONObject;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * web安全配置配置
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private static final Logger log = LoggerFactory.getLogger(WebSecurityConfig.class);

  @Bean
  protected AuthenticationManager authenticationManager() {
    try {
      return super.authenticationManager();
    } catch (Exception e) {
      log.error("Exception:{}", e.getMessage());
      throw new RuntimeException(e);
    }
  }

  @Bean
  public UserDetailsService userDetailsService() {
    InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
    User user = new User("admin", "123456", Arrays.asList(new SimpleGrantedAuthority("Role_admin")));
    userDetailsManager.createUser(user);
    log.info("UserDetailsService:{},username:{},password:{},GrantedAuthority:{}", JSONObject.toJSON(user),
        user.getUsername(),user.getPassword(),user.getAuthorities());
    return userDetailsManager;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return NoOpPasswordEncoder.getInstance();
  }

  /**
   * WebSecurityConfig配置
   *
   * @param http
   * @throws Exception
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable().authorizeRequests().anyRequest().authenticated();
  }
}
