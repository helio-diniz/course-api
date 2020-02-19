package br.com.cast.avaliacao.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import br.com.cast.avaliacao.model.AppUser;

public class ApplicationUser extends User {

	private static final long serialVersionUID = 1L;
	private AppUser appUser;

	public ApplicationUser(AppUser user, Collection<? extends GrantedAuthority> authorities) {
		super(user.getEmail(), user.getPassword(), authorities);
		this.appUser = user;
	}

	public AppUser getAppUser() {
		return appUser;
	}

}
