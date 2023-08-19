package br.com.luan.lojavirtual.security.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.luan.lojavirtual.config.ApplicationContextLoad;
import br.com.luan.lojavirtual.model.Usuario;
import br.com.luan.lojavirtual.repository.UsuarioRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
//@Component
public class JWTTokenAutentificacaoService {
	
	private static final long EXPIRATION_TIME = 86400000;
	
	private static final String SECRET = "admin";
	
	private static final String TOKEN_PREFIX = "Bearer";
	
	private static final String HEADER_STRING = "Authorization";
	
	public void addAuthentification(HttpServletResponse response, String username) throws Exception {
		String JWT = Jwts.builder()
					.setSubject(username)//add user
					.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))//tempo exp;
					.signWith(SignatureAlgorithm.HS512, SECRET).compact();
		
		String token = TOKEN_PREFIX + " " + JWT;
		
		response.addHeader(HEADER_STRING, token);
		
		liberacaoCors(response);
		
		response.getWriter().write("{\"Authorization\": \"" + token + "\"}");
	}
	
	/*Retorna  o usuario valido com token, caso contrario retorna null*/
	public Authentication getAuthentification(HttpServletRequest request, HttpServletResponse response) {
		String  token = request.getHeader(HEADER_STRING);
		
		if (token != null) {
			String tokenLimpo = token.replace(TOKEN_PREFIX, "").trim();
			
			/*Fz liberação do token do  usuário na requisição e obtem o user*/
			String user = Jwts.parser()
					.setSigningKey(SECRET)
					.parseClaimsJwt(tokenLimpo)
					.getBody()
					.getSubject();
			
			if (user != null) {
				Usuario usuario = ApplicationContextLoad
						.getApplicationContext()
						.getBean(UsuarioRepository.class).findUserByLogin(user);
				
				if (usuario != null) {
					return new UsernamePasswordAuthenticationToken(
							usuario.getLogin(),
							usuario.getSenha(),
							usuario.getAuthorities());
				}
			}
		}
		
		liberacaoCors(response);
		return null;
	}
	
	/*Fazendo validação contra erro de CORS no navegador*/
	private void liberacaoCors(HttpServletResponse response) {
		if (response.getHeader("Access_Control-Allow-origin") == null) {
			response.addHeader("Access_Control-Allow-origin", "*");
		}
		
		if (response.getHeader("Access_Control-Allow-Headers") == null) {
			response.addHeader("Access_Control-Allow-Headers", "*");
		}
		
		if (response.getHeader("Access_Control-Request-Headers") == null) {
			response.addHeader("Access_Control-Request-Headers", "*");
		}
		
		if (response.getHeader("Access_Control-Allow-Methods") == null) {
			response.addHeader("Access_Control-Allow-Methods", "*");
		}
	}

}
