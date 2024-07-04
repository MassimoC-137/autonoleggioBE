package it.prova.autonoleggio.exception;
//Classe di eccezione personalizzata che viene lanciata quando l'ID non è null durante un'operazione di inserimento
public class IdNotNullForInsertException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public IdNotNullForInsertException() {
		super("Id must be null for insert operation");
	}
}