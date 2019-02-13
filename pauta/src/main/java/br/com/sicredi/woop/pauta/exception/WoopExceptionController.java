package br.com.sicredi.woop.pauta.exception;

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

	@ExceptionHandler(value = WoopPautaNaoLocalizadaException.class)
	public ResponseEntity<Object> exception(WoopPautaNaoLocalizadaException exception) {
		return new ResponseEntity<>("Pauta não foi localizada.", HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = WoopSessaoNaoLocalizadaException.class)
	public ResponseEntity<Object> exceptionSessaoNaoLocalizada(WoopSessaoNaoLocalizadaException exception) {
		return new ResponseEntity<>("Sessao não foi localizada.", HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = WoopSessaoJaIniciadaException.class)
	public ResponseEntity<Object> exceptionSessaoJaIniciada(WoopSessaoJaIniciadaException exception) {
		return new ResponseEntity<>("Sessao já iniciada anteriormente.", HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = WoopSessaoAbertaException.class)
	public ResponseEntity<Object> exceptionSessaoAberta(WoopSessaoAbertaException exception) {
		return new ResponseEntity<>("Sessao já iniciada anteriormente. Aguarde encerrar para ver o resultado final.", HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = WoopAssociadoForaDoArException.class)
	public ResponseEntity<Object> exceptionPautaNaoLocalizada(WoopAssociadoForaDoArException exception) {
		return new ResponseEntity<>("Busca por Associado esta off-line", HttpStatus.BAD_GATEWAY);
	}
	
	@ExceptionHandler(value = WoopAssociadoNaoLocalizadaException.class)
	public ResponseEntity<Object> exceptionPautaNaoLocalizada(WoopAssociadoNaoLocalizadaException exception) {
		return new ResponseEntity<>("Associado não encontrado Matricula inválida", HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = WoopSessaoEncerradaException.class)
	public ResponseEntity<Object> exceptionSessaoJaIniciada(WoopSessaoEncerradaException exception) {
		return new ResponseEntity<>("A sessão já encerrou, não é mais possivel votar. Seja mais rapido da próxima vez =]", HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = WoopVotoJaRealizadoException.class)
	public ResponseEntity<Object> exceptionSessaoJaIniciada(WoopVotoJaRealizadoException exception) {
		return new ResponseEntity<>("Associado já votou na Pauta.", HttpStatus.BAD_REQUEST);
	}
}