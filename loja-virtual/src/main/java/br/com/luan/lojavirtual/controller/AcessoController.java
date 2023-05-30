package br.com.luan.lojavirtual.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.luan.lojavirtual.model.Acesso;
import br.com.luan.lojavirtual.service.AcessoService;

@RestController
public class AcessoController {

	@Autowired
	private AcessoService acessoService;
	
	@PostMapping
	public Acesso salvarAcesso(Acesso acesso) {
		return (acessoService.salvarAcesso(acesso));
	}
	
	
}
