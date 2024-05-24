package com.hjw.metaland.system.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hjw.metaland.common.R;
import com.hjw.metaland.system.mapper.dto.SysInfo;
import com.hjw.metaland.system.service.RoleService;
import com.hjw.metaland.system.service.SysInfoService;

import cn.dev33.satoken.stp.StpUtil;
import jakarta.annotation.Resource;

@RestController
@RequestMapping("/sysinfo")
public class SysInfoController {
  @Resource
  SysInfoService sysInfoService;
  @Resource
  RoleService roleService;

  @GetMapping("/list")
  public R list() {
    if (StpUtil.isLogin()) {
      return R.data(sysInfoService.list());
    } else {
      return R.data(sysInfoService.lambdaQuery().eq(SysInfo::getPub, SysInfo.BOOL_TRUE).list());
    }
  }

  /**
   * 批量更新系统信息
   * 
   * @param values
   * @return
   */
  @GetMapping("/update")
  @Transactional
  public R list(@RequestBody List<SysInfo> values) {
    for (SysInfo info : values) {
      Optional<SysInfo> sysInfoOpt = sysInfoService.lambdaQuery().eq(SysInfo::getKey, info.getKey()).oneOpt();
      if (sysInfoOpt.isPresent()) {
        sysInfoOpt.get().setValue(info.getValue());
        sysInfoService.updateByKey(info.getKey(), info.getValue());
      } else {
        sysInfoService.save(info);
      }
    }
    return R.success();
  }

  @GetMapping("/roles")
  public R roles() {
    return R.data(roleService.lambdaQuery().list());
  }

}
