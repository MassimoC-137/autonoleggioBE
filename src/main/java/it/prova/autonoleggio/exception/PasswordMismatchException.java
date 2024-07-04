package it.prova.autonoleggio.exception;
//Classe di eccezione personalizzata che viene lanciata quando le password non corrispondono
public class PasswordMismatchException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PasswordMismatchException() {
		super("Passwords do not match. Please ensure both password fields are identical");
	}
}