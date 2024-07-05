package it.prova.autonoleggio.security.auth;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.prova.autonoleggio.dto.UtenteDTO;
import it.prova.autonoleggio.security.JWTUtil;
import it.prova.autonoleggio.security.dto.UtenteAuthDTO;
import it.prova.autonoleggio.service.UtenteService;

// Controller per gestire le operazioni di autenticazione
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    // Utilizzato per generare e verificare i token JWT
    @Autowired
    private JWTUtil jwtUtil;
    // Servizio per gestire le operazioni sugli utenti
    @Autowired
    private UtenteService utenteService;
    // Endpoint per gestire il login
    @PostMapping("/login")
    public Map<String, Object> loginHandler(@RequestBody UtenteAuthDTO body) {
        try {
            // Creazione del token di autenticazione contenente le credenziali per l'autenticazione
            String token = "";
            // Se l'utente esiste, generare il token JWT
            if (utenteService.eseguiAccesso(body.getUsername(), body.getPassword()) != null) {
                token = jwtUtil.generateToken(body.getUsername());
            } else {
                // Autenticazione fallita
                throw new RuntimeException("Auth failed for Username: " + body.getUsername());
            }
            // Risposta con il token JWT
            return Collections.singletonMap("jwt", token);
        } catch (AuthenticationException authExc) {
            // Autenticazione fallita
            throw new RuntimeException("Invalid Login Credentials. " + authExc.getMessage());
        }
    }
    // Endpoint per gestire la registrazione di un nuovo utente
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody UtenteDTO nuovoUtente) {
        // Inserisce un nuovo utente utilizzando il servizio
    	utenteService.inserisciNuovo(nuovoUtente);
    }
}