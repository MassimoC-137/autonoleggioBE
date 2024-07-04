package it.prova.autonoleggio.repository;

import org.springframework.data.repository.CrudRepository;

import it.prova.autonoleggio.model.Prenotazione;
//Interfaccia repository per l'entità Prenotazione, estende CrudRepository per fornire operazioni CRUD di base
public interface PrenotazioneRepository extends CrudRepository<Prenotazione, Long> {
}