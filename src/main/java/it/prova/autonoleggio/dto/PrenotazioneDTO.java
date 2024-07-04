package it.prova.autonoleggio.dto;

import java.time.LocalDate;

import it.prova.autonoleggio.model.Auto;
import it.prova.autonoleggio.model.Prenotazione;
import it.prova.autonoleggio.model.Utente;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PrenotazioneDTO {

	private Long id;

	@NotNull(message = "{utente.notnull}")
	private Long utenteId;

	@NotNull(message = "{auto.notnull}")
	private Long autoId;

	@NotNull(message = "{dataInizio.notnull}")
	private LocalDate dataInizio;

	@NotNull(message = "{dataFine.notnull}")
	private LocalDate dataFine;

	@NotNull(message = "{annullata.notnull}")
	private Boolean annullata;
	// Costruisce un oggetto Prenotazione dal DTO corrente
	public Prenotazione buildPrenotazioneModel() {
		Utente utente = new Utente();
		utente.setId(this.utenteId);

		Auto auto = new Auto();
		auto.setId(this.autoId);

		return Prenotazione.builder().id(this.id).utente(utente).auto(auto).dataInizio(this.dataInizio)
				.dataFine(this.dataFine).annullata(this.annullata).build();
	}
	// Costruisce un DTO Prenotazione dall'oggetto Prenotazione fornito
	public static PrenotazioneDTO buildPrenotazioneDTOFromModel(Prenotazione model) {
		return new PrenotazioneDTO(model.getId(), model.getUtente().getId(), model.getAuto().getId(),
				model.getDataInizio(), model.getDataFine(), model.getAnnullata());
	}
}