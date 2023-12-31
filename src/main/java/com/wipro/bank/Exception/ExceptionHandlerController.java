package com.wipro.bank.Exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerController {
//
//	@Autowired
//	ExceptionResponse response;
	
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	@ResponseBody
	ExceptionResponse handleResourceNotFoundException(ResourceNotFoundException exception, HttpServletRequest request)
	{
		ExceptionResponse response = new ExceptionResponse();
		response.setErrorMessage(exception.getMessage());
		response.setRequestURI(request.getRequestURI());
		
		return response;
		
	}
	
	@ExceptionHandler(ResourceNotCreatedException.class)
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	@ResponseBody
	ExceptionResponse handleResourceNotCreatedException(ResourceNotCreatedException exception, HttpServletRequest request)
	{
		ExceptionResponse response = new ExceptionResponse();
		response.setErrorMessage(exception.getMessage());
		response.setRequestURI(request.getRequestURI());
		
		return response;
		
	}
}
