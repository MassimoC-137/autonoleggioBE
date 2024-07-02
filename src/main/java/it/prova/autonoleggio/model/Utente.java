package it.prova.autonoleggio.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
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
@Table(name = "utente")
public class Utente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id; 
	@Column(name = "username")
	private String username; 
	@Column(name = "password")
	private String password; 
	@Column(name = "conferma_password")
	private String confermaPassword; 
	@Column(name = "email")
	private String email; 
	@Column(name = "nome")
	private String nome; 
	@Column(name = "cognome")
	private String cognome; 
	@Column(name = "data_conseguimento_patente")
	private LocalDate dataConseguimentoPatente; 
	@Column(name = "credito_disponibile")
	private float creditoDisponibile;
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "utente")
	private Set <Auto> auto = new HashSet<>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "utente")
	private List<Prenotazione> prenotazioni = new ArrayList<>();
	
	@ManyToMany
	@JoinTable(name = "utente_ruolo", 
	joinColumns = @JoinColumn(name = "utente_id", referencedColumnName = "id"), 
	inverseJoinColumns = @JoinColumn(name = "ruolo_id", referencedColumnName = "id"))
	private Set<Ruolo> ruoli = new HashSet<>(0);
}
