package com.example.algamoney.Config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.example.algamoney.Config.token.CustomTokenEnhancer;

@Profile("oauth-security")
@Configuration
@EnableAuthorizationServer//habilitar a autorização do servidor.
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	
	@Autowired
	private AuthenticationManager authenticationManager;//gerenciar a autenticação
	
	@Autowired
	private UserDetailsService userDetailsService;
	
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
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();//Token melhorado / editado
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accesTokenConverter()));
		
		endpoints
			.tokenStore(tokenStore())
			.tokenEnhancer(tokenEnhancerChain)//passo ele com a informação adicional, e acessToken
			.reuseRefreshTokens(false)
			.userDetailsService(userDetailsService)
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
	
	@Bean
	public TokenEnhancer tokenEnhancer() {
	    return new CustomTokenEnhancer();//retorna um token melhorado e customizado com o nome do usuario
	}
	
	
	

}
