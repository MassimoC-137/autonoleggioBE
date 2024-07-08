package it.prova.autonoleggio.web.api;

import it.prova.autonoleggio.dto.PrenotazioneDTO;
import it.prova.autonoleggio.service.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prenotazione")
public class PrenotazioneController {

    @Autowired
    private PrenotazioneService prenotazioneService;

    @GetMapping
    public List<PrenotazioneDTO> listAll() {
        return prenotazioneService.findAll();
    }

    @GetMapping("/{id}")
    public PrenotazioneDTO findById(@PathVariable Long id) {
        return prenotazioneService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PrenotazioneDTO create(@RequestBody PrenotazioneDTO prenotazioneDTO) {
        return prenotazioneService.save(prenotazioneDTO);
    }

    @PutMapping("/{id}")
    public PrenotazioneDTO update(@PathVariable Long id, @RequestBody PrenotazioneDTO prenotazioneDTO) {
        prenotazioneDTO.setId(id);
        return prenotazioneService.update(prenotazioneDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        prenotazioneService.delete(id);
    }
}