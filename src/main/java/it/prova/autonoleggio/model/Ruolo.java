package it.prova.autonoleggio.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ruolo")
public class Ruolo {
	
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static final String ROLE_CLASSIC_USER = "ROLE_CLASSIC_USER";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "codice")
	private String codice;
	
	public Ruolo(Long id) {
		this.id = id; 
	}
	
	public Ruolo(String codice) {
		this.codice = codice; 
	}
}
