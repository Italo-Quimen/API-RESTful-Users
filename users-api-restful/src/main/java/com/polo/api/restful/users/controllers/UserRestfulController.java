package com.polo.api.restful.users.controllers;

import static com.polo.api.restful.users.security.TokenJwtConfig.SECRET_KEY;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.polo.api.restful.users.models.entity.User;
import com.polo.api.restful.users.models.services.IUserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserRestfulController {

	@Autowired
	private IUserService userService;

	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result) {
		if (result.hasFieldErrors()) {
			return validation(result);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody User user, BindingResult result) {

		user.setAdmin(false);

		try {
			Claims claims = Jwts.claims()
					.add("authorities", new ObjectMapper().writeValueAsString("ROLE_USER"))
					.add("username", user.getName()).build();

			String token = Jwts.builder()
					.subject(user.getName())
					.claims(claims)
					.expiration(new Date(System.currentTimeMillis() + 3600000)).issuedAt(new Date())
					.signWith(SECRET_KEY)
					.compact();

			user.setToken(token);

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return create(user, result);
	}

	private ResponseEntity<?> validation(BindingResult result) {
		Map<String, String> errors = new HashMap<>();

		result.getFieldErrors().forEach(err -> {
			errors.put("Mensaje", "El campo " + err.getField() + " " + err.getDefaultMessage());
		});
		return ResponseEntity.badRequest().body(errors);
	}
}
