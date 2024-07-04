
package it.prova.autonoleggio.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.prova.autonoleggio.model.Auto;
import it.prova.autonoleggio.model.TipologiaAuto;
import it.prova.autonoleggio.model.TipologiaMotore;
//Interfaccia repository per l'entità Auto, estende CrudRepository per fornire operazioni CRUD di base
public interface AutoRepository extends CrudRepository<Auto, Long> {
    // Query personalizzata per trovare auto in base a diversi criteri di ricerca.
    // Utilizza parametri opzionali per costruire dinamicamente la query.
    @Query("SELECT a FROM Auto a WHERE " +
           "(:targa IS NULL OR a.targa = :targa) " +
           "AND (:tipologiaAuto IS NULL OR a.tipologiaAuto = :tipologiaAuto) " +
           "AND (:marca IS NULL OR a.marca = :marca) " +
           "AND (:modello IS NULL OR a.modello = :modello) " +
           "AND (:cilindrata IS NULL OR a.cilindrata = :cilindrata) " +
           "AND (:tipologiaMotore IS NULL OR a.tipologiaMotore = :tipologiaMotore) " +
           "AND (:numeroPosti IS NULL OR a.numeroPosti = :numeroPosti) " +
           "AND (:cambioAutomatico IS NULL OR a.cambioAutomatico = :cambioAutomatico) " +
           "AND (:dataImmatricolazione IS NULL OR a.dataImmatricolazione = :dataImmatricolazione) " +
           "AND (:descrizione IS NULL OR a.descrizione = :descrizione) " +
           "AND (:prezzoPerGiornata IS NULL OR a.prezzoPerGiornata = :prezzoPerGiornata)")
    List<Auto> findByExample(@Param("targa") String targa,
                             @Param("tipologiaAuto") TipologiaAuto tipologiaAuto,
                             @Param("marca") String marca,
                             @Param("modello") String modello,
                             @Param("cilindrata") Integer cilindrata,
                             @Param("tipologiaMotore") TipologiaMotore tipologiaMotore,
                             @Param("numeroPosti") Integer numeroPosti,
                             @Param("cambioAutomatico") Boolean cambioAutomatico,
                             @Param("dataImmatricolazione") LocalDate dataImmatricolazione,
                             @Param("descrizione") String descrizione,
                             @Param("prezzoPerGiornata") Float prezzoPerGiornata);
}