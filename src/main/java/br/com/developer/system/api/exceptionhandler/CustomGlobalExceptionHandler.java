package br.com.developer.system.api.exceptionhandler;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
																  HttpHeaders headers,
																  HttpStatus status,
																  WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", new Date());
		body.put("status", status.value());
		
		body.put("errors", i18n("messagem.invalida"));
		body.put("errorsDev", ex.getMessage());
		
		return handleExceptionInternal(ex, body, headers, HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
																  HttpHeaders headers, 
																  HttpStatus status, 
																  WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", new Date());
		body.put("status", status.value());
		
		//Get all errors
		List<String> errors = ex.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(x -> x.getDefaultMessage())
				.collect(Collectors.toList());
		
		body.put("errors", errors);
		
		return handleExceptionInternal(ex, body, headers, status, request);
	}
	
	@ExceptionHandler({EmptyResultDataAccessException.class})
	public ResponseEntity<Object> handlerEmptyResultDataAccessException(EmptyResultDataAccessException exception, WebRequest request) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", new Date());
		body.put("status",  HttpStatus.NOT_FOUND);
		
		body.put("errors", i18n("messagem.no.content"));
		body.put("errorsDev", exception.getMessage());
	
		return handleExceptionInternal(exception, body, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	private String i18n(String chave) {
		return i18n(chave, null);
	}

	private String i18n(String chave, Object[] args) {
		return messageSource.getMessage(chave, args, LocaleContextHolder.getLocale());
	}
}
