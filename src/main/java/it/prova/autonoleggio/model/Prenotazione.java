package it.prova.autonoleggio.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table
public class Prenotazione {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@Column(name = "data_inizio")
	private LocalDate dataInizio;
	@Column(name = "data_fine")
	private LocalDate dataFine;
	@Column(name = "annullata")
	private Boolean annullata; 
	
	@ManyToOne
	@JoinColumn(name = "utente_id", referencedColumnName = "id")
	private Utente utente;
	@ManyToOne
	@JoinColumn(name = "auto_id", referencedColumnName = "id")
	private Auto auto;
	
}
