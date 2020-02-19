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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import br.com.cast.avaliacao.model.Category;
import br.com.cast.avaliacao.security.AppUserDetailsService;
import br.com.cast.avaliacao.service.CategoryService;

@WebAppConfiguration
@SpringBootTest
@AutoConfigureMockMvc
@ComponentScan(basePackages = {" br.com.cast.avaliacao"}) 
public class CategoryControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private CategoryService categoryService;
	@MockBean
	private AppUserDetailsService users;
	private Map<Long, Category> categoryMap = new HashMap<>();
	private String categoryURL = "/categorias";
	private String token;

	@BeforeEach
	public void initTest() throws Exception {
		this.categoryMap.clear();
		this.categoryMap.put(1L, new Category(1L, "Comportamental"));
		this.categoryMap.put(2L, new Category(2L, "Programação"));
		this.categoryMap.put(3L, new Category(3L, "Qualidade"));
		this.categoryMap.put(4L, new Category(4L, "Processos"));

		MockitoAnnotations.initMocks(CategoryControllerTest.class);		
		OAuthTokenGenerator tokenGenerator = new OAuthTokenGenerator("password", "course-ui", "c0urs3_u1", "admin@challenge.com", "123456", 
				new String[] {"ROLE_REGISTER_CATEGORY", "ROLE_SEARCH_CATEGORY", "ROLE_REGISTER_COURSE", 
					"ROLE_DELETE_COURSE", "ROLE_SEARCH_COURSE"}, 
				this.users, this.mockMvc);
		this.token = tokenGenerator.obtainAccessToken();
	}
	
	@Test
	public void findAllCategories() throws Exception {
		when(this.categoryService.findAll()).thenReturn(getAllCategories());
		String jsonResponse = "[" + "{\"id\":1,\"description\":\"Comportamental\"},"
				+ "{\"id\":2,\"description\":\"Programação\"}," + "{\"id\":3,\"description\":\"Qualidade\"},"
				+ "{\"id\":4,\"description\":\"Processos\"}" + "]";
		
		this.mockMvc.perform(get(this.categoryURL + "/todas")
				.header("Authorization", "Bearer " + this.token)
				.accept("application/json;charset=UTF-8"))
			.andDo(print()).andExpect(status().isOk()).andExpect(content().json(jsonResponse));
	}
	
	private List<Category> getAllCategories() {
		List<Category> categoryList = new ArrayList<>();
		categoryList.addAll(this.categoryMap.values());
		return categoryList;
	}
}
