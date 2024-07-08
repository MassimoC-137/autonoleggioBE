package it.prova.autonoleggio.web.api;

import it.prova.autonoleggio.dto.AutoDTO;
import it.prova.autonoleggio.service.AutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auto")
public class AutoController {

    @Autowired
    private AutoService autoService;

    @GetMapping
    public List<AutoDTO> listAll() {
        return autoService.findAll();
    }

    @GetMapping("/{id}")
    public AutoDTO findById(@PathVariable Long id) {
        return autoService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AutoDTO create(@RequestBody AutoDTO autoDTO) {
        return autoService.save(autoDTO);
    }

    @PutMapping("/{id}")
    public AutoDTO update(@PathVariable Long id, @RequestBody AutoDTO autoDTO) {
        autoDTO.setId(id);
        return autoService.update(autoDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        autoService.delete(id);
    }
}