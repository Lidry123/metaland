package com.hjw.metaland.config;

import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 操作记录
 */
@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Operation {
  /**
   * 操作名
   * 
   * @return
   */
  String name();

  /**
   * 操作类型
   * 
   * @return
   */
  String type();

  boolean enable() default true;
}
