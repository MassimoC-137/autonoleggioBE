package it.prova.autonoleggio.exception;
	//Eccezione personalizzata per indicare che una prenotazione non è stata trovata
public class PrenotazioneNotfoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public PrenotazioneNotfoundException() {
		super("Prenotazione not found");
	}
}