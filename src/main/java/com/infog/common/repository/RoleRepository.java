package com.infog.common.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.infog.common.domain.Role;

public interface RoleRepository extends MongoRepository<Role, String> {
}
