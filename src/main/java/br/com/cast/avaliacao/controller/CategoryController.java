package br.com.cast.avaliacao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.cast.avaliacao.model.Category;
import br.com.cast.avaliacao.service.CategoryService;

@RestController
@RequestMapping("/categorias")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/todas")
	@PreAuthorize("hasAuthority('ROLE_SEARCH_CATEGORY')")
	public @ResponseBody List<Category> findAllCategories() {
		return this.categoryService.findAll();
	}
	
}
