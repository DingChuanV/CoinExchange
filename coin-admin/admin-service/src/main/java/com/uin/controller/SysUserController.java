package com.uin.controller;


import com.uin.model.R;
import com.uin.domain.SysUser;
import com.uin.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 平台用户 前端控制器
 * </p>
 *
 * @author dingchuan
 * @since 2023-05-01
 */
@RestController
@Api(tags = "平台用户")
@RequestMapping("/sys-user")
@RequiredArgsConstructor
public class SysUserController {

  private final ISysUserService sysUserService;

  @GetMapping("/getSysUserList")
  @ApiOperation(value = "查询所有平台用户")
  public R<List<SysUser>> getSysUserList() {
    List<SysUser> list = sysUserService.list();
    List<SysUser> list1 = list.stream().filter(t -> !t.getUsername().equals("admin")).collect(Collectors.toList());
    return R.ok(list1);
  }
}
