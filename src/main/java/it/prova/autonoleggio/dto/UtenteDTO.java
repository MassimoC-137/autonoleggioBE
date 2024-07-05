package it.prova.autonoleggio.dto;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import it.prova.autonoleggio.model.Ruolo;
import it.prova.autonoleggio.model.Utente;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UtenteDTO {

	private Long id;

	@NotBlank(message = "{username.notblank}")
	@Size(min = 3, max = 16, message = "Il valore inserito '${validatedValue}' deve essere lungo tra {min} e {max} caratteri")
	private String username;

	@NotBlank(message = "{password.notblank}")
	@Size(min = 5, max = 16, message = "Il valore inserito deve essere lungo tra {min} e {max} caratteri")
	private String password;

	private String confermaPassword;

	@Email
	@NotBlank(message = "{email.notblank}")
	private String email;

	@NotBlank(message = "{nome.notblank}")
	private String nome;

	@NotBlank(message = "{cognome.notblank}")
	private String cognome;
	
	@NotNull(message = "{attivo.notblank}")
	private Boolean attivo;

	@NotNull(message = "{dataConseguimentoPatente.notnull}")
	private LocalDate dataConseguimentoPatente;

	@NotNull(message = "{creditoDisponibile.notnull}")
	private Float creditoDisponibile;

	private Set<Long> autoIds;
	private Set<Long> ruoliIds;
	// Costruttore che inizializza i campi principali dell'utente
	public UtenteDTO(Long id, String username, String email, String nome, String cognome,
			LocalDate dataConseguimentoPatente, Float creditoDisponibile) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.nome = nome;
		this.cognome = cognome;
	    this.dataConseguimentoPatente = dataConseguimentoPatente;
	    this.creditoDisponibile = creditoDisponibile;
	}
	// Costruisce un oggetto Utente dal DTO corrente
	// Se includeIdRoles è true, include i ruoli associati
	public Utente buildUtenteModel(boolean includeIdRoles) {
		Utente res = Utente.builder().id(id).username(username).password(password).email(email).nome(nome)
				.cognome(cognome).dataConseguimentoPatente(dataConseguimentoPatente)
				.creditoDisponibile(creditoDisponibile).build();
		if (includeIdRoles && ruoliIds != null) {
			res.setRuoli(ruoliIds.stream().map(id -> {
				Ruolo ruolo = new Ruolo();
				ruolo.setId(id);
				return ruolo;
			}).collect(Collectors.toSet()));
		}
		return res;
	}
	// Costruisce un DTO Utente dall'oggetto Utente fornito
	public static UtenteDTO buildUtenteDTOFromModel(Utente utenteModel) {
		UtenteDTO result = new UtenteDTO(utenteModel.getId(), utenteModel.getUsername(), utenteModel.getEmail(),
				utenteModel.getNome(), utenteModel.getCognome(), utenteModel.getDataConseguimentoPatente(),
				utenteModel.getCreditoDisponibile());
		if (utenteModel.getRuoli() != null) {
			result.setRuoliIds(utenteModel.getRuoli().stream().map(Ruolo::getId).collect(Collectors.toSet()));
		}
		return result;
	}

}