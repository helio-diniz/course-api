package br.com.cast.avaliacao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cast.avaliacao.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
	public Optional<AppUser> findByEmail(String email);
}
