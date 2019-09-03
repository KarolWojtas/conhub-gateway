package com.karol.config.security;

import com.karol.filters.WebSocketHeaderFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;





@Configuration
@EnableResourceServer
public class Oauth2ResourceServerConfiguration extends ResourceServerConfigurerAdapter{
	
	@Autowired
	private TokenStore jwtTokenStore;
	@Autowired
	private AuthenticationManager authManager;
	@Autowired
	private OAuth2WebSecurityExpressionHandler oauth2WebSecurityExpressionHandler;
	@Value("${allowed-origin}")
	private String allowedOrigin;
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
                .antMatchers( "/content/comments-emitter").permitAll()
                .antMatchers(HttpMethod.GET, "/content/venues/{venueId}", "/content/venues").permitAll()
			.antMatchers(HttpMethod.GET, "/content/venues/{venueId}/avatar").permitAll()
			//.antMatchers(HttpMethod.POST, "/content/venues/{venueId}/avatar").hasRole("ADMIN")
			.antMatchers(HttpMethod.PATCH, "/content/venues/{venueId}").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/content/venues/{venueId}").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/content/concerts").permitAll()
                .antMatchers(HttpMethod.DELETE, "/content/comments/{commentId}/{username}").access("@pathAccessService.usernameEqualsPrincipal(authentication,#username)")
                .antMatchers(HttpMethod.GET, "/content/concerts/{concertId}").permitAll()
                .antMatchers(HttpMethod.PATCH, "/content/concerts/{concertId}").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/content/concerts/{concertId}").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/content/concerts/{concertId}/comments").permitAll()
                .antMatchers(HttpMethod.POST, "/content/concerts/{concertId}/comments/{username}").access("@pathAccessService.usernameEqualsPrincipal(authentication,#username)")
                .antMatchers("/content/interests/{username}").permitAll()
			.antMatchers("/users/auth/**").denyAll()
			.antMatchers(HttpMethod.GET, "/users/{username}","/users/checkusername/{username}","/users/{username}/avatar").permitAll()
			.antMatchers(HttpMethod.POST,"/users/").permitAll()
			.antMatchers("/users/{username}","/users/{username}/**")
				.access("@pathAccessService.usernameEqualsPrincipal(authentication,#username)")
			.antMatchers(HttpMethod.PATCH, "/users/{username}")
				.access("@pathAccessService.usernameEqualsPrincipal(authentication,#username)")
			.antMatchers("/uaa/oauth/token**").permitAll()
			.antMatchers(HttpMethod.GET, "/social/**").permitAll()
			.antMatchers("/**").authenticated()
			.and()
			.cors()
			;
	}
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenStore(jwtTokenStore)	
			.resourceId("user")
			.expressionHandler(oauth2WebSecurityExpressionHandler)
			.authenticationManager(authManager);
		
	}
	@Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new     UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin(allowedOrigin);
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        source.registerCorsConfiguration("/**", config);
        return source;
    }
	
	
	
}
