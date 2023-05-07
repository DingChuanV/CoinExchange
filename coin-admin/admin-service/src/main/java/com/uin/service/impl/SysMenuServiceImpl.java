package com.uin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uin.domain.SysMenu;
import com.uin.mapper.SysMenuMapper;
import com.uin.service.ISysMenuService;
import com.uin.service.ISysRoleService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统菜单 服务实现类
 * </p>
 *
 * @author dingchuan
 * @since 2023-05-01
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {
  private final ISysRoleService sysRoleService;
  private final SysMenuMapper sysMenuMapper;
  @Override
  public List<SysMenu> getMenusByUserId(Long userId) {
    if (sysRoleService.isSuperAdmin(userId)){
      return list();
    }
    return sysMenuMapper.selectMenusByUserId(userId);
  }
}
