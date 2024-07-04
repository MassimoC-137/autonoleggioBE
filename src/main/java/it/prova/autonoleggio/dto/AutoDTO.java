package it.prova.autonoleggio.dto;

import java.time.LocalDate;
import java.util.List;

import it.prova.autonoleggio.model.Auto;
import it.prova.autonoleggio.model.TipologiaAuto;
import it.prova.autonoleggio.model.TipologiaMotore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AutoDTO {

	private Long id;

	@NotBlank(message = "{targa.notblank}")
	private String targa;

	@NotNull(message = "{tipologia.notnull}")
	private TipologiaAuto tipologiaAuto;

	@NotBlank(message = "{marca.notblank}")
	private String marca;

	@NotBlank(message = "{modello.notblank}")
	private String modello;

	@NotNull(message = "{tipologiaMotore.notnull}")
	private TipologiaMotore tipologiaMotore;

	@NotNull(message = "{cilindrata.notnull}")
	private Integer cilindrata;

	@NotNull(message = "{numeroPosti.notnull}")
	private Integer numeroPosti;

	@NotNull(message = "{cambioAutomatico.notnull}")
	private Boolean cambioAutomatico;

	@NotNull(message = "{dataImmatricolazione.notnull}")
	private LocalDate dataImmatricolazione;

	@NotBlank(message = "{descrizione.notblank}")
	private String descrizione;

	@NotNull(message = "{prezzoPerGiornata.notnull}")
	private Float prezzoPerGiornata;

	private List<PrenotazioneDTO> prenotazioni;
	// Costruttore che inizializza tutti i campi della classe
	public AutoDTO(Long id, String targa, TipologiaAuto tipologiaAuto, String marca, String modello,
			TipologiaMotore tipologiaMotore, Integer cilindrata, Integer numeroPosti, Boolean cambioAutomatico,
			LocalDate dataImmatricolazione, String descrizione, Float prezzoPerGiornata) {
		this.id = id;
		this.targa = targa;
		this.tipologiaAuto = tipologiaAuto;
		this.marca = marca;
		this.modello = modello;
		this.tipologiaMotore = tipologiaMotore;
		this.cilindrata = cilindrata;
		this.numeroPosti = numeroPosti;
		this.cambioAutomatico = cambioAutomatico;
		this.dataImmatricolazione = dataImmatricolazione;
		this.descrizione = descrizione;
		this.prezzoPerGiornata = prezzoPerGiornata;
	}
	// Costruisce un oggetto Auto dal DTO corrente
	public Auto buildAutoModel() {
		return Auto.builder().id(this.id).targa(this.targa).tipologiaAuto(this.tipologiaAuto).marca(this.marca)
				.modello(this.modello).tipologiaMotore(this.tipologiaMotore).cilindrata(this.cilindrata)
				.numeroPosti(this.numeroPosti).cambioAutomatico(this.cambioAutomatico)
				.dataImmatricolazione(this.dataImmatricolazione).descrizione(this.descrizione)
				.prezzoPerGiornata(this.prezzoPerGiornata).build();
	}
	// Costruisce un DTO Auto dall'oggetto Auto fornito
	public static AutoDTO buildAutoDTOFromModel(Auto model) {
		return new AutoDTO(model.getId(), model.getTarga(), model.getTipologiaAuto(), model.getMarca(),
				model.getModello(), model.getTipologiaMotore(), model.getCilindrata(), model.getNumeroPosti(),
				model.getCambioAutomatico(), model.getDataImmatricolazione(), model.getDescrizione(),
				model.getPrezzoPerGiornata());
	}
}