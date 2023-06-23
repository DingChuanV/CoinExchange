package com.uin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.uin.domain.SysRole;

/**
 * <p>
 * 角色 服务类
 * </p>
 *
 * @author dingchuan
 * @since 2023-05-01
 */
public interface ISysRoleService extends IService<SysRole> {

  /**
   * 根据用户ID判断是否是超级用户
   *
   * @param userId
   * @return
   */
  boolean isSuperAdmin(Long userId);

  Page<SysRole> findByPage(Page<SysRole> page, String name);
}
