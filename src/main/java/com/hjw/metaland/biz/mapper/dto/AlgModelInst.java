package com.hjw.metaland.biz.mapper.dto;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@TableName("alg_model_inst")
@Data
public class AlgModelInst implements Serializable {
  public static final Integer INIT = 0;
  public static final Integer RUN = 1;
  public static final Integer SUCCESS = 2;
  public static final Integer FAIL = 3;

  @TableId(value = "pk_oid", type = IdType.AUTO)
  private Integer id;

  String name;

  String remark;

  String version;

  String props;

  @TableField("create_at")
  Date createAt;

  @TableField("update_at")
  Date updateAt;

  String result;

  Integer status;

  String log;

  String msg;
}
