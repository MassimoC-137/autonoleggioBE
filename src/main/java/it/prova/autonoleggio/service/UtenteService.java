package it.prova.autonoleggio.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import it.prova.autonoleggio.dto.UtenteDTO;
import it.prova.autonoleggio.exception.CreditMustBeZeroException;
import it.prova.autonoleggio.exception.IdNotNullForInsertException;
import it.prova.autonoleggio.exception.PasswordMismatchException;
import it.prova.autonoleggio.exception.UserNotFoundException;
import it.prova.autonoleggio.model.Ruolo;
import it.prova.autonoleggio.model.Utente;
import it.prova.autonoleggio.repository.RuoloRepository;
import it.prova.autonoleggio.repository.UtenteRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UtenteService {
    // Iniettato automaticamente da Spring per interagire con il repository Utente
    @Autowired
    private UtenteRepository utenteRepository;
    // Iniettato automaticamente da Spring per codificare le password
    @Autowired
    private PasswordEncoder passwordEncoder;
    // Iniettato automaticamente da Spring per interagire con il repository Ruolo
    @Autowired
    private RuoloRepository ruoloRepository;
    // Restituisce una lista di tutti gli utenti mappati a DTO
    public List<UtenteDTO> listAllUtenti() {
        return utenteRepository.findAll().stream()
                .map(UtenteDTO::buildUtenteDTOFromModel)
                .collect(Collectors.toList());
    }
    // Restituisce un singolo utente mappato a DTO dato il suo ID, oppure null se non trovato
    public UtenteDTO caricaSingoloUtente(Long id) {
        return utenteRepository.findById(id)
                .map(UtenteDTO::buildUtenteDTOFromModel)
                .orElse(null);
    }
    // Restituisce un singolo utente con i suoi ruoli mappati a DTO dato il suo ID, oppure null se non trovato
    public UtenteDTO caricaSingoloUtenteConRuoli(Long id) {
        return utenteRepository.findByIdConRuoli(id)
                .map(UtenteDTO::buildUtenteDTOFromModel)
                .orElse(null);
    }
    // Aggiorna le informazioni di un utente esistente e restituisce il DTO dell'utente aggiornato
    // Lancia UserNotFoundException se l'utente non esiste
    public UtenteDTO aggiorna(UtenteDTO utenteDTO) {
        Utente utenteInstance = utenteDTO.buildUtenteModel(true);
        Utente utenteReloaded = utenteRepository.findById(utenteInstance.getId()).orElse(null);
        if (utenteReloaded == null)
            throw new UserNotFoundException();
        utenteReloaded.setNome(utenteInstance.getNome());
        utenteReloaded.setCognome(utenteInstance.getCognome());
        utenteReloaded.setDataConseguimentoPatente(utenteInstance.getDataConseguimentoPatente());
        utenteReloaded.setEmail(utenteInstance.getEmail());
        utenteReloaded.setRuoli(utenteInstance.getRuoli());
        return UtenteDTO.buildUtenteDTOFromModel(utenteRepository.save(utenteReloaded));
    }
    // Inserisce un nuovo utente nel database con i ruoli e il credito predefiniti
    // Verifica che l'ID sia null e che il credito disponibile sia zero, altrimenti lancia eccezioni
    // Codifica la password e conferma la password prima di salvare l'utente
    // Lancia IdNotNullForInsertException, CreditMustBeZeroException e PasswordMismatchException per controlli di validazione
    public void inserisciNuovo(UtenteDTO utenteDTO) {
        Utente utenteInstance = utenteDTO.buildUtenteModel(true);
        if (utenteInstance.getId() != null) {
            throw new IdNotNullForInsertException();
        }
        if (utenteInstance.getRuoli() == null) {
            Set<Ruolo> ruoli = new HashSet<>();
            ruoli.add(ruoloRepository.findByCodice(Ruolo.ROLE_CLASSIC_USER));
            utenteInstance.setRuoli(ruoli);
        }
        if (utenteInstance.getCreditoDisponibile() != 0) {
            throw new CreditMustBeZeroException();
        } else {
            utenteInstance.setCreditoDisponibile(0.0f);
        }

        if (!(utenteDTO.getPassword().equals(utenteDTO.getConfermaPassword()))) {
            throw new PasswordMismatchException();
        }
        utenteInstance.setConfermaPassword(passwordEncoder.encode(utenteDTO.getConfermaPassword()));
        utenteInstance.setPassword(passwordEncoder.encode(utenteDTO.getPassword()));
        utenteRepository.save(utenteInstance);
    }
    // Rimuove un utente dal database dato il suo ID
    public void rimuovi(Long idToRemove) {
        utenteRepository.deleteById(idToRemove);
    }
    // Trova un utente attivo dato il suo username e password, restituisce il DTO dell'utente trovato oppure null
    public UtenteDTO findByUsernameAndPassword(String username, String password) {
        return utenteRepository.findByUsernameAndPassword(username, password);
    }
    // Esegue l'accesso dell'utente verificando username e password
    // Restituisce il DTO dell'utente se le credenziali sono corrette, altrimenti null
    public UtenteDTO eseguiAccesso(String username, String password) {
        Optional<Utente> optionalUser = utenteRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            Utente user = optionalUser.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return UtenteDTO.buildUtenteDTOFromModel(user);
            }
        }
        return null;
    }
    // Cambia l'abilitazione (attivo/inattivo) di un utente dato il suo ID
    // Lancia UserNotFoundException se l'utente non esiste
    public void changeUserAbilitation(Long utenteInstanceId) {
        Utente utenteInstance = utenteRepository.findById(utenteInstanceId).orElse(null);
        if (utenteInstance == null)
            throw new UserNotFoundException();
        utenteRepository.save(utenteInstance);
    }
    // Trova un utente dato il suo username e restituisce il DTO dell'utente trovato oppure null
    public UtenteDTO findByUsername(String username) {
        return utenteRepository.findByUsername(username)
                .map(UtenteDTO::buildUtenteDTOFromModel)
                .orElse(null);
    }
    // Aggiorna il credito disponibile di un utente e restituisce il DTO dell'utente aggiornato
    // Lancia UserNotFoundException se l'utente non esiste
    public UtenteDTO aggiornaCredito(UtenteDTO utenteDTO) {
        Utente utenteInstance = utenteDTO.buildUtenteModel(false);
        Utente utenteReloaded = utenteRepository.findById(utenteInstance.getId()).orElse(null);
        if (utenteReloaded == null)
            throw new UserNotFoundException();
        utenteReloaded.setCreditoDisponibile(utenteInstance.getCreditoDisponibile());
        return UtenteDTO.buildUtenteDTOFromModel(utenteRepository.save(utenteReloaded));
    }
}