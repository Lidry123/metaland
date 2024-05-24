package com.hjw.metaland.system.controller;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hjw.metaland.common.R;
import com.hjw.metaland.config.Operation;
import com.hjw.metaland.system.dal.LoginReq;
import com.hjw.metaland.system.dal.LoginResp;
import com.hjw.metaland.system.mapper.dto.User;
import com.hjw.metaland.system.service.AuthService;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.captcha.ICaptcha;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

@RestController
@RequestMapping("/auth")
public class AuthController {
  @Resource
  AuthService authService;

  @PostMapping("/login")
  @Operation(name = "用户登录", type = "系统安全")
  public R login(@RequestBody LoginReq body, HttpServletRequest request) {
    User user = authService.login(body, request);
    StpUtil.login(user.getId());
    return R.data(StpUtil.getTokenValue());
  }

  @GetMapping("/captcha")
  @SneakyThrows
  public void captcha(HttpServletRequest request, HttpServletResponse response) {
    ICaptcha captcha = authService.newCaptcha(request);
    captcha.write(response.getOutputStream());
    // ImageIO.write(image, "jpeg", response.getOutputStream());
  }

  @PostMapping("/logout")
  @Operation(name = "用户登出", type = "系统安全")
  public LoginResp logout() {
    StpUtil.logout();
    return new LoginResp();
  }
}
