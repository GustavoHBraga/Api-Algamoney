package com.example.algamoney.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer//habilitar a autorização do servidor.
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	
	@Autowired
	private AuthenticationManager authenticationManager;//gerenciar a autenticação
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
					.withClient("angular")							//clientes
					.secret("@ngul@r0")								//senha do cliente
					.scopes("read","write")							//acesso
					.authorizedGrantTypes("password","refresh_token")//fluxo password flow e Refresh_token
					.accessTokenValiditySeconds(1800)				//acess token expira 1h
					.refreshTokenValiditySeconds(3600 * 24 )		//vai expirar depois de 24h
				.and()
					.withClient("mobile")
					.secret("m0b1l30")
					.scopes("read")
					.authorizedGrantTypes("password","refresh_token")
					.accessTokenValiditySeconds(1800)
					.refreshTokenValiditySeconds(3600 * 24 );
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints
			.tokenStore(tokenStore())//armazena o token, para aplicação buscar o token, depois manda token para o cliente usar
			.accessTokenConverter(accesTokenConverter())
			.reuseRefreshTokens(false)//vai ser renovado acada access token for requisitado.
			.authenticationManager(authenticationManager);
	}

	@Bean
	public JwtAccessTokenConverter accesTokenConverter() {
		JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
		accessTokenConverter.setSigningKey("algaworks");//chave que valida um Token
		return accessTokenConverter;
	}
	
	@Bean
	public TokenStore tokenStore() {//armazenar o token
		return new JwtTokenStore(accesTokenConverter());//armazenar o token convertido
	}
	
	
	
	

}
