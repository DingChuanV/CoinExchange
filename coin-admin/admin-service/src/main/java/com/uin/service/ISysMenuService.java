package com.uin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.uin.domain.SysMenu;
import java.util.List;

/**
 * <p>
 * 系统菜单 服务类
 * </p>
 *
 * @author dingchuan
 * @since 2023-05-01
 */
public interface ISysMenuService extends IService<SysMenu> {

  /**
   * 根据用户id查询菜单
   *
   * @param userId
   * @return
   */
  List<SysMenu> getMenusByUserId(Long userId);
}
