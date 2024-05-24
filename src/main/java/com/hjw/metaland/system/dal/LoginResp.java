package com.hjw.metaland.system.dal;

import lombok.Data;

@Data
public class LoginResp {
  String accessToken;
  String userId;
  String refreshToken;
  String expires;
}
