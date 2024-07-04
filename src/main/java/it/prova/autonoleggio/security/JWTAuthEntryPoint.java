package it.prova.autonoleggio.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
	//Classe che gestisce gli errori di autenticazione JWT
@Component
public class JWTAuthEntryPoint implements AuthenticationEntryPoint {
    // Logger per registrare messaggi di errore
	private static final Logger logger = LoggerFactory.getLogger(JWTAuthEntryPoint.class);
    // Metodo chiamato quando un'eccezione di autenticazione viene lanciata
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
    // Registra l'errore di autenticazione
		logger.error("Unauthorized error: {}", authException.getMessage());

    // Controlla se l'attributo "expired" è presente nella richiesta
		final String expired = (String) request.getAttribute("expired");
		if (expired != null) {
    // Se presente, invia un errore di risposta 401 con il messaggio di scadenza
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, expired);
		}
	// Imposta il tipo di contenuto della risposta come JSON
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
	// Imposta lo stato della risposta come 401 (Non autorizzato)
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	// Crea un corpo di risposta JSON con i dettagli dell'errore
		final Map<String, Object> body = new HashMap<>();
		body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
		body.put("error", "Unauthorized");
		body.put("message", authException.getMessage());
		body.put("path", request.getServletPath());
    // Scrive il corpo della risposta come JSON
		final ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(response.getOutputStream(), body);

	}

}