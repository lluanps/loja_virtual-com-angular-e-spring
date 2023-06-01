package br.com.luan.lojavirtual.security;

import javax.servlet.http.HttpSessionListener;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebConfigSecurity extends WebSecurityConfigurerAdapter implements HttpSessionListener {

	@Override
	public void configure(WebSecurity webSecurity) {
		/*Ignorando urls temporariamente*/
		webSecurity.ignoring()
		.antMatchers(HttpMethod.POST, "/salvar")
		.antMatchers(HttpMethod.GET, "/buscar")
		.antMatchers(HttpMethod.DELETE, "/delete");
	}
	
}
