package com.karol.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class PathAccessService {
	
	public boolean usernameEqualsPrincipal(Authentication authentication, String username) {
		return authentication.getName().equals(username);
	}
}
