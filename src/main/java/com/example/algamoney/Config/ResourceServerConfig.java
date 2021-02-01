package com.example.algamoney.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;

@Profile("oauth-security")
@Configuration
@EnableWebSecurity
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{

	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	  auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/categorias").permitAll()//permitido para todos acessarem categorias
			.anyRequest().authenticated().and()//as demais vao precisar de autenticação
		    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		    .csrf().disable();//desabilita javaScript web services
	}
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
	
		resources.stateless(true);//nao vai mander estado de nada meu servidor
	}
	
	//onde vai tirar a senhas encodadas dos Usuarios.
	private PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}	
	
	//adicionar um Bean com object OAuth2 Handler, assim vou estar garantindo a segurança
	@Bean
	public MethodSecurityExpressionHandler createExpressionHandler() {
		return new OAuth2MethodSecurityExpressionHandler();
	}
	
	
}
