package com.luan.enviodeEmail;

import java.util.Properties;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EnvioDeEmailApplicationTests {

	@Test
	void testeEmail() {
		/*envio de email com gmail, para outros verificar configurações SMTP do email*/

		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");//autoriação
		properties.put("mail.smtp.starttls", "true");//tipo de autentificação tls
		properties.put("mial.smtp.host", "stmp.gmai.com");//servidor gmail
		properties.put("mail.smpt.port", "465");//porta do servidor
		properties.put("mail.smtp.socketFactory.port", "465");//expecifica a porta a ser conectada pelo socket
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");//Classe socket de conexão ao smtp
		
	}

}
