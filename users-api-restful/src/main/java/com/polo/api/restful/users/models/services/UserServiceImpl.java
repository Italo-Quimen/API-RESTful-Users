package com.polo.api.restful.users.models.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
	
	@Transactional(readOnly = true)
	@Override
	public List<User> findAll() {
		return (List<User>) userDao.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Optional<User> findById(UUID uuid) {
		return userDao.findById(uuid);
	}

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
    @Transactional
    public Optional<User> update(UUID uuid, User user) {
        Optional<User> userOptional = userDao.findById(uuid);
        if (userOptional.isPresent()) {
        	User userDb = userOptional.orElseThrow();
            
            userDb.setName(user.getName());
            userDb.setEmail(user.getEmail());
            userDb.setPassword(user.getPassword());
            userDb.setPhones(user.getPhones());
            userDb.setModified(user.getModified());
            userDb.setLast_login(user.getLast_login());
            
            return Optional.of(userDao.save(userDb));
            
        }
        return userOptional;
    }
    
    @Transactional
    @Override
    public Optional<User> delete(UUID uuid) {
        Optional<User> userOptional = userDao.findById(uuid);
        userOptional.ifPresent(userDb -> {
        	userDao.delete(userDb);
        });
        return userOptional;
    }

	@Override
	@Transactional(readOnly = true)
	public boolean existsByEmail(String email) {
		return userDao.existsByEmail(email);
	}
}
