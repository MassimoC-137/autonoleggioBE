package it.prova.autonoleggio.dto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import it.prova.autonoleggio.model.Ruolo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RuoloDTO {

	private Long id;
	private String codice;
	// Costruisce un oggetto Ruolo dal DTO corrente
	public Ruolo buildRuoloModel() {
		return Ruolo.builder().id(this.id).codice(this.codice).build();
	}
	// Costruisce un DTO Ruolo dall'oggetto Ruolo fornito
	public static RuoloDTO buildRuoloDTOFromModel(Ruolo ruoloModel) {
		return new RuoloDTO(ruoloModel.getId(), ruoloModel.getCodice());
	}
	// Crea una lista di RuoloDTO a partire da un Set di oggetti Ruolo
	public static List<RuoloDTO> createRuoloDTOListFromModelSet(Set<Ruolo> modelListInput) {
		return modelListInput.stream().map(ruoloEntity -> {
			return RuoloDTO.buildRuoloDTOFromModel(ruoloEntity);
		}).collect(Collectors.toList());
	}
	// Crea una lista di RuoloDTO a partire da una lista di oggetti Ruolo
	public static List<RuoloDTO> createRuoloDTOListFromModelList(List<Ruolo> modelListInput) {
		return modelListInput.stream().map(ruoloEntity -> {
			return RuoloDTO.buildRuoloDTOFromModel(ruoloEntity);
		}).collect(Collectors.toList());
	}
	
	public RuoloDTO(Long id) {
		this.id = id; 
	}
	
	public RuoloDTO(String codice) {
		this.codice = codice; 
	}
}