package it.prova.autonoleggio.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.prova.autonoleggio.dto.RuoloDTO;
import it.prova.autonoleggio.exception.IdNotNullForInsertException;
import it.prova.autonoleggio.model.Ruolo;
import it.prova.autonoleggio.repository.RuoloRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class RuoloService {
    // Iniettato automaticamente da Spring per interagire con il repository Ruolo
    @Autowired
    private RuoloRepository ruoloRepository;
    // Restituisce una lista di tutti i ruoli mappati a DTO
    public List<RuoloDTO> listAll() {
        Iterable<Ruolo> ruoli = ruoloRepository.findAll();
        return StreamSupport.stream(ruoli.spliterator(), false)
                .map(RuoloDTO::buildRuoloDTOFromModel)
                .collect(Collectors.toList());
    }
    // Restituisce un singolo ruolo mappato a DTO dato il suo ID, oppure null se non trovato
    public RuoloDTO caricaSingoloElemento(Long id) {
        return ruoloRepository.findById(id)
                .map(RuoloDTO::buildRuoloDTOFromModel)
                .orElse(null);
    }
    // Aggiorna le informazioni di un ruolo esistente e restituisce il DTO del ruolo aggiornato
    public RuoloDTO aggiorna(RuoloDTO ruoloDTO) {
        Ruolo ruolo = ruoloDTO.buildRuoloModel();
        return RuoloDTO.buildRuoloDTOFromModel(ruoloRepository.save(ruolo));
    }
    // Inserisce un nuovo ruolo nel database
    // Verifica che l'ID sia null, altrimenti lancia IdNotNullForInsertException
    public void inserisciNuovo(RuoloDTO ruoloDTO) {
        Ruolo ruolo = ruoloDTO.buildRuoloModel();
        if (ruolo.getId() != null) {
            throw new IdNotNullForInsertException();
        }
        ruoloRepository.save(ruolo);
    }
    // Rimuove un ruolo dal database dato il suo ID
    public void rimuovi(Long idToRemove) {
        ruoloRepository.deleteById(idToRemove);
    }
    // Trova un ruolo dato il suo codice e restituisce il DTO del ruolo trovato
    public RuoloDTO cercaPerCodice(String codice) {
        return RuoloDTO.buildRuoloDTOFromModel(ruoloRepository.findByCodice(codice));
    }
}