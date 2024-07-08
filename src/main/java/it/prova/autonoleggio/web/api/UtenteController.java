package it.prova.autonoleggio.web.api;

import it.prova.autonoleggio.dto.UtenteDTO;
import it.prova.autonoleggio.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utente")
public class UtenteController {

    @Autowired
    private UtenteService utenteService;

    @GetMapping
    public List<UtenteDTO> listAll() {
        return utenteService.listAllUtenti();
    }

    @GetMapping("/{id}")
    public UtenteDTO findById(@PathVariable Long id) {
        return utenteService.caricaSingoloUtente(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody UtenteDTO utenteDTO) {
        utenteService.inserisciNuovo(utenteDTO);
    }

    @PutMapping("/{id}")
    public UtenteDTO update(@PathVariable Long id, @RequestBody UtenteDTO utenteDTO) {
        utenteDTO.setId(id);
        return utenteService.aggiorna(utenteDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        utenteService.rimuovi(id);
    }
}