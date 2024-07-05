package it.prova.autonoleggio.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
// Configurazione di sicurezza per l'applicazione
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // Filtro JWT per autenticare le richieste HTTP
    @Autowired
    private JWTFilter jwtFilter;
    // Servizio personalizzato per caricare i dettagli dell'utente
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    // Gestore per gli errori di autenticazione JWT
    @Autowired
    private JWTAuthEntryPoint unauthorizedHandler;
    // Configura e restituisce un AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
    }
    // Configura e restituisce un PasswordEncoder per criptare le password
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    // Configura e restituisce un filtro CORS per gestire le richieste cross-origin
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:4200");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
    // Configura la catena dei filtri di sicurezza
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Disabilita CSRF e abilita CORS
        http.csrf(csrf -> csrf.disable()).cors(withDefaults())
                // Configura le autorizzazioni per le richieste HTTP
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/auth/**").permitAll() // Permetti l'accesso senza autenticazione a /api/auth/**
                        .requestMatchers("/api/utente/userInfo").authenticated() // Richiedi autenticazione per /api/utente/userInfo
                        .requestMatchers("/api/utente/**", "/api/prenotazione/listAll").hasAnyAuthority("ROLE_ADMIN") // Richiedi il ruolo ADMIN per alcune rotte
                        .requestMatchers("/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_CLASSIC_USER") // Richiedi i ruoli ADMIN o CLASSIC_USER per tutte le altre rotte
                        .anyRequest().authenticated() // Richiedi autenticazione per tutte le altre richieste
                )
                // Configura l'autenticazione HTTP di base
                .httpBasic(withDefaults())
                // Configura la gestione delle sessioni
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        // Aggiungi il filtro JWT prima del filtro UsernamePasswordAuthentication
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}