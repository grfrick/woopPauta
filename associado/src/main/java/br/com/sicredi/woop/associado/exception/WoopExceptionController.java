package br.com.sicredi.woop.associado.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class WoopExceptionController {
	
	@ExceptionHandler(value = WoopException.class)
	public ResponseEntity<Object> exception(WoopException exception) {
		return new ResponseEntity<>("Erro Generico - Algo deu errado...", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = WoopAssociadoJaExisteException.class)
	public ResponseEntity<Object> exception(WoopAssociadoJaExisteException exception) {
		return new ResponseEntity<>("Já existe um associado com mesma Matricula.", HttpStatus.BAD_REQUEST);
	}
}