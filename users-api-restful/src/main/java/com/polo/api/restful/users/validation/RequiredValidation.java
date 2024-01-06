package com.polo.api.restful.users.validation;

import org.springframework.util.StringUtils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RequiredValidation implements ConstraintValidator<IsRequired, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return StringUtils.hasText(value);
	}
}
