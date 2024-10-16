package com.uva.reserva;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ReservaApplication {

	public static void main(String[] args) {

		SpringApplication.run(ReservaApplication.class, args);

	}

}
