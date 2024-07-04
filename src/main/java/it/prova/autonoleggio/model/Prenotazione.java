package it.prova.autonoleggio.model;

import java.time.LocalDate;

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
@Table(name = "prenotazione")
public class Prenotazione {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "data_inizio", nullable = false)
	private LocalDate dataInizio;

	@Column(name = "data_fine", nullable = false)
	private LocalDate dataFine;

	@Column(name = "annullata")
	private Boolean annullata;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "utente_id", referencedColumnName = "id", nullable = false)
	private Utente utente;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "auto_id", referencedColumnName = "id", nullable = false)
	private Auto auto;
}