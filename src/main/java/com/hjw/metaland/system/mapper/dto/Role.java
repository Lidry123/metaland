package com.hjw.metaland.system.mapper.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.ToString;

@Data
@TableName("us_role")
@ToString
public class Role {
  public static final Integer C_NORMAL = 1;
  public static final Integer C_DISABLE = 0;

  @TableId(value = "pk_role_oid", type = IdType.AUTO)
  Long id;

  @TableField("role_name")
  String name;

  Integer status;
}
