package it.prova.autonoleggio.exception;
//Classe di eccezione personalizzata che viene lanciata quando il credito disponibile di un utente non è zero
public class CreditMustBeZeroException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public CreditMustBeZeroException() {
		super("Credit must be 0");
	}
}