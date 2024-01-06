package com.polo.api.restful.users.models.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.polo.api.restful.users.models.entity.Role;

public interface IRoleDao extends CrudRepository<Role, Long> {

	Optional<Role> findByName(String name);
}
