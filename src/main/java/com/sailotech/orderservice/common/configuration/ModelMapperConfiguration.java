package com.sailotech.orderservice.common.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sailotech.orderservice.JwtTokenFilter;

@Configuration
public class ModelMapperConfiguration {

	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	public SecurityFilterChain configureSecurity(HttpSecurity httpSecurity) throws Exception {
		
		httpSecurity.cors().disable(); //Cross origin resource sharing
		httpSecurity.csrf().disable();
		
		httpSecurity.authorizeHttpRequests(config -> {
			config.anyRequest().permitAll();
		});
		
		httpSecurity.addFilterBefore(new JwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		return httpSecurity.build();
	}
}
