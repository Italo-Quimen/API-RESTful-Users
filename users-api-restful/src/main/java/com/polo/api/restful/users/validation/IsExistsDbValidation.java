package com.polo.api.restful.users.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.polo.api.restful.users.models.services.IUserService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class IsExistsDbValidation implements ConstraintValidator<IsExistsDb, String> {

	@Autowired
	private IUserService service;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return !service.existsByEmail(value);
	}
}
