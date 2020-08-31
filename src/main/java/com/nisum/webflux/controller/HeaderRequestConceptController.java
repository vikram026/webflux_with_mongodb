package com.nisum.webflux.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class HeaderRequestConceptController {
	
	private static final Logger log = LoggerFactory.getLogger(HeaderRequestConceptController.class);
	@GetMapping("/get")
	public String get(@RequestHeader("Accept") String accept
			,@RequestHeader("Accept-Language") String acceptLang
			) {
		log.info("into  HeaderRequestConceptController::get()"+accept);
		log.info("into  HeaderRequestConceptController::get()"+acceptLang);
		//log.info("into  HeaderRequestConceptController::get()"+agent);
		return accept+"/n"+acceptLang; 
	
	}

}
