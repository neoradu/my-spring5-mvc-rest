package guru.springframework.controllers;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import guru.springframework.api.v1.model.ErrorDTO;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	//this generic exception handler should be in @ControllerAdvice EntityNotFoundException
	@ExceptionHandler({EntityNotFoundException.class, EmptyResultDataAccessException.class})
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<ErrorDTO> handleNotFound(HttpServletRequest req, Exception ex) {
		ErrorDTO errorMsg = ErrorDTO.builder()
									.message(String.format("404 Not Found"))
									.url(req.getServletPath())
									.extraInfo(ex.getMessage())
									.build();
		
		return new ResponseEntity<>(errorMsg, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler({java.lang.NumberFormatException.class})
	public ResponseEntity<ErrorDTO> wrongInputParams(HttpServletRequest req, Exception ex) {
		ErrorDTO errorMsg = ErrorDTO.builder()
									.message(String.format("400 Wrong input parameters"))
									.url(req.getServletPath())
									.extraInfo(ex.getMessage())
									.build();
		
		return new ResponseEntity<>(errorMsg, HttpStatus.BAD_REQUEST);
	}
	
}
