package com.App.LojaVirtual;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.App.LojaVirtual.controller.acessoController;
import com.App.LojaVirtual.model.Acesso;
import com.App.LojaVirtual.repository.AcessoRepository;

@SpringBootTest(classes = LojaVirtualApplication.class)
class LojaVirtualApplicationTests {

	@Autowired
	private acessoController acessoController;
	
	@Autowired
	private AcessoRepository acessoRepository;

	@Test
	public void testCadastraAcesso() {

		/*
		 * Acesso acesso = new Acesso();
		 * 
		 * acesso.setDescricao("ROLE_ADMIM");
		 * 
		 * assertEquals(true, acesso.getId() == null);
		 * 
		 * acesso = acessoController.salvarAcesso(acesso).getBody();
		 * 
		 * assertEquals(true, acesso.getId() > 0 );
		 * 
		 * assertEquals("ROLE_ADMIM", acesso.getDescricao());
		 * 
		 * Acesso acesso2 = acessoRepository.findById(acesso.getId()).get();
		 * 
		 * assertEquals(acesso.getId(), acesso2.getId());
		 * 
		 * acessoRepository.deleteById(acesso2.getId());
		 * 
		 * acessoRepository.flush();
		 * 
		 * Acesso acesso3 = acessoRepository.findById(acesso2.getId()).orElse(null);
		 * 
		 * assertEquals(true, acesso3 == null);
		 */
		 
		 /* teste de query */
			
			/*
			 * Acesso acesso = new Acesso();
			 * 
			 * acesso.setDescricao("ROLE_ALUNO");
			 * 
			 * acesso = acessoController.salvarAcesso(acesso).getBody();
			 * 
			 * List<Acesso> acessos =
			 * acessoRepository.buscaAcessoDesc("ALUNO".trim().toUpperCase());
			 * 
			 * assertEquals(1, acessos.size());
			 * 
			 * acessoRepository.deleteById(acesso.getId());
			 */
		 
	}
}
														