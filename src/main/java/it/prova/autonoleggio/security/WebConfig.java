package it.prova.autonoleggio.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
// Configurazione CORS per l'applicazione
@Configuration
public class WebConfig implements WebMvcConfigurer {
    // Metodo per configurare le mappature CORS
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Aggiunge una mappatura CORS per tutte le rotte che iniziano con /api/
        registry.addMapping("/api/**")
                // Permette richieste dall'origine specificata (ad es. frontend su localhost:4200)
                .allowedOrigins("http://localhost:4200")
                // Permette solo i metodi HTTP specificati
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                // Permette tutti gli header
                .allowedHeaders("*")
                // Permette di includere le credenziali (cookie, header di autorizzazione, ecc.)
                .allowCredentials(true);
    }
}