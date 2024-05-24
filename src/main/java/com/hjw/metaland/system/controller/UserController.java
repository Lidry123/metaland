package com.hjw.metaland.system.controller;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hjw.metaland.common.R;
import com.hjw.metaland.config.Operation;
import com.hjw.metaland.system.mapper.dto.User;
import com.hjw.metaland.system.service.UserService;
import com.hjw.metaland.utils.RequestUtil;
import com.hjw.metaland.utils.SM4Utils;

import cn.dev33.satoken.stp.StpUtil;
import jakarta.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {
  @Resource
  UserService userService;

  @GetMapping("/info")
  public R getUserInfo(@RequestBody JSONObject body) {
    Long userId = null;
    if (body != null) {
      userId = body.getLong("id");
    } else {
      userId = (Long) StpUtil.getLoginId();
    }
    User user = userService.getById(userId);
    return R.data(user);
  }

  // 注册用户
  @PostMapping("/register")
  @Operation(name = "用户登出", type = "系统安全")
  public R register(@RequestBody User user) {
    var cnt = userService.lambdaQuery().eq(User::getUserName, user.getUserName()).count();
    if (cnt > 0)
      return R.fail("用户名已被使用");
    userService.save(user);
    return R.data(user);
  }

  // 删除用户
  @PostMapping("/delete")
  @Operation(name = "用户登出", type = "系统安全")
  public R delete(@RequestBody User user) {
    var cnt = userService.lambdaQuery().eq(User::getId, user.getId()).count();
    if (cnt > 0)
      return R.fail("用户不存在");
    userService.removeById(user);
    return R.data(user);
  }

  // 修改用户
  @PostMapping("/edit")
  @Operation(name = "用户登出", type = "系统安全")
  public R edit(@RequestBody User user) {
    var cnt = userService.lambdaQuery().eq(User::getId, user.getId()).count();
    if (cnt > 0)
      return R.fail("用户不存在");

    cnt = userService.lambdaQuery().eq(User::getUserName, user.getUserName())
        .ne(User::getId, user.getId()).count();
    if (cnt > 0)
      return R.fail("用户名已被使用");

    userService.updateById(user);
    return R.data(user);
  }

  @PostMapping("/reset-pass")
  @Operation(name = "用户登出", type = "系统安全")
  public R resetPassword(@RequestBody User user) {
    var cnt = userService.lambdaQuery().eq(User::getId, user.getId()).count();
    if (cnt > 0)
      return R.fail("用户不存在");

    String pass = RandomStringUtils.random(6);
    String password = SM4Utils.encrypt(pass);
    user.setPassword(password);
    userService.lambdaUpdate().set(User::getPassword, password).eq(User::getId, user.getId()).update();
    return R.data(pass);
  }

  @PostMapping("/query")
  @Operation(name = "用户登出", type = "系统安全")
  public R query(@RequestBody JSONObject body) {
    IPage<User> pager = RequestUtil.getPager(body);
    var q = userService.lambdaQuery();
    String name = body.getString("name");
    if (StringUtils.isEmpty(name)) {
      q.like(User::getUserName, name);
    }
    var result = q.page(pager);
    return R.data(result);
  }

}
