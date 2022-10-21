package com.App.LojaVirtual.security;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.App.LojaVirtual.ApplicationContextLoad;
import com.App.LojaVirtual.model.Usuario;
import com.App.LojaVirtual.repository.UsuarioRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
@Component
public class JWTTokenAutenticacaoService {

	/* Token validação 3 dias */
	private static final long EXPIRATION_TIME = 259990000;

	/* Chave secreta pessoal */
	private static final String SECRET = "Djd12123#";

	/* Chave secreta pessoal */
	private static final String TOKEN_PREFIX = "Bearer";

	/* Chave secreta pessoal */
	private static final String HEADER_STRING = "Authorization";

	public void addAuthentication(HttpServletResponse response, String username) throws Exception {

		/* Contruindo o token */
		String JWT = Jwts.builder() /* Chama o gerador */
				.setSubject(username) /* Adiciona o usuario */
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) /* Tempo de expiração */
				.signWith(SignatureAlgorithm.HS512, SECRET).compact(); /* Adiciona a chave secreta */

		/*
		 * Exemplo: Bearer
		 * *-d5d5d5d45d45d4dd54d5s4s54ds54ds5a45.ad4ad45as4d5as4d5sa4d5as4d.
		 * dasd4as5d4as5
		 */
		String token = TOKEN_PREFIX + " " + JWT;

		/* Da resposta para o cliente, outra api, navagador, app etc.. */
		response.addHeader(HEADER_STRING, token);

		liberaCors(response);
		
		/* Usado no postman para teste */
		response.getWriter().write("{\"Authorization\": \"" + token + "\"}");

	}
	
	public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response) {
		
		String token = request.getHeader(HEADER_STRING);
		
		if(token != null) {
			
			String tokenLimpo = token.replace(TOKEN_PREFIX, "").trim();
			
			String user = Jwts.parser()
					.setSigningKey(SECRET)
					.parseClaimsJws(tokenLimpo)
					.getBody().getSubject();
			
			if (user != null) {
				Usuario usuario = ApplicationContextLoad
						.getApplicationContext()
						.getBean(UsuarioRepository.class)
						.findUserByLogin(user);
				
				if(usuario != null) {
					return new UsernamePasswordAuthenticationToken(
							usuario.getLogin(), 
							usuario.getPassword(), 
							usuario.getAuthorities());
				}
				
			}
			
		}
		
		liberaCors(response);
		return null;
	}

	/* Faz a liberação de cors no navegador */
	private void liberaCors(HttpServletResponse response) {
		if (response.getHeader("Access-Control-Allow-Origin") == null) {
			response.addHeader("Access-Control-Allow-Origin", "*");
		}

		if (response.getHeader("Access-Control-Allow-Headers") == null) {
			response.addHeader("Access-Control-Allow-Headers", "*");
		}

		if (response.getHeader("Access-Control-Request-Headers") == null) {
			response.addHeader("Access-Control-Request-Headers", "*");
		}

		if (response.getHeader("Access-Control-Allow-Methods") == null) {
			response.addHeader("Access-Control-Allow-Methods", "*");
		}
	}

}
