package com.App.LojaVirtual.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.App.LojaVirtual.ExceptionLojaVirtual;
import com.App.LojaVirtual.model.PessoaJuridica;
import com.App.LojaVirtual.repository.PessoaRepository;
import com.App.LojaVirtual.service.PessoaUsuarioService;

@RestController
public class PessoaController {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private PessoaUsuarioService pessoaUsuarioService;
	
	@RequestMapping
	@PostMapping(value = "salvarPJ")
	public ResponseEntity<PessoaJuridica> salvarPJ(@RequestBody PessoaJuridica pessoaJuridica) throws ExceptionLojaVirtual {
		
		if(pessoaJuridica == null) {
			throw new ExceptionLojaVirtual("Pessoa juridica não pode ser Null ");
		}
		
		if (pessoaJuridica.getId() == null && pessoaRepository.existeCNPJ(pessoaJuridica.getCnpj()) != null) {
			throw new ExceptionLojaVirtual("Já existe CNPJ Cadastrado com o número " + pessoaJuridica.getCnpj());
		}
		
		pessoaJuridica = pessoaUsuarioService.salvarPessoaJuridica(pessoaJuridica);
		
		
		return new ResponseEntity<PessoaJuridica>(pessoaJuridica, HttpStatus.OK);
	}
}
