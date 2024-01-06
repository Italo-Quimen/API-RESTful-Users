package com.polo.api.restful.users.models.services;

import com.polo.api.restful.users.models.entity.User;

public interface IUserService {

	public User save(User user);

	boolean existsByEmail(String email);
}
