package com.uin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.uin.domain.SysRole;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 角色 Mapper 接口
 * </p>
 *
 * @author dingchuan
 * @since 2023-05-01
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

  /**
   * 根据用户id查询角色code
   * @param userId
   * @return
   */
  String getUserRoleCode(@Param("userId") Long userId);
}
