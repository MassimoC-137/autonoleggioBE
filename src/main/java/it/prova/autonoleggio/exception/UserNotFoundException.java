package it.prova.autonoleggio.exception;
//Classe di eccezione personalizzata che viene lanciata quando un utente non viene trovato
public class UserNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UserNotFoundException() {
		super("User not found");
	}
}