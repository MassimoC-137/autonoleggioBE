package it.prova.autonoleggio.service;

import it.prova.autonoleggio.dto.PrenotazioneDTO;
import it.prova.autonoleggio.model.Prenotazione;
import it.prova.autonoleggio.repository.PrenotazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PrenotazioneService {
    // Iniettato automaticamente da Spring per interagire con il repository Prenotazione
    @Autowired
    private PrenotazioneRepository prenotazioneRepository;
    // Restituisce una lista di tutte le prenotazioni mappate a DTO
    public List<PrenotazioneDTO> findAll() {
        Iterable<Prenotazione> prenotazioni = prenotazioneRepository.findAll();
        return StreamSupport.stream(prenotazioni.spliterator(), false)
                .map(PrenotazioneDTO::buildPrenotazioneDTOFromModel)
                .collect(Collectors.toList());
    }
    // Restituisce una singola prenotazione mappata a DTO dato il suo ID, oppure null se non trovata    
    public PrenotazioneDTO findById(Long id) {
        return prenotazioneRepository.findById(id)
                .map(PrenotazioneDTO::buildPrenotazioneDTOFromModel)
                .orElse(null);
    }
    // Salva una nuova prenotazione nel database e restituisce il DTO della prenotazione salvata
    public PrenotazioneDTO save(PrenotazioneDTO prenotazioneDTO) {
        Prenotazione prenotazione = prenotazioneDTO.buildPrenotazioneModel();
        return PrenotazioneDTO.buildPrenotazioneDTOFromModel(prenotazioneRepository.save(prenotazione));
    }
    // Aggiorna le informazioni di una prenotazione esistente e restituisce il DTO della prenotazione aggiornata
    public PrenotazioneDTO update(PrenotazioneDTO prenotazioneDTO) {
        Prenotazione prenotazione= prenotazioneDTO.buildPrenotazioneModel();
        return PrenotazioneDTO.buildPrenotazioneDTOFromModel(prenotazioneRepository.save(prenotazione));
    }
    // Rimuove una prenotazione dal database dato il suo ID    
    public void delete(Long id) {
        prenotazioneRepository.deleteById(id);
    }
    
}