package br.com.cast.avaliacao;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import br.com.cast.avaliacao.security.AppUserDetailsService;

public class OAuthTokenGenerator {
	private String grantType;
	private String client;
	private String clientPassword;
	private String username;
	private String password;
	private String[] authorities;
	private AppUserDetailsService users;
	private MockMvc mockMvc;

	public OAuthTokenGenerator(String grantType, String client, String clientPassword, String username, String password,
			String[] authorities, AppUserDetailsService users, MockMvc mockMvc) {
		super();
		this.grantType = grantType;
		this.client = client;
		this.clientPassword = clientPassword;
		this.username = username;
		this.password = password;
		this.authorities = authorities;
		this.users = users;
		this.mockMvc = mockMvc;
	}

	public String getGrantType() {
		return grantType;
	}

	public String getClient() {
		return client;
	}

	public String getClientPassword() {
		return clientPassword;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String[] getAuthorities() {
		return authorities;
	}

	public AppUserDetailsService getUsers() {
		return users;
	}

	public MockMvc getMockMvc() {
		return mockMvc;
	}

	public MultiValueMap<String, String> getParms() {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", this.grantType);
		params.add("client", this.client);
		params.add("username", this.username);
		params.add("password", this.password);

		return params;
	}

	public User getUser() {
		Set<SimpleGrantedAuthority> authoritiesMap = new HashSet<>();
		for (String authority : authorities) {
			authoritiesMap.add(new SimpleGrantedAuthority(authority));
		}
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode(this.password);
		return new User(this.username, encodedPassword, authoritiesMap);
	}

	public String obtainAccessToken() throws Exception {

		MultiValueMap<String, String> params = this.getParms();
		User user = this.getUser();
		when(this.users.loadUserByUsername(any(String.class))).thenReturn(user);

		ResultActions result = this.mockMvc
				.perform(post("/oauth/token").params(params).with(httpBasic(this.client, this.clientPassword))
						.accept("application/json;charset=UTF-8"))
				.andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"));

		String resultString = result.andReturn().getResponse().getContentAsString();

		JacksonJsonParser jsonParser = new JacksonJsonParser();
		return jsonParser.parseMap(resultString).get("access_token").toString();
	}
}
