
package com.App.LojaVirtual.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.App.LojaVirtual.ExceptionLojaVirtual;
import com.App.LojaVirtual.model.Acesso;
import com.App.LojaVirtual.repository.AcessoRepository;
import com.App.LojaVirtual.service.AcessoService;
import com.fasterxml.jackson.databind.introspect.TypeResolutionContext.Empty;

@Controller
@RestController
public class acessoController {
	
	@Autowired
	private AcessoRepository acessoRepository;
	
	@Autowired
	private AcessoService acessoService;
	
	@ResponseBody /* Poder dar um retorno da API */
	@PostMapping("/salvaAcesso") /* Mapeiando a url para receber o JSON*/
	public ResponseEntity<Acesso> salvarAcesso(@RequestBody Acesso acesso) throws ExceptionLojaVirtual { /* Recebe o JSON e transforma em objeto */
		
		if (acesso.getId() == null) {
			
			List<Acesso> acessos = acessoRepository.buscaAcessoDesc(acesso.getDescricao().toUpperCase());
			
			 if(!acessos.isEmpty()) { 
				 throw new ExceptionLojaVirtual("Já existe acesso com essa descrição " + acesso.getDescricao());
			}
			
		}
		
		Acesso response = acessoService.save(acesso);
		
		return new ResponseEntity<Acesso>(response, HttpStatus.OK);
		
	}
	
	@ResponseBody /* Poder dar um retorno da API */
	@PostMapping("/deletaAcesso") /* Mapeiando a url para receber o JSON*/
	public ResponseEntity<?> deletarAcesso(@RequestBody Acesso acesso) { /* Recebe o JSON e transforma em objeto */
		
		acessoRepository.deleteById(acesso.getId());
		
		return new ResponseEntity("Acesso removido: ", HttpStatus.OK);
		
	}
	
	@ResponseBody 
	@GetMapping("/obterAcesso/{id}")
	public ResponseEntity<Acesso> obterAcesso(@PathVariable("id") Long id) throws ExceptionLojaVirtual {
		
		Acesso acesso = acessoRepository.findById(id).orElse(null);
		
		if(acesso == null) {
			throw new ExceptionLojaVirtual("Não encontrou acesso com id: " + id);
		}
		
		return new ResponseEntity<Acesso>( acesso ,HttpStatus.OK);
		
	}
	
	@ResponseBody 
	@GetMapping("/buscarAcessoPorDesc/{desc}")
	public ResponseEntity<List<Acesso>> buscarAcessoPorDesc(@PathVariable("desc") String desc) throws ExceptionLojaVirtual {
		
		List<Acesso> acessos = acessoRepository.buscaAcessoDesc(desc);
		
		if(acessos == null) {
			throw new ExceptionLojaVirtual("Não encontrou acesso com descrição : " + desc);
		}
		
		return new ResponseEntity <List<Acesso>>(acessos, HttpStatus.OK);
		
	}
	
}
