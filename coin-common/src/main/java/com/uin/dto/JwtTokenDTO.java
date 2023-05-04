package com.uin.dto;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author dingchuan
 */
@Data
public class JwtTokenDTO {

  @JsonProperty(value = "accessToken")
  private String accessToken;
  /**
   * token
   */
  @JsonProperty("tokenType")
  private String tokenType;
  @JsonProperty("refreshToken")
  private String refreshToken;
  @JsonProperty("expiresIn")
  private Long expiresIn;
  /**
   * 范围
   */
  @JsonProperty("scope")
  private String scope;
  /**
   * JWT ID
   */
  @JsonProperty("jti")
  private String jti;
}
