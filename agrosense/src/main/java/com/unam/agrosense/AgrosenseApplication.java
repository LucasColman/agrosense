package com.unam.agrosense;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling  // 🔹 Habilita la ejecución automática de tareas programadas
public class AgrosenseApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgrosenseApplication.class, args);
	}

}
