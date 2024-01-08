package com.polo.api.restful.users.models.services;

import java.util.Optional;
import java.util.UUID;

import com.polo.api.restful.users.models.entity.User;

public interface IUserService {

	boolean existsByEmail(String email);

	public User save(User user);

	Optional<User> update(UUID uuid, User user);
	
	Optional<User> delete(UUID uuid);

}
