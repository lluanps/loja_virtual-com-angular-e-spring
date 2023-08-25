package br.com.luan.lojavirtual.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import br.com.luan.lojavirtual.config.ApplicationContextLoad;
import br.com.luan.lojavirtual.model.Usuario;
import br.com.luan.lojavirtual.repository.UsuarioRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

/*Criar a autenticação e retonar também a autenticação JWT*/
@Service
@Component
public class JWTTokenAutentificacaoService {
	
	
	/*tempo da validade do token*/
	private static final long EXPIRATION_TIME = 8600000;
	
	/*Chave de senha para juntar com o JWT*/
	private static final String SECRET = "ss/-*-*sds565dsd-s/d-s*dsds";
	
	private static final String TOKEN_PREFIX = "Bearer";
	
	private static final String HEADER_STRING = "Authorization";
	
	/*Gera o token e da a resposta para o cliente o com JWT*/
	public void addAuthentification(HttpServletResponse response, String username) throws Exception {
		/*Montagem do Token*/
		String JWT = Jwts.builder()./*Chama o gerador de token*/
				setSubject(username) /*Adiciona o user*/
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SECRET).compact(); /*Temp de expiração*/
		
		String token = TOKEN_PREFIX + " " + JWT;
		
		/*Dá a resposta pra tela e para o cliente, outra API, navegador, aplicativo, outra chamada java*/
		response.addHeader(HEADER_STRING, token);
		
		liberacaoCors(response);
		
		response.getWriter().write("{\"Authorization\": \"" + token + "\"}");
	}
	
	/*Retorna o usuário validado com token ou caso nao seja válido retorna null*/
	public Authentication getAuthentification(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String token = request.getHeader(HEADER_STRING);
		try {
		if (token != null) {
			String tokenLimpo = token.replace(TOKEN_PREFIX, "").trim();
			/*Faz a validação do token do usuário na requisicao e obtem o USER*/
			String user = Jwts.parser().
					setSigningKey(SECRET)
					.parseClaimsJws(tokenLimpo)
					.getBody().getSubject();

			if (user != null) {
				Usuario usuario = ApplicationContextLoad.
						getApplicationContext().
						getBean(UsuarioRepository.class).findUserByLogin(user);
				if (usuario != null) {
					return new UsernamePasswordAuthenticationToken(
							usuario.getLogin(),
							usuario.getSenha(), 
							usuario.getAuthorities());
				}
			}
		}
		}catch (SignatureException e) {
			response.getWriter().write("Token está inválido.");
		}catch (ExpiredJwtException e) {
			response.getWriter().write("Token está expirado, efetue o login novamente.");
		}
		finally {
			liberacaoCors(response);
		}
		return null;
	}
	
	/*Fazendo liberação contra erro de COrs no navegador*/
	private void liberacaoCors(HttpServletResponse response) {
		if (response.getHeader("Access-Control-Allow-Origin") == null) {
			response.addHeader("Access-Control-Allow-Origin", "*");
		}
		
		if (response.getHeader("Access-Control-Allow-Headers") == null) {
			response.addHeader("Access-Control-Allow-Headers", "*");
		}
		
		if (response.getHeader("Access-Control-Request-Headers") == null) {
			response.addHeader("Access-Control-Request-Headers", "*");
		}
		
		if (response.getHeader("Access-Control-Allow-Methods") == null) {
			response.addHeader("Access-Control-Allow-Methods", "*");
		}
	}
	
}
