package it.prova.autonoleggio.security.dto;

import lombok.Getter;
import lombok.Setter;
	//DTO per l'autenticazione dell'utente contenente username e password
@Getter
@Setter
public class UtenteAuthDTO {
	private String username;
	private String password;

}