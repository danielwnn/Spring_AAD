package com.example.SpringAADDemo.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class AzureAADLoginConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.mvcMatchers("/secure").authenticated() // Require authentication for /secure path
			.and()
			.oauth2Login();	// Enables OAuth2 login using client registration constructed from app settings 
	}
	
}
