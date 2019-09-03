package com.karol.route.fallback;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import com.netflix.zuul.exception.ZuulException;
@Component
public class UaaRouteFallback implements FallbackProvider{

	@Override
	public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
		return new CustomClientHttpResponse(cause, route);
	}

	@Override
	public String getRoute() {
		// TODO Auto-generated method stub
		return "conhub-auth-server";
	}

}
