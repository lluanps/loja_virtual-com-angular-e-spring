package br.com.luan.lojavirtual.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.luan.lojavirtual.exception.BusinessException;
import br.com.luan.lojavirtual.model.Acesso;
import br.com.luan.lojavirtual.repository.AcessoRepository;
import br.com.luan.lojavirtual.service.AcessoService;

@Controller
@RestController
public class AcessoController {

	@Autowired
	private AcessoService acessoService;
	
	@Autowired
	private AcessoRepository acessoRepository;
	
	@ResponseBody /*Poder dar um retorno da API*/
	@PostMapping(value = "/salvarAcesso") /*Mapeando a url para receber JSON*/
	public ResponseEntity<Acesso> salvarAcesso(@RequestBody Acesso acesso) throws BusinessException { /*Recebe o JSON e converte pra Objeto*/
		
		if (acesso.getId() == null) {
		  List<Acesso> acessos = acessoRepository.buscaAcessoDescricao(acesso.getDescricao().toUpperCase());
		  
		  if (!acessos.isEmpty()) {
			  throw new BusinessException("Já existe Acesso com a descrição: " + acesso.getDescricao());
		  }
		}
		
		Acesso acessoSalvo = acessoService.save(acesso);
		return new ResponseEntity<Acesso>(acessoSalvo, HttpStatus.OK);
	}
	
	@ResponseBody /*Poder dar um retorno da API*/
	@PostMapping(value = "**/deleteAcesso") /*Mapeando a url para receber JSON*/
	public ResponseEntity<?> deleteAcesso(@RequestBody Acesso acesso) { /*Recebe o JSON e converte pra Objeto*/
		acessoRepository.deleteById(acesso.getId());
		return new ResponseEntity("Acesso Removido",HttpStatus.OK);
	}

	//@Secured({ "ROLE_GERENTE", "ROLE_ADMIN" })
	@ResponseBody
	@DeleteMapping(value = "**/deleteAcessoPorId/{id}")
	public ResponseEntity<?> deleteAcessoPorId(@PathVariable("id") Long id) { 
		acessoRepository.deleteById(id);
		return new ResponseEntity("Acesso Removido",HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/obterAcesso/{id}")
	public ResponseEntity<Acesso> obterAcesso(@PathVariable("id") Long id) throws BusinessException { 
		Acesso acesso = acessoRepository.findById(id).orElse(null);
		if (acesso == null) {
			throw new BusinessException("Não encontrou Acesso com código: " + id);
		}
		return new ResponseEntity<Acesso>(acesso,HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/buscarPorDesc/{desc}")
	public ResponseEntity<List<Acesso>> buscarPorDesc(@PathVariable("desc") String desc) { 
		List<Acesso> acesso = acessoRepository.buscaAcessoDescricao(desc.toUpperCase());
		return new ResponseEntity<List<Acesso>>(acesso,HttpStatus.OK);
	}
	
}
