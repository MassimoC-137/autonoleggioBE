package it.prova.autonoleggio.security;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.autonoleggio.model.Utente;
import it.prova.autonoleggio.repository.UtenteRepository;
	//Classe di servizio personalizzata per la gestione dei dettagli dell'utente per l'autenticazione
@Service
public class CustomUserDetailsService implements UserDetailsService {
    // Iniettato automaticamente da Spring per interagire con il repository Utente
	@Autowired
	private UtenteRepository utenteRepository;
    // Metodo per caricare i dettagli dell'utente dato il suo username. Se l'utente non viene trovato, lancia UsernameNotFoundException
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Utente> optionalUser = utenteRepository.findByUsername(username);
		Utente user = optionalUser
				.orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found"));
	// Crea e restituisce un oggetto User di Spring Security con i dettagli dell'utente
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				user.getAttivo(), true, true, !user.getAttivo(), getAuthorities(user));
	}
    // Metodo per ottenere le autorità (ruoli) dell'utente. Converte i ruoli dell'utente in una lista di stringhe
	private static Collection<? extends GrantedAuthority> getAuthorities(Utente user) {
		String[] userRoles = user.getRuoli().stream().map((role) -> role.getCodice()).toArray(String[]::new);
	// Crea una lista di GrantedAuthority da queste stringhe
		Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
		return authorities;
	}

}
