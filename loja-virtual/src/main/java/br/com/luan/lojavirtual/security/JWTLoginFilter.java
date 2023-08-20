package br.com.luan.lojavirtual.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.luan.lojavirtual.model.Usuario;
import br.com.luan.lojavirtual.security.service.JWTTokenAutentificacaoService;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

	/*Config gerenciador de autenticação*/
	public JWTLoginFilter(String url, AuthenticationManager authenticationManager) {
		/*torna obrigatorio autehtificar a url*/
		super(new AntPathRequestMatcher(url));
		/*Gerenciador  de autentificacao*/
		setAuthenticationManager(authenticationManager);
	}
	
	/*Retorna o usuario ao processar autentificacao*/
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		/*Obtem usuario*/
		Usuario user = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
		/*retorna user com login e senha*/
		return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(user.getLogin(), user.getSenha()));
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		try {
			new JWTTokenAutentificacaoService().addAuthentification(response, authResult.getName());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
