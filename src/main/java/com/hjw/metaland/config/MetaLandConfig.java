package com.hjw.metaland.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "metaland")
@Data
public class MetaLandConfig {
  private String sm4Key;
  private Integer loginTimes;
  private Integer loginLockTime;
}
