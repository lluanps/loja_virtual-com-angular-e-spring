package br.com.luan.lojavirtual.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

public class JWTApiAutentificacaoFilter extends GenericFilterBean{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		/*estabelece a autentificação do usuario*/
		Authentication authentication = new JWTTokenAutentificacaoService()
				.getAuthentification((HttpServletRequest)request, (HttpServletResponse)response);
		
		/*add processo de autentificação do Spring security*/
		SecurityContextHolder.getContext().setAuthentication(authentication);
		/*continua com a requisicao, ou chama a api ou bloqueia*/
		chain.doFilter(request, response);
	}

}
