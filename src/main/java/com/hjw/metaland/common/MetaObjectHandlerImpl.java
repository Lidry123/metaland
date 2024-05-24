package com.hjw.metaland.common;

import java.util.Calendar;
import java.util.Date;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

@Component
public class MetaObjectHandlerImpl implements MetaObjectHandler {

  @Override
  public void insertFill(MetaObject metaObject) {
    Date time = Calendar.getInstance().getTime();
    if (metaObject.hasSetter("createTime")) {
      metaObject.setValue("createTime", time);
    }
    if (metaObject.hasSetter("updateTime")) {
      metaObject.setValue("updateTime", time);
    }

    if (metaObject.hasSetter("createAt")) {
      metaObject.setValue("createAt", time);
    }
    if (metaObject.hasSetter("updateAt")) {
      metaObject.setValue("updateAt", time);
    }
  }

  @Override
  public void updateFill(MetaObject metaObject) {
    Date time = Calendar.getInstance().getTime();
    if (metaObject.hasSetter("updateTime")) {
      metaObject.setValue("updateTime", time);
    }

    if (metaObject.hasSetter("updateAt")) {
      metaObject.setValue("updateAt", time);
    }
  }

}
