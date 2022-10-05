package guru.springfamework.controllers;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import guru.springfamework.Exceptions.NotFoundException;
import guru.springfamework.api.v1.model.ErrorDTO;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	//this generic exception handler should be in @ControllerAdvice EntityNotFoundException
	@ExceptionHandler({EntityNotFoundException.class, EmptyResultDataAccessException.class,
					   NotFoundException.class})
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
	
	//@ExceptionHandler(MethodArgumentNotValidException.class) Spring already has this mapped to 
	// handleMethodArgumentNotValid so to add custom handling i need to overwrite this
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, 
			                                                   HttpHeaders headers, HttpStatus status,
			                                                   WebRequest request) {
		MethodArgumentNotValidException mArgEx = (MethodArgumentNotValidException)ex;
		StringBuilder sb = new StringBuilder();
		for(FieldError err : mArgEx.getFieldErrors()) {
			sb.append(err.getField())
			  .append(":")
			  .append(err.getDefaultMessage())
			  .append("| |");
		}
		ErrorDTO errorMsg = ErrorDTO.builder()
									.message(String.format("400 Wrong input parameters"))
								    .url(request.getDescription(false).replaceFirst("uri=", ""))
									.extraInfo(sb.toString())
				                    .build();
		return new ResponseEntity<>(errorMsg, HttpStatus.BAD_REQUEST);
	}
	
	
}
