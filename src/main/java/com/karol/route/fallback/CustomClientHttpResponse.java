package com.karol.route.fallback;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;

import com.netflix.zuul.exception.ZuulException;

public class CustomClientHttpResponse implements ClientHttpResponse{
	private Throwable cause;
	private String route;
	private InputStream body;
	private HttpHeaders headers;
	private int rawStatusCode = 500;
	private HttpStatus statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
	private String statusText = "Server exception";
	
	
	public CustomClientHttpResponse(Throwable cause, String route) {
		super();
		this.cause = cause;
		this.route = route;
		this.body =null;
		this.headers = new HttpHeaders();
			this.rawStatusCode = 503;
			this.statusCode = HttpStatus.SERVICE_UNAVAILABLE;
			this.statusText = "Service currently unavailable. Try again in a moment";
		
	}

	@Override
	public InputStream getBody() throws IOException {
		
		return this.body;
	}

	@Override
	public HttpHeaders getHeaders() {
		// TODO Auto-generated method stub
		return this.headers;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getRawStatusCode() throws IOException {
		// TODO Auto-generated method stub
		return this.rawStatusCode;
	}

	@Override
	public HttpStatus getStatusCode() throws IOException {
		// TODO Auto-generated method stub
		return this.getStatusCode();
	}

	@Override
	public String getStatusText() throws IOException {
		// TODO Auto-generated method stub
		return this.statusText;
	}

}
