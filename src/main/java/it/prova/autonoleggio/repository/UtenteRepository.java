package it.prova.autonoleggio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.prova.autonoleggio.dto.UtenteDTO;
import it.prova.autonoleggio.model.Utente;
	//Interfaccia repository per l'entità Utente, estende CrudRepository e JpaRepository per fornire operazioni CRUD avanzate
public interface UtenteRepository extends CrudRepository<Utente, Long>, JpaRepository<Utente, Long> {
	// Utilizza EntityGraph per caricare l'utente insieme ai suoi ruoli
    @EntityGraph(attributePaths = { "ruoli" })
    Optional<Utente> findByUsername(String username);
    // Query personalizzata per trovare un utente con i suoi ruoli dato il suo ID
    @Query("from Utente u left join fetch u.ruoli where u.id = ?1")
    Optional<Utente> findByIdConRuoli(Long id);
    // Utilizza EntityGraph per caricare l'utente insieme ai suoi ruoli e trovare un utente dato il suo username e password
    @EntityGraph(attributePaths = { "ruoli" })
    UtenteDTO findByUsernameAndPassword(String username, String password);
}