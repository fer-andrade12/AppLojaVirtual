package com.App.LojaVirtual;

import java.util.Calendar;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import com.App.LojaVirtual.controller.PessoaController;
import com.App.LojaVirtual.model.PessoaJuridica;
import com.App.LojaVirtual.repository.PessoaRepository;
import com.App.LojaVirtual.service.PessoaUsuarioService;

import junit.framework.TestCase;

@Profile("test")
@SpringBootTest(classes = LojaVirtualApplication.class)
public class TestePessoaUsuario extends TestCase{

	@Autowired
	private PessoaController pessoaController;
	
	@Test
	public void TestCadastroPessoa() throws ExceptionLojaVirtual {
		
		PessoaJuridica pj = new PessoaJuridica();
		
		pj.setNome("Emprea dev");
		pj.setRazaoSocial("Qualuer");
		pj.setCnpj("" + Calendar.getInstance().getTimeInMillis());
		pj.setEmail("testergmail.com");
		pj.setInscEstatudal("777777777");
		pj.setInscMunicipal("888888888");
		pj.setNomeFantasia("desenvolve sp");
		pj.setTelefone("1111151616548");
		
		pessoaController.salvarPJ(pj);
		
		
	}

}
