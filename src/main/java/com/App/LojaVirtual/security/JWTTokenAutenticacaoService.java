package com.App.LojaVirtual.security;

import java.io.IOException;
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

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;


// Cria a autenticação e retorna também a autenticação JWT 
@Service
@Component
public class JWTTokenAutenticacaoService {

	private static final long EXPIRATION_TIME = 259990000;

	private static final String SECRET = "Djd12123#";

	private static final String TOKEN_PREFIX = "Bearer";

	private static final String HEADER_STRING = "Authorization";

	public void addAuthentication(HttpServletResponse response, String username) throws Exception {

		String JWT = Jwts.builder()
				.setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) 
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();

		String token = TOKEN_PREFIX + " " + JWT;

		response.addHeader(HEADER_STRING, token);

		liberaCors(response);
		
		response.getWriter().write("{\"Authorization\": \"" + token + "\"}");

	}
	
	public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String token = request.getHeader(HEADER_STRING);
		
		try {
			
			if(token != null) {
				
				String tokenLimpo = token.replace(TOKEN_PREFIX, "").trim();
				
				String user = Jwts.parser()
						.setSigningKey(SECRET)
						.parseClaimsJws(tokenLimpo)
						.getBody()
						.getSubject();
				
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
		}catch (SignatureException e) {
			response.getWriter().write("Token está inválido.");

		}catch (ExpiredJwtException e) {
			response.getWriter().write("Token está expirado, efetue o login novamente.");
		}finally {
			liberaCors(response);
		}
		return null;
		
	}

	/* Faz a liberação de cors no navegador para evitar problema de cors*/
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
