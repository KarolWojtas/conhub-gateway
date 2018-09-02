package com.karol.config.security;

import java.io.IOException;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.util.FileCopyUtils;
@Configuration
public class ResourceSecurityConfiguration extends WebSecurityConfigurerAdapter{
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		OAuth2AuthenticationManager manager = new OAuth2AuthenticationManager();
		manager.setTokenServices(tokenServices());
		return manager;
	}

	
	@Bean
	public JwtAccessTokenConverter jwtTokenEnhancer() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		Resource resource = new ClassPathResource("public.cert");
		String publicKey="";
		try {
			publicKey = new String(FileCopyUtils.copyToByteArray(resource.getInputStream()));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		converter.setVerifierKey(publicKey);
		return converter;
	}
	@Bean
	public TokenStore jwtTokenStore() {
		return new JwtTokenStore(jwtTokenEnhancer());
	}
	@Bean
    @Primary
    public ResourceServerTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(jwtTokenStore());
        return defaultTokenServices;
    }
	
	 @Bean
	    public OAuth2WebSecurityExpressionHandler oauth2WebSecurityExpressionHandler(ApplicationContext applicationContext) {
	        OAuth2WebSecurityExpressionHandler expressionHandler = new OAuth2WebSecurityExpressionHandler();
	        expressionHandler.setApplicationContext(applicationContext);
	        return expressionHandler;
	    }
	

}
