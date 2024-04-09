package br.com.itau.banktransfer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BankTransferApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankTransferApplication.class, args);
	}

}
