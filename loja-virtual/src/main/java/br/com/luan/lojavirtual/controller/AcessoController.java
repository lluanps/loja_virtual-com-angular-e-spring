package br.com.luan.lojavirtual.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.luan.lojavirtual.model.Acesso;
import br.com.luan.lojavirtual.repository.AcessoRepository;
import br.com.luan.lojavirtual.service.AcessoService;

@RestController
public class AcessoController {

	@Autowired
	private AcessoService acessoService;
	
	@Autowired
	private AcessoRepository acessoRepository;
	
	@ResponseBody
	@PostMapping(value = "/salvar")
	public ResponseEntity<Acesso> salvaAcesso(@RequestBody Acesso acesso) {
		Acesso acessoSalvo = acessoService.save(acesso);
		return new ResponseEntity<Acesso>(acessoSalvo, HttpStatus.OK);
	}
	
	@ResponseBody
	@DeleteMapping(value = "/delete")
	public ResponseEntity<?> delete(@RequestBody Acesso acesso) {
		acessoRepository.deleteById(acesso.getId());
		return new ResponseEntity("Acesso Deletado", HttpStatus.ACCEPTED);
	}
	
}
