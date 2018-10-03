package com.karol;

import com.github.mthizo247.cloud.netflix.zuul.web.socket.EnableZuulWebSocket;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import com.karol.route.fallback.UaaRouteFallback;

@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
@EnableZuulWebSocket
public class ConhubGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConhubGatewayApplication.class, args);
	}
	
}
