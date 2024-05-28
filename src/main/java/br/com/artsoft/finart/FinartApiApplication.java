package br.com.artsoft.finart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FinartApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinartApiApplication.class, args);
	}

}
