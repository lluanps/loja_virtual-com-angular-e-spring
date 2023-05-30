package br.com.luan.lojavirtual;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.luan.lojavirtual.model.Acesso;
import br.com.luan.lojavirtual.repository.AcessoRepository;
import br.com.luan.lojavirtual.service.AcessoService;

@SpringBootTest(classes = LojaVirtualApplication.class)
public class LojaVirtualApplicationTests {
	
	@Autowired
	private AcessoService acessoService;
	
	@Autowired
	private AcessoRepository acessoRepository;

	@Test
	public void cadastraAcesso() {
		Acesso acesso = new Acesso();
		
		acesso.setDescricao("ROLE_ADMIN");
		
		acessoService.salvarAcesso(acesso);
		
	}
	
	

}
