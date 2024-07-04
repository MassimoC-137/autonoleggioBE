package it.prova.autonoleggio.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.autonoleggio.dto.AutoDTO;
import it.prova.autonoleggio.model.Auto;
import it.prova.autonoleggio.repository.AutoRepository;

@Service
@Transactional
public class AutoService {
    // Iniettato automaticamente da Spring per interagire con il repository Auto
    @Autowired
    private AutoRepository autoRepository;
    // Restituisce una lista di tutte le auto mappate a DTO
    public List<AutoDTO> findAll() {
        Iterable<Auto> auto = autoRepository.findAll();
        return StreamSupport.stream(auto.spliterator(), false)
                .map(AutoDTO::buildAutoDTOFromModel)
                .collect(Collectors.toList());
    }
    // Restituisce una singola auto mappata a DTO dato il suo ID, oppure null se non trovata
    public AutoDTO findById(Long id) {
        return autoRepository.findById(id)
                .map(AutoDTO::buildAutoDTOFromModel)
                .orElse(null);
    }
    // Salva una nuova auto nel database e restituisce il DTO dell'auto salvata
    public AutoDTO save(AutoDTO autoDTO) {
        Auto auto = autoDTO.buildAutoModel();
        return AutoDTO.buildAutoDTOFromModel(autoRepository.save(auto));
    }
    // Aggiorna le informazioni di un'auto esistente e restituisce il DTO dell'auto aggiornata
    public AutoDTO update(AutoDTO autoDTO) {
        Auto auto = autoDTO.buildAutoModel();
        return AutoDTO.buildAutoDTOFromModel(autoRepository.save(auto));
    }
    // Rimuove un'auto dal database dato il suo ID
    public void delete(Long id) {
        autoRepository.deleteById(id);
    }
}