package br.com.cast.avaliacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cast.avaliacao.model.Category;

public interface Categories extends JpaRepository<Category, Long> {

}
