package com.polo.api.restful.users.models.dao;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

import com.polo.api.restful.users.models.entity.User;

public interface IUserDao extends CrudRepository<User, UUID> {

	Optional<User> findById(UUID uuid);

	boolean existsByName(String name);

	Optional<User> findByName(String name);

	boolean existsByEmail(String email);
}
