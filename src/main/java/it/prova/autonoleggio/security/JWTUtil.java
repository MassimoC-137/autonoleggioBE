package it.prova.autonoleggio.security;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import it.prova.autonoleggio.model.Ruolo;
import it.prova.autonoleggio.model.Utente;
import it.prova.autonoleggio.repository.UtenteRepository;

	//Classe utilitaria per la generazione e verifica dei token JWT
@Component
public class JWTUtil {
    // Repository per interagire con l'entità Utente
    @Autowired
    private UtenteRepository utenteRepository;

    // Segreto per la firma dei token JWT, iniettato dal file di configurazione
    @Value("${jwt.secret}")
    private String secret;

    // Durata di validità del token JWT, iniettata dal file di configurazione
    @Value("${jwt.expiration}")
    private Long jwtExpirationMs;
 // Metodo per generare un token JWT utilizzando il segreto iniettato
    public String generateToken(String username) throws IllegalArgumentException, JWTCreationException {
        Optional<Utente> optionalUser = utenteRepository.findByUsername(username);
        Utente utente = optionalUser.get();

        // Converte i ruoli dell'utente in un array di stringhe
        String[] ruoliArray = utente.getRuoli().stream().map(Ruolo::getCodice).toArray(String[]::new);

        // Crea e restituisce il token JWT firmato
        return JWT.create()
                .withSubject("User Details")
                .withClaim("username", username)
                .withClaim("nome", utente.getNome())
                .withArrayClaim("roles", ruoliArray)
                .withIssuedAt(new Date())
                .withIssuer("AUTONOLEGGIO")
                .withExpiresAt(new Date((new Date()).getTime() + jwtExpirationMs))
                .sign(Algorithm.HMAC256(secret));
    }
    // Metodo per verificare il token JWT e estrarre il nome utente dal payload del token
    public String validateTokenAndRetrieveSubject(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User Details")
                .withIssuer("AUTONOLEGGIO")
                .build();
        // Verifica il token e decodifica il JWT
        DecodedJWT jwt = verifier.verify(token);
        // Restituisce il nome utente dal claim del token
        return jwt.getClaim("username").asString();
    }
}