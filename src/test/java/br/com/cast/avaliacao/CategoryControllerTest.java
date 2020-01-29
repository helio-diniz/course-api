package br.com.cast.avaliacao;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import br.com.cast.avaliacao.controller.CategoryController;
import br.com.cast.avaliacao.model.Category;
import br.com.cast.avaliacao.service.CategoryService;

@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private CategoryService categoryService;
	private Map<Long, Category> categoryMap = new HashMap<>();
	private String categoryURL = "/categorias";
	
	@BeforeEach
	public void initTest() {
		this.categoryMap.clear();
		this.categoryMap.put(1L, new Category(1L, "Comportamental"));
		this.categoryMap.put(2L, new Category(2L, "Programação"));
		this.categoryMap.put(3L, new Category(3L, "Qualidade"));
		this.categoryMap.put(4L, new Category(4L, "Processos"));

		MockitoAnnotations.initMocks(CategoryControllerTest.class);
	}
	
	@Test
	public void findAllCategories() throws Exception {
		
		when(this.categoryService.findAll()).thenReturn(getAllCategories());
		String jsonResponse = "["
				+ "{\"id\":1,\"description\":\"Comportamental\"},"
				+ "{\"id\":2,\"description\":\"Programação\"},"
				+ "{\"id\":3,\"description\":\"Qualidade\"},"
				+ "{\"id\":4,\"description\":\"Processos\"}"
				+ "]";
		this.mockMvc.perform(get(this.categoryURL + "/todas")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().json(jsonResponse));
	}
	
	private List<Category> getAllCategories(){
		List<Category> categoryList = new ArrayList<>();
		categoryList.addAll(this.categoryMap.values());
		return categoryList;
	}
}
