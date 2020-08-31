package com.nisum.webflux.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nisum.webflux.model.Employee;
import com.nisum.webflux.model.Hobby;
import com.nisum.webflux.repository.HobbyRepo;
import com.nisum.webflux.service.IEmployeeService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class EmployeeController {
	
	@Autowired
	IEmployeeService employeeService;
	
//	@Autowired
//	private HobbyRepo hobbyRepo;
//	
//	
//	@PostMapping("/hobby")
//	public Mono<Hobby> saveh(@RequestBody Hobby hobby) {
//		System.out.println("employee obj ##################################################"+hobby);
//		return hobbyRepo.save(hobby);
//	}
	
	
	
	@PostMapping("/employee")
	public Mono<Employee> save(@RequestBody Employee employee) {
		System.out.println("employee obj ##################################################"+employee);
		return employeeService.save(employee);
	}
	
	
	@GetMapping("/employee")
	public Flux<Employee> getAll(){
		return employeeService.getAllEmployee();
	}
	@GetMapping("/employee/byName")
	public Flux<Employee> findByName(@RequestParam String name){
		
		return employeeService.findByName(name);
	}
	
//	@GetMapping("/employee/byName/flatMap")
//	public Flux<Employee> findByNameFlatMap(@RequestParam String name){
//		
//		return Flux.just(name).flatMap(employeeService::findByName);//employeeService.findByName(name);
//	}
	
	@GetMapping("/employee/byCity")
	public Flux<Employee> findByCity(@RequestParam String city){
		
		return employeeService.findByCity(city);
	}
	@GetMapping("/employee/byHobby")
	public Flux<Employee> findByHobbyName(@RequestParam String hobbyName){
		
		return employeeService.findByHobbyName(hobbyName);
	}
	
	@GetMapping("/employee/byNameAndHobby")
	public Flux<Employee> findByNameAndHobbyName(@RequestParam String name, @RequestParam String hobbyName){
				return employeeService.findByNameAndHobbyName(name,hobbyName);
	}
	@GetMapping("/employee/byNameAndCityAndHobby")
	public Flux<Employee> findByNameAndCityAndHobby(@RequestParam String name,@RequestParam String cityName, @RequestParam String hobbyName){
		
		return employeeService.findByNameAndCityAndHobby(name,cityName,hobbyName);
	}
	
//	@GetMapping("/hobby")
//	public Flux<Hobby> getHobby(){
//		return hobbyRepo.findAll();
//	}
	
}
