package com.hjw.metaland.system.dal;

import lombok.Data;

@Data
public class LoginReq {
  String username;
  String password;
  String captcha;
}
