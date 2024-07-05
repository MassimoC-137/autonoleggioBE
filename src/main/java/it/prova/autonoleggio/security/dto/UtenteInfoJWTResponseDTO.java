package it.prova.autonoleggio.security.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
	//DTO per la risposta JWT contenente le informazioni dettagliate sull'utente
@Getter
@Setter
public class UtenteInfoJWTResponseDTO {

	private String nome;
	private String cognome;
	private String type = "Bearer";
	private String username;
	private String email;
	private List<String> ruoli;
    // Costruttore per inizializzare i campi della risposta con le informazioni dell'utente
	public UtenteInfoJWTResponseDTO(String nome, String cognome, String username, String email, List<String> ruoli) {
		this.nome = nome;
		this.cognome = cognome;
		this.username = username;
		this.email = email;
		this.ruoli = ruoli;
	}

}