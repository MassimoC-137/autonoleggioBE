package it.prova.autonoleggio.repository;

import org.springframework.data.repository.CrudRepository;

import it.prova.autonoleggio.model.Ruolo;
//Interfaccia repository per l'entità Ruolo, estende CrudRepository per fornire operazioni CRUD di base
public interface RuoloRepository extends CrudRepository<Ruolo, Long> {
	// Metodo per trovare un ruolo in base al suo codice
    Ruolo findByCodice(String codice);
}