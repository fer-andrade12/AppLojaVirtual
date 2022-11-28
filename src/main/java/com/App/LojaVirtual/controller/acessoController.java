
package com.App.LojaVirtual.controller;

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

import com.App.LojaVirtual.model.Acesso;
import com.App.LojaVirtual.repository.AcessoRepository;
import com.App.LojaVirtual.service.AcessoService;

@Controller
@RestController
public class acessoController {
	
	@Autowired
	private AcessoRepository acessoRepository;
	
	@Autowired
	private AcessoService acessoService;
	
	@ResponseBody /* Poder dar um retorno da API */
	@PostMapping("/salvaAcesso") /* Mapeiando a url para receber o JSON*/
	public ResponseEntity<Acesso> salvarAcesso(@RequestBody Acesso acesso) { /* Recebe o JSON e transforma em objeto */
		
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
	public ResponseEntity<Acesso> obterAcesso(@PathVariable("id") Long id) {
		
		Acesso acesso = acessoRepository.findById(id).get();
		
		return new ResponseEntity<Acesso>(HttpStatus.OK);
		
	}
	
	@ResponseBody 
	@GetMapping("/buscarAcessoPorDesc/{desc}")
	public ResponseEntity<List<Acesso>> buscarAcessoPorDesc(@PathVariable("desc") String desc) {
		
		List<Acesso> acessos = acessoRepository.buscaAcessoDesc(desc);
		
		return new ResponseEntity <List<Acesso>>(HttpStatus.OK);
		
	}
	
}
