package it.prova.autonoleggio.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
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

	@Column(name = "targa", unique = true, nullable = false)
	private String targa;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipologia_auto", nullable = false)
	private TipologiaAuto tipologiaAuto;

	@Column(name = "marca", nullable = false)
	private String marca;

	@Column(name = "modello", nullable = false)
	private String modello;

	@Column(name = "cilindrata", nullable = false)
	private Integer cilindrata;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipologia_motore", nullable = false)
	private TipologiaMotore tipologiaMotore;

	@Column(name = "numero_posti", nullable = false)
	private Integer numeroPosti;

	@Column(name = "cambio_automatico")
	private Boolean cambioAutomatico;

	@Column(name = "data_immatricolazione", nullable = false)
	private LocalDate dataImmatricolazione;

	@Column(name = "descrizione")
	private String descrizione;

	@Column(name = "prezzo_per_giornata", nullable = false)
	private Float prezzoPerGiornata;

	@ManyToMany(mappedBy = "auto")
	private Set<Utente> utenti = new HashSet<>();
}