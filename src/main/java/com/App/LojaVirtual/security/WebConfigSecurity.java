package com.App.LojaVirtual.security;

import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.App.LojaVirtual.service.ImplementacaoUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebConfigSecurity implements HttpSessionListener {

	@Autowired
	private ImplementacaoUserDetailsService implementacaoUserDetailsService;

	@Bean
	public SecurityFilterChain web(HttpSecurity http) throws Exception {
		 http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
		 .disable().authorizeRequests()
		 .antMatchers("/").permitAll()
		 .antMatchers("/index").permitAll().antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
		 .anyRequest()
		 .authenticated()
		 .and().logout().logoutSuccessUrl("/index")
		 .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		 .and()
		 .addFilterAfter(new JWTLoginFilter("/login", authentication -> authentication), UsernamePasswordAuthenticationFilter.class)
		 .addFilterBefore(new JWTApiAutenticacaoFilter(), UsernamePasswordAuthenticationFilter.class);
		return null;
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(implementacaoUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

	/* Ignora url
	 * @Bean public WebSecurityCustomizer webSecurityCustomizer() { return (web) ->
	 * web.ignoring() .antMatchers("/salvaAcesso", "/deletaAcesso",
	 * "/buscarAcessoPorDesc"); }
	 */
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}


