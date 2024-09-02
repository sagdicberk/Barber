package com.example.barber_appointment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BarberAppointmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(BarberAppointmentApplication.class, args);
	}

}
