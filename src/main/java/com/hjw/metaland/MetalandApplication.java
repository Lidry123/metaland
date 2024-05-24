package com.hjw.metaland;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.hjw.metaland.utils.SM4Utils;

@SpringBootApplication
@MapperScan(basePackages = { "com.hjw.metaland.system.mapper", "com.hjw.metaland.biz.mapper" })
public class MetalandApplication {

	public static void main(String[] args) {
		SpringApplication.run(MetalandApplication.class, args);
	}

}
