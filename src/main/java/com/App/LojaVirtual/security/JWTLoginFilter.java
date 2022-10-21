package com.App.LojaVirtual.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.App.LojaVirtual.model.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

	public JWTLoginFilter(String url, AuthenticationManager authenticationManager) {

		/* Obriga a autenticação por url */
		super(new AntPathRequestMatcher(url));

		/* Gerador de autenticação */
		setAuthenticationManager(authenticationManager);
	}

	/* Retorna usuario ao processar a autenticação */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {

		/* Obtem usuário */
		Usuario user = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);

		/* Retorna user com login e senha */

		return getAuthenticationManager()
				.authenticate(new UsernamePasswordAuthenticationToken(user.getLogin(), user.getSenha()));
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		try {
			new JWTTokenAutenticacaoService().addAuthentication(response, authResult.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
