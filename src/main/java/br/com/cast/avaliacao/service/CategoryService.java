package br.com.cast.avaliacao.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cast.avaliacao.model.Category;
import br.com.cast.avaliacao.repository.Categories;

@Service
public class CategoryService {
	@Autowired
	private Categories categories;

	public Optional<Category> findById(Long id) {
		return this.categories.findById(id);
	}

	public List<Category> findAll() {
		return this.categories.findAll();
	}
	
	
}
