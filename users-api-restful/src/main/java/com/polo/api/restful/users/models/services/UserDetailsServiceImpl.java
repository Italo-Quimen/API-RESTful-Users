package com.polo.api.restful.users.models.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.polo.api.restful.users.models.dao.IUserDao;
import com.polo.api.restful.users.models.entity.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private IUserDao userDao;

	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

		Optional<User> userOptional = userDao.findByName(name);

		if (userOptional.isEmpty()) {
			throw new UsernameNotFoundException(String.format("Username %s no existe en el sistema!", name));
		}

		User user = userOptional.orElseThrow();

		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());

		return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(),
				user.isIsactive(), true, true, true, authorities);
	}
}
