package com.hjw.metaland.system.service;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hjw.metaland.system.mapper.SysInfoMapper;
import com.hjw.metaland.system.mapper.dto.SysInfo;

import lombok.val;

@Service
public class SysInfoService extends ServiceImpl<SysInfoMapper, SysInfo> {

  public void updateByKey(String key, String value) {
    lambdaUpdate().set(SysInfo::getValue, value).eq(SysInfo::getKey, key).update();
  }

}
