package com.nisum.webflux.controller;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.nisum.webflux.model.Address;
import com.nisum.webflux.model.City;
import com.nisum.webflux.model.Employee;
import com.nisum.webflux.model.Hobby;
import com.nisum.webflux.repository.EmployeeRepo;
import com.nisum.webflux.service.IEmployeeService;
import com.nisum.webflux.service.impl.TestConfig;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureWebTestClient
@ContextConfiguration(classes = TestConfig.class)

/*
 * Use of Mockito for component tests using webtestClient;
 */

public class EmployeeControllerComponentTest {
	
	
	@Autowired
	WebTestClient webTestClient;

	@Autowired
	EmployeeRepo empRepo;

	@Before
	public void setup() {
		
	}
	@Test
    public void saveUsingWebTestClientAndMockito() {
		
		ArgumentCaptor<Employee> requestCaptor = ArgumentCaptor.forClass(
                Employee.class);
		when(empRepo.save(requestCaptor.capture())).thenReturn(Mono.error(new RuntimeException()));
    	webTestClient.post().uri("/employee").contentType(MediaType.APPLICATION_JSON_UTF8).body(getMonEmployee(),Employee.class)
    	.exchange()
    	.expectStatus().isOk().expectBody().jsonPath("$.id").isEqualTo("2");
//    	.jsonPath("$.description").isEqualTo("xx").jsonPath("$.price").isEqualTo(34.4);
    	}
	@Test
    public void saveUsingWebTestClientAndMockitoAndStepVerifier() {
		
		ArgumentCaptor<Employee> requestCaptor = ArgumentCaptor.forClass(
                Employee.class);
		when(empRepo.save(requestCaptor.capture())).thenReturn(Mono.error(new RuntimeException()));
    	Employee employees=webTestClient.post().uri("/employee").contentType(MediaType.APPLICATION_JSON_UTF8).body(getMonEmployee(),Employee.class)
    	.exchange()
    	.expectStatus().isOk().expectBody(Employee.class).returnResult().getResponseBody();
    	StepVerifier.create(Flux.just(employees)).expectSubscription()
    	.expectNextMatches(e-> e.getName().equals("servicename")
    	).verifyComplete();
//    	.jsonPath("$.description").isEqualTo("xx").jsonPath("$.price").isEqualTo(34.4);
    	}

	@Test
	public void getAll_UsingWebClientComponentTestCaseMockito() {
		when(empRepo.findAll()).thenReturn(Flux.error(new RuntimeException()));
		webTestClient.get().uri("/employee").exchange()
		.expectStatus().isOk()
		.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
		.expectBodyList(Employee.class)
		.hasSize(1);
		//hasSize(7);
	}
	@Test
	public void getAll_UsingWebClientUsingStepVerifier() {
		when(empRepo.findAll()).thenReturn(Flux.error(new RuntimeException()));
		Flux<Employee> employeeFlux=webTestClient.get().uri("/employee").exchange()
		.expectStatus().isOk()
		.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
	    .returnResult(Employee.class)
	    .getResponseBody();
		
		StepVerifier.create(employeeFlux.log()).expectSubscription()
		.expectNextMatches(e->{
			return e.getName().equals("servicename");
		}).verifyComplete();
	}
	private Flux<Employee> getMockEmployee() {
		
		List<Hobby> hobbyObj=new ArrayList<>();
		hobbyObj.add(new Hobby("1","cricket"));
		List<City> cities=Arrays.asList(new City("1","patna"));
		List<Address> address =Arrays.asList(new Address("1","bhr",cities));
		return Flux.just(new Employee("1","abc",address,hobbyObj));
	}
	private Mono<Employee> getMonEmployee() {
		List<Hobby> hobbyObj=new ArrayList<>();
		hobbyObj.add(new Hobby("1","cricket"));
		List<City> cities=Arrays.asList(new City("1","patna"));
		List<Address> address =Arrays.asList(new Address("1","bhr",cities));

		return Mono.just(new Employee("1","testName",address,hobbyObj));
		
	}
	
	
}

