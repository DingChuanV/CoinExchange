package com.uin.model.Response;

import com.uin.domain.SysMenu;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * @author dingchuan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "登录响应结果")
public class LoginResponse {

  /**
   * token
   */
  @ApiModelProperty(value = "token")
  private String token;
  /**
   * 该用户的菜单数据
   */
  @ApiModelProperty(value = "菜单数据")
  private List<SysMenu> menus;
  /**
   * 该用户的权限数据
   */
  @ApiModelProperty(value = "权限数据")
  private List<SimpleGrantedAuthority> authorities;
}
