package br.com.luan.lojavirtual.security;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.com.luan.lojavirtual.security.service.ImplUserDetailService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebConfigSecurity extends WebSecurityConfigurerAdapter implements HttpSessionListener {

	@Autowired
	private ImplUserDetailService implUserDetailService; 
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/*Ativa proteção do user q n ta validado por token*/
		http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
			.disable().authorizeRequests().antMatchers("/").permitAll()//usado para accessar a parte incial/principal 
			.antMatchers("/index").permitAll()
			.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()//evitar bloqueio de cors do navegador
			.anyRequest().authenticated().and().logout().logoutSuccessUrl("/index")//redireciona ou retorna o index ao deslogar
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))//mapeia o logout do sistema
			//filtra as requisições
			.and()
				.addFilterAfter(new JWTLoginFilter("/login", authenticationManager()),
					UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(new JWTApiAutentificacaoFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(implUserDetailService).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Override
	public void configure(WebSecurity webSecurity) {
		/*Ignorando urls temporariamente*/
		//webSecurity.ignoring()
		//.antMatchers(HttpMethod.POST, "/salvarAcesso")
		//.antMatchers(HttpMethod.GET, "/buscar")
		//.antMatchers(HttpMethod.DELETE, "/delete");
	}
	
}
