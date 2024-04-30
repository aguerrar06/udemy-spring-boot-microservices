package com.microservicios.springbootserviciooauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class SpringbootServicioOauth implements CommandLineRunner {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootServicioOauth.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String pwd = "12345";
		for (int i = 0; i < 4; i++) {
			String pwdBcrypt = passwordEncoder.encode(pwd);
			System.out.println(pwdBcrypt);
		}
	}

}
