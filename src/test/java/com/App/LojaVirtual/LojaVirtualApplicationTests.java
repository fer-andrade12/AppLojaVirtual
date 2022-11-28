package com.App.LojaVirtual;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer.MockRestServiceServerBuilder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.App.LojaVirtual.controller.acessoController;
import com.App.LojaVirtual.model.Acesso;
import com.App.LojaVirtual.repository.AcessoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import junit.framework.TestCase;

@SpringBootTest(classes = LojaVirtualApplication.class)
class LojaVirtualApplicationTests extends TestCase {

	@Autowired
	private acessoController acessoController;

	@Autowired
	private AcessoRepository acessoRepository;
	
	@Autowired
	private WebApplicationContext wac;

	@Test
	public void TestRestApiCadastroAcesso() throws JsonProcessingException, Exception{
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();
		
		Acesso acesso = new Acesso();
		
		acesso.setDescricao("ROLE_ADMINISTADOR");
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		ResultActions retornoApi = mockMvc.perform(MockMvcRequestBuilders.post("/salvaAcesso")
				.content(objectMapper.writeValueAsString(acesso))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON));
		
		//mostra um retorno em json
		System.out.println("retorno da api: " + retornoApi.andReturn().getResponse().getContentAsString());
		
		Acesso objRetorno = objectMapper.readValue(retornoApi.andReturn().getResponse().getContentAsString(), Acesso.class);
		
		assertEquals(acesso.getDescricao(), objRetorno.getDescricao());
		
	}
	
	@Test
	public void TestRestApiDeletaAcesso() throws JsonProcessingException, Exception{
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();
		
		Acesso acesso = new Acesso();
		
		acesso.setDescricao("ROLE_TESTE_DELETA");
		
		acesso = acessoRepository.save(acesso);
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		ResultActions retornoApi = mockMvc.perform(MockMvcRequestBuilders.post("/deletaAcesso")
				.content(objectMapper.writeValueAsString(acesso))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON));
		
		//mostra um retorno em json
		System.out.println("retorno da api: " + retornoApi.andReturn().getResponse().getContentAsString());
		System.out.println("status da retorno: " + retornoApi.andReturn().getResponse().getStatus());
		
		//valida retorno
		assertEquals("acesso removido ", retornoApi.andReturn().getResponse().getContentAsString());
		assertEquals(200, retornoApi.andReturn().getResponse().getStatus());
	}
	
	@Test
	public void TestRestApiDeletaAcessoPorId() throws JsonProcessingException, Exception{
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();
		
		Acesso acesso = new Acesso();
		
		acesso.setDescricao("ROLE_TESTE_DELETA_ID");
		
		acesso = acessoRepository.save(acesso);
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		ResultActions retornoApi = mockMvc.perform(MockMvcRequestBuilders.delete("/deletaAcessoPorId/" + acesso.getId())
				.content(objectMapper.writeValueAsString(acesso))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON));
		
		//mostra um retorno em json
		System.out.println("retorno da api: " + retornoApi.andReturn().getResponse().getContentAsString());
		System.out.println("status da retorno: " + retornoApi.andReturn().getResponse().getStatus());
		
		assertEquals(200, retornoApi.andReturn().getResponse().getStatus());
	}
	
	@Test
	public void TestRestApiObterAcesso() throws JsonProcessingException, Exception{
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();
		
		Acesso acesso = new Acesso();
		
		acesso.setDescricao("ROLE_OBTER_ACESSO_ID");
		
		acesso = acessoRepository.save(acesso);
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		ResultActions retornoApi = mockMvc.perform(MockMvcRequestBuilders.get("/obterAcesso/" + acesso.getId())
				.content(objectMapper.writeValueAsString(acesso))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON));
		
		assertEquals(200, retornoApi.andReturn().getResponse().getStatus());
		
		Acesso acessoRetorno = objectMapper.readValue(retornoApi.andReturn().getResponse().getContentAsString(), Acesso.class);
		
		assertEquals(acesso.getDescricao(), acessoRetorno.getDescricao());
		
		assertEquals(acesso.getId(), acessoRetorno.getId());
		
	}
	
	
	@Test
	public void TestRestApiObterAcessoPorDesc() throws JsonProcessingException, Exception{
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();
		
		Acesso acesso = new Acesso();
		
		acesso.setDescricao("ROLE_OBTER_ACESSO_LIST");
		
		acesso = acessoRepository.save(acesso);
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		ResultActions retornoApi = mockMvc.perform(MockMvcRequestBuilders.get("/buscarAcessoPorDesc/ACESSO_LIST")
				.content(objectMapper.writeValueAsString(acesso))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON));
		
		assertEquals(200, retornoApi.andReturn().getResponse().getStatus());
		
		List<Acesso> retornoAPIList = objectMapper.readValue(retornoApi
				.andReturn()
				.getResponse().getContentAsString(), new TypeReference <List<Acesso>>() {});
		
		assertEquals(1, retornoAPIList.size());
		
		assertEquals(acesso.getDescricao(), retornoAPIList.get(0).getDescricao());
		
		acessoRepository.deleteById(acesso.getId());
		
	}
	
	 @Test public void testCadastraAcesso() {
	 
	 
	 Acesso acesso = new Acesso();
	 
	 acesso.setDescricao("ROLE_ADMIM");
	 
	 assertEquals(true, acesso.getId() == null);
	 
	 acesso = acessoController.salvarAcesso(acesso).getBody();
	 
	 assertEquals(true, acesso.getId() > 0 );
	 
	 assertEquals("ROLE_ADMIM", acesso.getDescricao());

	 Acesso acesso2 = acessoRepository.findById(acesso.getId()).get();
	
	 assertEquals(acesso.getId(), acesso2.getId());
	
	 acessoRepository.deleteById(acesso2.getId());
	
	 acessoRepository.flush();
	 
	 Acesso acesso3 = acessoRepository.findById(acesso2.getId()).orElse(null);
	 
	 assertEquals(true, acesso3 == null);
	 
	 Acesso acesso1 = new Acesso();
	
	 acesso1.setDescricao("ROLE_ALUNO");
	 
	 acesso1 = acessoController.salvarAcesso(acesso1).getBody();
	 
	 List<Acesso> acessos =
	 acessoRepository.buscaAcessoDesc("ALUNO".trim().toUpperCase());
	 
	 assertEquals(1, acessos.size());
	
	 acessoRepository.deleteById(acesso1.getId());
	
	 }
}
