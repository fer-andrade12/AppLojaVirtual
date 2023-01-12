package com.App.LojaVirtual;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.App.LojaVirtual.model.objetoErroDTO.ObjetoErroDTO;

/* Capitura excecoes do projeto*/
@RestControllerAdvice
@ControllerAdvice
public class ControleExcecoes extends ResponseEntityExceptionHandler{
	
	// exceção customisada
	@ExceptionHandler(ExceptionLojaVirtual.class)
	public ResponseEntity<Object> HandleExceptionCustom(ExceptionLojaVirtual ex) {
		
		ObjetoErroDTO objetoErroDto = new ObjetoErroDTO();
		
		objetoErroDto.setErro(ex.getMessage());
		objetoErroDto.setCode(HttpStatus.OK.toString());
		
		return new ResponseEntity<Object>(objetoErroDto, HttpStatus.OK);
		
	}
	
	
	/* Capitura excecoes handleExceptionInternal*/
	@ExceptionHandler({Exception.class, RuntimeException.class, Throwable.class})
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		// Objeto erro DTO para usar dados temporário para essa exceções
		ObjetoErroDTO objetoErroDto = new ObjetoErroDTO();

		String msg ="";
		
		if (ex instanceof MethodArgumentNotValidException) {
			
			List<ObjectError> list = ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors();
			
			for (ObjectError objectError : list) {
				msg += objectError.getDefaultMessage() + "/n";
			}
			
		} else {
			msg = ex.getMessage();
		}
		
		objetoErroDto.setErro(msg);
		objetoErroDto.setCode(status.value() + "==>" + status.getReasonPhrase());
		
		return new ResponseEntity<Object>(objetoErroDto, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	/* Capitura excecoes HandleExceptionDataIntegry*/
	@ExceptionHandler({DataIntegrityViolationException.class, ConstraintViolationException.class, SQLException.class})
	protected ResponseEntity<Object> HandleExceptionDataIntegry(Exception ex) {
		
		ObjetoErroDTO objetoErroDto = new ObjetoErroDTO();

		String msg ="";
		
		if(ex instanceof SQLException) {
			msg = "Erro de SQL no banco" + ((SQLException) ex).getCause().getCause().getMessage();
		}else {
			msg = ex.getMessage();
		}if(ex instanceof DataIntegrityViolationException) {
			msg = "Erro de Data Integrity Violation Exception no banco" + ((DataIntegrityViolationException) ex).getCause().getCause().getMessage();
		}else {
			msg = ex.getMessage();
		}if(ex instanceof ConstraintViolationException) {
			msg = "Erro de Constraint Violation Exception no banco" + ((ConstraintViolationException) ex).getCause().getCause().getMessage();
		}else {
			msg = ex.getMessage();
		}
		
		return new ResponseEntity<Object>(objetoErroDto, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	
	
	
	
	
	
}