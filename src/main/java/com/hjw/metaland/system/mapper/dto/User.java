package com.hjw.metaland.system.mapper.dto;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hjw.metaland.common.BaseDO;

import lombok.Data;

@TableName("us_user")
@Data
public class User {
  public static final Integer NORMAL = 0;
  public static final Integer LOCK = 1;

  @TableId(value = "pk_user_oid", type = IdType.AUTO)
  private Integer id;

  /** 登录名 */
  @TableField("user_name")
  private String userName;

  /** 昵称 */
  // private String nickName;
  /** 密码 */
  @JsonIgnore
  @TableField("password")
  private String password;
  /** 加密盐 */
  // @JsonIgnore
  // private String salt;
  /** 邮箱 */
  @TableField("email")
  private String mail;
  /** 手机号码 */
  // private String phone;
  /** 头像URL */
  // private String avatar;
  @TableField("fk_role")
  Integer roleId;

  /**
   * 单位
   */
  @TableField("affiliation")
  String affiliation;

  /**
   * 注册时间
   */
  @TableField("time_gene")
  private Date createTime;

  /** 登录时间 */
  @TableField("time_login")
  private Date loginTime;

  /** 登录失败次数 */
  @TableField("count")
  private Integer loginFail;
  /** 性别 */
  // private Integer sex;
  /**
   * 状态;0 正常
   * 1 禁用
   * 2 锁定
   */
  // private Integer status;
  /** 最后登录IP */
  // private String loginIp;
}
