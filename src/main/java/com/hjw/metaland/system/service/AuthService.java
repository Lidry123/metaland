package com.hjw.metaland.system.service;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.hjw.metaland.common.BizException;
import com.hjw.metaland.config.MetaLandConfig;
import com.hjw.metaland.system.dal.LoginReq;
import com.hjw.metaland.system.dal.LoginResp;
import com.hjw.metaland.system.mapper.dto.User;
import com.hjw.metaland.utils.SM4Utils;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ICaptcha;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.net.NetUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.val;

@Service
public class AuthService {
  @Resource
  RedisTemplate<String, Object> redisTemplate;

  @Resource
  DefaultKaptcha defaultKaptcha;

  @Resource
  UserService userService;

  @Resource
  MetaLandConfig config;

  @SneakyThrows
  public User login(LoginReq req, HttpServletRequest request) {
    // 验证码
    String sessionId = request.getSession().getId();
    String key = sessionId + "_captcha";
    Object value = redisTemplate.opsForValue().get(key);
    redisTemplate.delete(key);
    if (value == null || req.getCaptcha() == null)
      throw new BizException("验证码无效");
    if (!StringUtils.equalsIgnoreCase(value.toString(), req.getCaptcha()))
      throw new BizException("验证码无效");

    User user = userService.getByUserName(req.getUsername());
    if (user == null)
      throw new BizException("用户名或密码错误");

    if (user.getLoginFail() >= config.getLoginLockTime()) {
      Date now = Calendar.getInstance().getTime();
      long span = (now.getTime() - user.getLoginTime().getTime()) / 1000 / 60;
      if (span < config.getLoginLockTime()) {
        throw new BizException("用户被锁定，请十分钟后重试");
      }
    }

    if (!checkPassword(user, req.getPassword())) {
      Integer fail = 1;
      if (user.getLoginFail() != null) {
        fail = user.getLoginFail() + 1;
      }

      userService.lambdaUpdate().set(User::getLoginFail, fail)
          .set(User::getLoginTime, Calendar.getInstance().getTime())
          .eq(User::getId, user.getId()).update();

      throw new BizException("用户名或密码错误");
    }

    userService.lambdaUpdate().set(User::getLoginFail, 0)
        .set(User::getLoginTime, Calendar.getInstance().getTime())
        .eq(User::getId, user.getId()).update();
    return user;
  }

  // 检查密码有效性
  private boolean checkPassword(User user, String password) {
    password = SM4Utils.encrypt(password);
    return user.getPassword().equals(password);
  }

  public ICaptcha newCaptcha(HttpServletRequest request) {
    ICaptcha captcha = CaptchaUtil.createCircleCaptcha(200, 40, 4, 5);
    String text = captcha.getCode();
    String sessionId = request.getSession().getId();
    redisTemplate.opsForValue().set(sessionId + "_captcha", text,
        Duration.ofSeconds(60));
    return captcha;
  }

  public void logout() {
    if (StpUtil.isLogin()) {
      StpUtil.logout();
    }
  }
}
