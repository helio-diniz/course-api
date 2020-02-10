package br.com.cast.avaliacao;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.MultiValueMap;

import br.com.cast.avaliacao.security.AppUserDetailsService;

@WebAppConfiguration
@SpringBootTest
@AutoConfigureMockMvc
@ComponentScan(basePackages = {" br.com.cast.avaliacao"})
public class OAuthSecurityTest {
	@Autowired
	private MockMvc mockMvc;	
	@MockBean
	private AppUserDetailsService users;
	private OAuthTokenGenerator tokenGenerator;
	private MultiValueMap<String, String> params;
	private User user;
	private String token;
	@Autowired
	private MessageSource messageSource;
	
	@BeforeEach
	public void initTest() {

	}
	
	@Test
	public void getAccessToken() throws Exception {
		this.tokenGenerator = new OAuthTokenGenerator("password", "course-ui", "c0urs3_u1", "admin@challenge.com", "123456", 
				new String[] {"ROLE_REGISTER_CATEGORY", "ROLE_SEARCH_CATEGORY", "ROLE_REGISTER_COURSE", 
						"ROLE_DELETE_COURSE", "ROLE_SEARCH_COURSE"}, 
				this.users, this.mockMvc);
		this.params = this.tokenGenerator.getParms();
		this.user = this.tokenGenerator.getUser();	
		
		when(this.users.loadUserByUsername(any(String.class))).thenReturn(user);
		ResultActions result = this.mockMvc.perform(post("/oauth/token")
					.params(params)
					.with(httpBasic(this.tokenGenerator.getClient(), this.tokenGenerator.getClientPassword()))
					.accept("application/json;charset=UTF-8"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"));
		String resultString = result.andReturn().getResponse().getContentAsString();
		assertNotNull(resultString);
		JacksonJsonParser jsonParser = new JacksonJsonParser();
		this.token = jsonParser.parseMap(resultString).get("access_token").toString();
		assertNotNull(this.token);
	}
	
	@Test
	public void getUnauthorizedClient() throws Exception {
		this.tokenGenerator = new OAuthTokenGenerator("password", "course-ui", "c0urs3_u", "admin@challenge.com", "123456", 
				new String[] {"ROLE_REGISTER_CATEGORY", "ROLE_SEARCH_CATEGORY", "ROLE_REGISTER_COURSE", 
						"ROLE_DELETE_COURSE", "ROLE_SEARCH_COURSE"}, 
				this.users, this.mockMvc);
		this.params = this.tokenGenerator.getParms();
		this.user = this.tokenGenerator.getUser();	
		
		when(this.users.loadUserByUsername(any(String.class))).thenReturn(user);
		this.mockMvc.perform(post("/oauth/token")
					.params(params)
					.with(httpBasic(this.tokenGenerator.getClient(), this.tokenGenerator.getClientPassword()))
					.accept("application/json;charset=UTF-8"))
				.andExpect(status().isUnauthorized());
	}
	
	@Test
	public void getBadRequestForInvalidUser() throws Exception {
		this.tokenGenerator = new OAuthTokenGenerator("password", "course-ui", "c0urs3_u1", "admin@challenge.com", "12345", 
				new String[] {"ROLE_REGISTER_CATEGORY", "ROLE_SEARCH_CATEGORY", "ROLE_REGISTER_COURSE", 
						"ROLE_DELETE_COURSE", "ROLE_SEARCH_COURSE"}, 
				this.users, this.mockMvc);
		this.params = this.tokenGenerator.getParms();
		this.user = this.tokenGenerator.getUser();	
		
		when(this.users.loadUserByUsername(any(String.class)))
			.thenThrow(new UsernameNotFoundException(messageSource.getMessage("message.user-invalid-email"
					, null, LocaleContextHolder.getLocale())));
		this.mockMvc.perform(post("/oauth/token")
					.params(params)
					.with(httpBasic(this.tokenGenerator.getClient(), this.tokenGenerator.getClientPassword()))
					.accept("application/json;charset=UTF-8"))
				.andExpect(status().isBadRequest());
	}

}
