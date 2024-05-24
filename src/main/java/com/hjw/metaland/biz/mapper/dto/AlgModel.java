package com.hjw.metaland.biz.mapper.dto;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@TableName("alg_model")
@Data
public class AlgModel implements Serializable {
  @TableId(value = "pk_oid", type = IdType.AUTO)
  private Integer id;

  String name;

  String remark;

  String version;

  String path;

  String props;

  @TableField(value = "create_at", fill = FieldFill.INSERT)
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  Date createAt;

  @TableField(value = "update_at", fill = FieldFill.INSERT_UPDATE)
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  Date updateAt;

  @TableField("last_value")
  String lastValue;
}
