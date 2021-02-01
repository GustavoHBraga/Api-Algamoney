package com.example.algamoney.Config.token;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import com.example.algamoney.Security.UsuarioSistema;

public class CustomTokenEnhancer implements TokenEnhancer {

	/**
	 * no authentication vai ficar o usuario e a senha de quem fez a requisição com 
	 * ela eu faço um cast para UsuarioSistema para estar obtendo o nome da class Usuario
	 * 
	 * metodo onde vamos adicionar uma informação adicional sendo ela
	 * o nome o usuario do sistema no JWT. sendo assim a class Map 
	 * faz todo o processo pra gente onde eu coloco o atributo e o seu valor
	 * no put.
	 * 
	 * e por fim faço o accessToken estar adicionando essa informação no JWT
	 */
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		UsuarioSistema usuarioSistema = (UsuarioSistema) authentication.getPrincipal();
		
		Map<String, Object> addInfo = new HashMap<>();
		addInfo.put("nome", usuarioSistema.getUsuario().getNome());
		
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(addInfo);
		return accessToken;
	}
}
