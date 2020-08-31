package com.nisum.webflux.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.nisum.webflux.model.Hobby;

public interface HobbyRepo extends ReactiveMongoRepository<Hobby, String> {

}
