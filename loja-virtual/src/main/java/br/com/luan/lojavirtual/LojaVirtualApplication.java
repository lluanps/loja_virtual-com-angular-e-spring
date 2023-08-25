package br.com.luan.lojavirtual;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan(basePackages = "br.com.luan.lojavirtual.model") //mapea classes de model que gera tabelas do banco
@ComponentScan(basePackages = {"br.*"}) //procura todos os arquivos com 'br.' 
@EnableJpaRepositories(basePackages = {"br.com.luan.lojavirtual.repository"}) //usado para assegurar que o repository entre na procura
@EnableTransactionManagement //gerencia transações do banco de dados
public class LojaVirtualApplication {

	public static void main(String[] args) {
		
		System.out.println(new BCryptPasswordEncoder().encode("admin"));
		
		SpringApplication.run(LojaVirtualApplication.class, args);
	}

}
