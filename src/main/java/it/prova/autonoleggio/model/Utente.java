package it.prova.autonoleggio.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "utente")
public class Utente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotEmpty
	@Column(name = "username")
	private String username;

	@NotEmpty
	@Column(name = "password")
	private String password;
	
	@NotEmpty
	@Column(name = "conferma_password")
	private String confermaPassword;

	@Email
	@NotEmpty
	@Column(name = "email")
	private String email;

	@NotEmpty
	@Column(name = "nome")
	private String nome;

	@Column(name = "attivo")
	private Boolean attivo;
	
	@NotEmpty
	@Column(name = "cognome")
	private String cognome;

	@NotNull
	@Column(name = "data_conseguimento_patente")
	private LocalDate dataConseguimentoPatente;

	@Positive
	@Column(name = "credito_disponibile")
	private Float creditoDisponibile;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "utente_auto", joinColumns = @JoinColumn(name = "utente_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "auto_id", referencedColumnName = "id"))
	private Set<Auto> auto = new HashSet<>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "utente")
	private List<Prenotazione> prenotazioni;

	@ManyToMany
	@JoinTable(name = "utente_ruolo", joinColumns = @JoinColumn(name = "utente_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "ruolo_id", referencedColumnName = "id"))
	private Set<Ruolo> ruoli = new HashSet<>();
}