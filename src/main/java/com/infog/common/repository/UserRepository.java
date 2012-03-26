package com.infog.common.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.infog.common.domain.User;

public interface UserRepository extends MongoRepository<User, String> {
	
	User findByUsername(String username);
}
