package com.example.houduan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.example.houduan.mapper")
@SpringBootApplication

public class HouduanApplication {

	public static void main(String[] args) {
		SpringApplication.run(HouduanApplication.class, args);
	}

}
