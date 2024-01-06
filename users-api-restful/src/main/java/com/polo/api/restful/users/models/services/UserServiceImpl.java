package com.polo.api.restful.users.models.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.polo.api.restful.users.models.dao.IRoleDao;
import com.polo.api.restful.users.models.dao.IUserDao;
import com.polo.api.restful.users.models.entity.Role;
import com.polo.api.restful.users.models.entity.User;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private IRoleDao roleDao;

	@Autowired
	private IUserDao userDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public User save(User user) {
		Optional<Role> optionalRoleUser = roleDao.findByName("ROLE_USER");
		List<Role> roles = new ArrayList<>();

		optionalRoleUser.ifPresent(roles::add);

		if (user.isAdmin()) {
			Optional<Role> optionalRoleAdmin = roleDao.findByName("ROLE_ADMIN");
			optionalRoleAdmin.ifPresent(roles::add);
		}

		user.setRoles(roles);
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		return userDao.save(user);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean existsByEmail(String email) {
		return userDao.existsByEmail(email);
	}
}
