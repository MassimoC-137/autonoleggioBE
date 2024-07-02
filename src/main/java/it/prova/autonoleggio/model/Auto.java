package it.prova.autonoleggio.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
@Table(name = "auto")
public class Auto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id; 
	@Column(name = "targa")
	private String targa; 
	@Enumerated(EnumType.STRING)
	private TipologiaAuto tipologiaAuto; 
	@Column(name = "marca")
	private String marca; 
	@Column(name = "modello")
	private String modello; 
	@Column(name = "cilindrata")
	private Integer cilindrata; 
	@Enumerated(EnumType.STRING)
	private TipologiaMotore tipologiaMotore; 
	@Column(name = "numero_posti")
	private Integer numeroPosti; 
	@Column(name = "cambio_automatico")
	private Boolean cambioAutomatico;
	@Column(name = "data_immatricolazione")
	private LocalDate dataImmatricolazione; 
	@Column(name = "descrizione")
	private String descrizione; 
	@Column(name = "prezzo_per_giornata")
	private Float prezzoPerGiornata;
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "auto")
	private Set <Utente> utente = new HashSet<>();
	
}
