package esun.social_media_test.controller;

import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.Errors;

import esun.social_media_test.exception.RequestValidException;

public class ControllerBase {

	protected void validateRequest(Errors errors) throws RequestValidException {
		if (errors == null) {
			return;
		}
		if (errors.hasErrors()) {
			throw new RequestValidException(
					(String) errors.getAllErrors().stream().collect(Collectors
							.mapping(DefaultMessageSourceResolvable::getDefaultMessage, Collectors.joining(";"))),
					errors);
		}
	}
}
