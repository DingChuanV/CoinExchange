package com.uin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.uin.domain.SysMenu;
import java.util.List;

/**
 * <p>
 * 系统菜单 Mapper 接口
 * </p>
 *
 * @author dingchuan
 * @since 2023-05-01
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

  /**
   * 根据用户id查询菜单
   * @param userId
   * @return
   */
  List<SysMenu> selectMenusByUserId(Long userId);
}
