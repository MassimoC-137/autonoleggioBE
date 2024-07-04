package it.prova.autonoleggio.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
//Filtro JWT per l'autenticazione delle richieste HTTP
@Component
public class JWTFilter extends OncePerRequestFilter {
    // Logger per registrare i messaggi di errore
    private static final Logger LOGGER = LoggerFactory.getLogger(JWTFilter.class);
    // Iniettato automaticamente da Spring per caricare i dettagli dell'utente
    @Autowired
    private CustomUserDetailsService userDetailsService;
    // Iniettato automaticamente da Spring per gestire le operazioni JWT
    @Autowired
    private JWTUtil jwtUtil;
    // Metodo principale che gestisce l'autenticazione della richiesta HTTP
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Estrae l'intestazione "Authorization"
        String authHeader = request.getHeader("Authorization");
        // Controlla se l'intestazione contiene un token Bearer
        if (StringUtils.isNotBlank(authHeader) && authHeader.startsWith("Bearer ")) {
            // Estrae il token JWT
            String jwt = authHeader.substring(7);
            if (StringUtils.isBlank(jwt)) {
                // JWT non valido
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token in Bearer Header");
            } else {
                try {
                    // Verifica il token e estrae il nome utente
                    String username = jwtUtil.validateTokenAndRetrieveSubject(jwt);
                    // Carica i dettagli dell'utente
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    // Crea un oggetto UsernamePasswordAuthenticationToken con i dettagli dell'utente
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // Imposta l'autenticazione nel SecurityContext
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } catch (TokenExpiredException exc) {
                    // JWT scaduto
                    LOGGER.error("JWT token is expired: {}", exc.getMessage());
                    request.setAttribute("expired", exc.getMessage());
                } catch (JWTVerificationException exc) {
                    // Verifica JWT fallita
                    LOGGER.error("Cannot set user authentication: {}", exc);
                }
            }
        }
        // Continua l'esecuzione della catena di filtri
        filterChain.doFilter(request, response);
    }
}