package br.com.sicredi.woop.pauta.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class VotoWoopException extends ResponseStatusException {
	private static final long serialVersionUID = 4876409883390877541L;

    public VotoWoopException(HttpStatus badRequest, String string) {
        super(badRequest, string);
    }
}