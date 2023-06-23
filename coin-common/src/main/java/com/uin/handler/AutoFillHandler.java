package com.uin.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import java.util.Date;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author dingchuan
 */
public class AutoFillHandler implements MetaObjectHandler {

  @Override
  public void insertFill(MetaObject metaObject) {
    long userId = getCurrentUserId();
    this.strictInsertFill(metaObject, "createBy", Long.class, userId);
    this.strictInsertFill(metaObject, "created", Long.class, new Date());
    this.strictInsertFill(metaObject, "lastUpdateTime", Long.class, new Date());
  }

  private Long getCurrentUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null) {
      String s = authentication.getPrincipal().toString();
      return Long.valueOf(s);
    }
    return null;
  }

  @Override
  public void updateFill(MetaObject metaObject) {
    this.strictInsertFill(metaObject, "lastUpdateTime", Long.class, new Date());
    this.strictInsertFill(metaObject, "modifyBy", Long.class, getCurrentUserId());
  }
}
