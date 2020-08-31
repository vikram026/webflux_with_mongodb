package com.nisum.webflux.service;

import com.nisum.webflux.model.Employee;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IEmployeeService {

	Mono<Employee> save(Employee employee);

	Flux<Employee> getAllEmployee();

	Flux<Employee> findByName(String name);

	Flux<Employee> findByCity(String city);

	Flux<Employee> findByHobbyName(String hobbyName);

	Flux<Employee> findByNameAndHobbyName(String hobbyName, String hobbyName2);

	Flux<Employee> findByNameAndCityAndHobby(String name, String cityName, String hobbyName);

}
