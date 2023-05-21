package com.uin.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.uin.domain.SysPrivilege;
import com.uin.model.R;
import com.uin.service.ISysPrivilegeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * <p>
 * 权限配置 前端控制器
 * </p>
 *
 * @author dingchuan
 * @since 2023-05-01
 */
@RestController
@RequestMapping("/admin")
@Api(tags = "权限控制器")
@RequiredArgsConstructor
public class SysPrivilegeController {
  private final ISysPrivilegeService sysPrivilegeService;

  @ApiOperation("分页查询权限配置")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "current", value = "当前页"),
      @ApiImplicitParam(name = "size", value = "每页大小")})
  @GetMapping("/getPrivilegesByPage")
  // @PreAuthorize("hasAnyAuthority('sys_privilege_query')")
  public R<Page<SysPrivilege>> getPrivilegesByPage(@ApiIgnore Page<SysPrivilege> page) {
    page.addOrder(OrderItem.desc("last_update_time"));
    return R.ok((sysPrivilegeService.page(page)));
  }
  @PostMapping("/addSysPrivileges")
  @ApiOperation(value = "新增权限配置")
  // @PreAuthorize("hasAnyAuthority('sys_privilege_create')")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "name",value = "功能点名称"),
      @ApiImplicitParam(name = "description",value = "功能描述"),
      @ApiImplicitParam(name = "menuId",value = "所属菜单Id")
  })
  public R addPrivileges(@RequestBody @Validated SysPrivilege sysPrivilege){
    sysPrivilege.setCreateBy(
        Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()));
    sysPrivilege.setCreated(new Date());
    return R.ok(sysPrivilegeService.save(sysPrivilege));
  }
  @PatchMapping("/updateSysPrivileges")
  @PreAuthorize("hasAnyAuthority('sys_privilege_update')")
  public R updateSysPrivileges(@RequestBody SysPrivilege sysPrivilege){
    sysPrivilege.setModifyBy(Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()));
    sysPrivilege.setLastUpdateTime(new Date());
    return R.ok(sysPrivilegeService.updateById(sysPrivilege));
  }

}
