package com.olteanuflorin86.msscbeerorderservicev1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisAutoConfiguration;

@SpringBootApplication(exclude = ArtemisAutoConfiguration.class)
public class MsscBeerOrderServiceV1Application {

	public static void main(String[] args) {
		SpringApplication.run(MsscBeerOrderServiceV1Application.class, args);
	}

}
