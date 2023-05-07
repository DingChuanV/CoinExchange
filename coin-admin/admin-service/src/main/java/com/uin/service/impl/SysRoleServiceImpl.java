package com.uin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uin.domain.SysRole;
import com.uin.mapper.SysRoleMapper;
import com.uin.service.ISysRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author dingchuan
 * @since 2023-05-01
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

  private final SysRoleMapper sysRoleMapper;

  @Override
  public boolean isSuperAdmin(Long userId) {
    String roleCode = sysRoleMapper.getUserRoleCode(userId);
    if (StrUtil.isNotEmpty(roleCode)&& "ROLE_ADMIN".equals(roleCode)){
      return true;
    }
    return false;
  }
}
