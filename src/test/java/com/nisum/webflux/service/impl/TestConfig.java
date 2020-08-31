package com.nisum.webflux.service.impl;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import com.nisum.webflux.repository.EmployeeRepo;

@TestConfiguration
public class TestConfig {
	
	@MockBean
	EmployeeRepo empRepo;
	
//	@Bean
//	@Primary
//	public EmployeeRepo empRepo() {
//		return empRepo;
//	}

}
