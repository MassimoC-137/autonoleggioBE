package it.prova.autonoleggio;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.prova.autonoleggio.dto.RuoloDTO;
import it.prova.autonoleggio.dto.UtenteDTO;
import it.prova.autonoleggio.model.Ruolo;
import it.prova.autonoleggio.model.Utente;
import it.prova.autonoleggio.service.RuoloService;
import it.prova.autonoleggio.service.UtenteService;

@SpringBootApplication
public class AutonoleggioApplication implements CommandLineRunner {
	// Iniezione delle dipendenze per i servizi Ruolo e Utente
	@Autowired
	private RuoloService ruoloServiceInstance;
	@Autowired
	private UtenteService utenteServiceInstance;

	// Metodo principale per avviare l'applicazione
	public static void main(String[] args) {
		SpringApplication.run(AutonoleggioApplication.class, args);
	}
	// Metodo eseguito al lancio dell'applicazione
	@Override
	public void run(String... args) throws Exception {
		// Creazione dei ruoli se non esistono già
		if (ruoloServiceInstance.cercaPerCodice(Ruolo.ROLE_ADMIN) == null) {
			ruoloServiceInstance.inserisciNuovo(new RuoloDTO(Ruolo.ROLE_ADMIN));
		}
		if (ruoloServiceInstance.cercaPerCodice(Ruolo.ROLE_CLASSIC_USER) == null) {
			ruoloServiceInstance.inserisciNuovo(new RuoloDTO(Ruolo.ROLE_CLASSIC_USER));
		}
		// Creazione dell'utente amministratore se non esiste già
		if (utenteServiceInstance.findByUsername("admin") == null) {
			Utente admin = new Utente(); 
					admin.setUsername("admin");
					admin.setPassword("admin"); // La password dovrebbe essere criptata
					admin.setConfermaPassword("admin"); 
					admin.setEmail("admin@admin.com"); 
					admin.setNome("Admin"); 
					admin.setCognome("Admin"); 
					admin.setDataConseguimentoPatente(LocalDate.of(1994, 9, 6)); 
					admin.setCreditoDisponibile(0.0f);

			Set<Ruolo> ruoli = new HashSet<>();
            // Aggiunge il ruolo di amministratore all'utente
			ruoli.add(ruoloServiceInstance.cercaPerCodice(Ruolo.ROLE_ADMIN));
			admin.setRuoli(ruoli);
			// Conversione di Utente in UtenteDTO per l'inserimento
			utenteServiceInstance.inserisciNuovo(UtenteDTO.buildUtenteDTOFromModel(admin));
		}
	}
}
