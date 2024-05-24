package com.hjw.metaland.system.mapper.dto;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.ToString;

@Data
@TableName("us_logs")
@ToString
public class Log {
  public static final Integer C_NORMAL = 1;
  public static final Integer C_DISABLE = 0;

  @TableId(value = "pk_log_oid", type = IdType.AUTO)
  Long id;

  String name;

  Integer status;

  @TableField(value = "create_at", fill = FieldFill.INSERT)
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  Date createAt;

  String type;

  String url;

  String req;

  String res;

  String exception;

  Integer userId;

  String userName;

  Integer time;

  String ipaddr;

}
