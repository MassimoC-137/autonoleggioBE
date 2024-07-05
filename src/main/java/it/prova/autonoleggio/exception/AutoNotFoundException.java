package it.prova.autonoleggio.exception;
	//Eccezione personalizzata per indicare che un'auto non è stata trovata
public class AutoNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public AutoNotFoundException() {
		super("Auto not found");
	}
}