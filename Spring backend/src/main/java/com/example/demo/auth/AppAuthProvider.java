package com.example.demo.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public class AppAuthProvider extends DaoAuthenticationProvider {
	@Autowired
	UserService userDetailsService;

	@Override
	public Authentication authenticate(Authentication authentication) {
		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;
		String name = auth.getName();
		String password = auth.getCredentials().toString();
		System.out.println(name + " " + password);
		UserDetails user = userDetailsService.loadUserByUsername(name);
		if (user == null) {
			System.out.println("DONE6");
			throw new BadCredentialsException("Username/Password does not match for " + auth.getPrincipal());
		}
		if (password.equals(user.getPassword())) {
			System.out.println("DONE7");
			return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

		} else {
			System.out.println("DONE8");
			throw new BadCredentialsException("Username/Password does not match for " + auth.getPrincipal());

		}

	}

	@Override
	public boolean supports(Class<?> authentication) {
		System.out.println("DONE9");
		return true;
	}
}
