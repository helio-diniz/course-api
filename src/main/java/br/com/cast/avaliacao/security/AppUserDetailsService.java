package br.com.cast.avaliacao.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.cast.avaliacao.model.AppUser;
import br.com.cast.avaliacao.repository.AppUserRepository;

@Service
public class AppUserDetailsService implements UserDetailsService {

	@Autowired
	private AppUserRepository users;

	@Autowired
	private MessageSource messageSource;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<AppUser> optionalUser = this.users.findByEmail(email);
		AppUser user = optionalUser.orElseThrow(() -> new UsernameNotFoundException(
				messageSource.getMessage("message.user-invalid-email", null, LocaleContextHolder.getLocale())));
		return new ApplicationUser(user, this.getPermissions(user));
	}

	private Collection<? extends GrantedAuthority> getPermissions(AppUser user) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		user.getPermissions()
				.forEach(p -> authorities.add(new SimpleGrantedAuthority(p.getDescription().toUpperCase())));
		return authorities;
	}

}
