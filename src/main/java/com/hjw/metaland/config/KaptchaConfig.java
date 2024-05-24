package com.hjw.metaland.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

@Configuration
public class KaptchaConfig {
  @Bean
  public DefaultKaptcha getDefaultKaptcha() {
    DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
    Properties properties = new Properties();
    properties.setProperty("kaptcha.border", "no");
    properties.setProperty("kaptcha.border.color", "134,114,200");
    properties.setProperty("kaptcha.image.width", "200");
    properties.setProperty("kaptcha.image.height", "40");
    properties.setProperty("kaptcha.textproducer.char.length", "4");
    properties.setProperty("kaptcha.textproducer.font.names",
        "Arial,Arial Narrow,Serif,Helvetica,Tahoma,Times New Roman,Verdana");
    properties.setProperty("kaptcha.textproducer.font.size", "38");
    properties.setProperty("kaptcha.textproducer.font.color", "blue");

    properties.setProperty("kaptcha.background.clear.from", "white");
    properties.setProperty("kaptcha.background.clear.to", "white");

    Config config = new Config(properties);
    defaultKaptcha.setConfig(config);
    return defaultKaptcha;
  }
}
