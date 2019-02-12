package br.com.sicredi.woop.associado.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class WoopException extends ResponseStatusException {
	private static final long serialVersionUID = 4489409883390877541L;

    public WoopException(HttpStatus badRequest, String string) {
        super(badRequest, string);
    }
}