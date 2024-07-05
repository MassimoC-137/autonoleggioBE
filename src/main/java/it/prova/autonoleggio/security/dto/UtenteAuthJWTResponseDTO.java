package it.prova.autonoleggio.security.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
	//DTO per la risposta JWT contenente il token e le informazioni sull'utente
@Getter
@Setter
public class UtenteAuthJWTResponseDTO {
	private String token;
	private String type = "Bearer";
	private String username;
	private String email;
	private List<String> roles;
    // Costruttore per inizializzare i campi della risposta JWT
	public UtenteAuthJWTResponseDTO(String token, String username, String email, List<String> roles) {
		this.token = token;
		this.username = username;
		this.email = email;
		this.roles = roles;
	}

}
