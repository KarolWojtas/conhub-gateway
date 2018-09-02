package com.karol.config.security;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;




@Configuration
@EnableResourceServer
public class Oauth2ResourceServerConfiguration extends ResourceServerConfigurerAdapter{
	
	@Autowired
	private TokenStore jwtTokenStore;
	@Autowired
	private AuthenticationManager authManager;
	@Autowired
	private OAuth2WebSecurityExpressionHandler oauth2WebSecurityExpressionHandler;
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/users/auth/**").denyAll()
			.antMatchers(HttpMethod.GET, "/users/{username}").permitAll()
			.antMatchers("/users/{username}","/users/{username}/**")
				.access("@pathAccessService.usernameEqualsPrincipal(authentication,#username)")
			.antMatchers("/uaa/oauth/token").permitAll()
			.antMatchers("/**").authenticated()
			;
	}
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenStore(jwtTokenStore)	
			.resourceId("user")
			.expressionHandler(oauth2WebSecurityExpressionHandler)
			.authenticationManager(authManager);
		
	}
	
	
}
