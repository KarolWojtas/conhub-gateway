package com.karol;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner{
	@Value("${eureka.client.service-url.default-zone}")
	private String eurekaUri;
	@Override
	public void run(String... args) throws Exception {
		System.out.println(eurekaUri);
		
	}

}
