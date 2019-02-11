package br.com.sicredi.woop.pauta.exception;

public class WoopException extends RuntimeException {
	private static final long serialVersionUID = 4489409883390877541L;

	public WoopException(String msg) {
        super(msg);
    }

    public WoopException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

