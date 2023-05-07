package com.uin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.enums.ApiErrorCode;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.uin.domain.SysMenu;
import com.uin.dto.auth.JwtToken;
import com.uin.feign.OAuth2FeignClient;
import com.uin.model.Response.LoginResponse;
import com.uin.service.ISysMenuService;
import com.uin.service.SysLoginService;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
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
  @Value("${basic.token:Basic Y29pbi1hcGk6Y29pbi1zZWNyZXQ=}")
  private String basicToken;
  private final RedisTemplate<String,Object> redisTemplate;

  @Override
  public LoginResponse login(String username, String password) {
    log.info("用户{}开始登录", username);
    // 1.去授权服务，授权并获取token
    ResponseEntity<JwtToken> oAuth2FeignClientToken = oAuth2FeignClient.getToken("password", username, password,
        "admin_type",
        basicToken);
    if (oAuth2FeignClientToken.getStatusCode() != HttpStatus.OK) {
      throw new ApiException(ApiErrorCode.FAILED);
    }
    JwtToken jwtTokenDTO = oAuth2FeignClientToken.getBody();
    log.info("远程登录授权服务器成功，获取的token:{}", JSON.toJSONString(jwtTokenDTO, true));
    assert jwtTokenDTO != null;
    String token = jwtTokenDTO.getAccessToken();
    // 2.查询用户的菜单数据
    Jwt jwt = JwtHelper.decode(token);
    String claims = jwt.getClaims();
    JSONObject jwtJson = JSON.parseObject(claims);
    Long userId = Long.valueOf(jwtJson.getString("user_name"));
    List<SysMenu> menus = sysMenuService.getMenusByUserId(userId);
    // 3.根据jwt获取权限数据
    JSONArray authorities = jwtJson.getJSONArray("authorities");
    List<SimpleGrantedAuthority> collect = authorities.stream().map(t -> new SimpleGrantedAuthority(t.toString()))
        .collect(Collectors.toList());
    redisTemplate.opsForValue().set(token,"", jwtTokenDTO.getExpiresIn(), TimeUnit.SECONDS);
    return new LoginResponse(jwtTokenDTO.getTokenType()+""+token,menus,collect);
  }
}
