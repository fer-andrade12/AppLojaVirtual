package com.App.LojaVirtual.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.App.LojaVirtual.model.PessoaJuridica;
import com.App.LojaVirtual.model.Usuario;
import com.App.LojaVirtual.repository.PessoaRepository;
import com.App.LojaVirtual.repository.UsuarioRepository;

@Service
public class PessoaUsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public PessoaJuridica salvarPessoaJuridica(PessoaJuridica juridica) {
		
		 juridica = pessoaRepository.save(juridica);
		 
		 Usuario usuarioPJ = usuarioRepository.findUserByPessoa(juridica.getId(), juridica.getEmail());
		 
		 if (usuarioPJ == null) {
			 String constraint = usuarioRepository.consultaConstratintAcesso();
			 if (constraint != null) {
				 jdbcTemplate.execute("begin; alter table usuario_acesso drop constraint " +constraint+ ";commit;");
			}
			 
			 usuarioPJ = new Usuario();
			 usuarioPJ.setDataAtualSenha(Calendar.getInstance().getTime());
			 usuarioPJ.setLogin(juridica.getEmail()); //usuarioPJ.setLogin(usuarioPJ.getLogin());
			 usuarioPJ.setPessoa(juridica);
			 usuarioPJ.setEmpresa(juridica);
			 
			 String senha = "" + Calendar.getInstance().getTimeInMillis();
			 String senhaCryp = new BCryptPasswordEncoder().encode(senha);
			 
			 usuarioPJ.setSenha(senhaCryp);
			 
			 usuarioPJ = usuarioRepository.save(usuarioPJ);
			 
			 usuarioRepository.insereAcessoUserPJ(usuarioPJ.getId());
		}
		return juridica;
	}
}
