package com.example.algamoney.Token;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.example.algamoney.Config.Property.AlgamoneyapiProperty;



@ControllerAdvice
public class RefreshTokenPostProcessor implements ResponseBodyAdvice<OAuth2AccessToken> {

	@Autowired
	private AlgamoneyapiProperty algamoneyapiProperty;
	
	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		
		return returnType.getMethod().getName().equals("postAccessToken");//quando da verdadeiro eu vou para segundo medoto
	}

	@Override
	public OAuth2AccessToken beforeBodyWrite(OAuth2AccessToken body, MethodParameter returnType,
			MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
			ServerHttpRequest request, ServerHttpResponse response) {
		
		HttpServletRequest req = ((ServletServerHttpRequest) request).getServletRequest();	//faço um cast para transformar em servlet	
		HttpServletResponse resp = ((ServletServerHttpResponse)response).getServletResponse();//faço um cast para transformar em servlet
		
		DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) body;//faço esse cast para conseguir alterar o corpo do meu token
		String refreshToken = body.getRefreshToken().getValue();//pegando e guardando o cookie, para depois eu tirar do retorno do Json
		
		adicionarRefreshTokenNoCookie(refreshToken,req,resp);
		removerRefreshTokenNoBody(token);
		
		return body;
	}
	
	/**
	 * @param token Tirei o refresh token do body assim não vai estar visivel
	 * para alguem conseguir pegar, vai estar protegido no Cookie
	 */
	private void removerRefreshTokenNoBody(DefaultOAuth2AccessToken token) {
		
		token.setRefreshToken(null);
		
	}

	/*adicionar meu cookei*/
	private void adicionarRefreshTokenNoCookie(String refreshToken, HttpServletRequest req, HttpServletResponse resp) {
		
		Cookie refreshTokenCookie = new Cookie("refreshToken",refreshToken); 
		refreshTokenCookie.setHttpOnly(true);//vai estar todo em Http
		refreshTokenCookie.setSecure(algamoneyapiProperty.getSeguranca().isEnableHttps());
		refreshTokenCookie.setPath(req.getContextPath() + "/oauth/token");//caminho onde vai ser enviado automaticamento.
		refreshTokenCookie.setMaxAge(2593000);//expira em 30 dias 
		
		resp.addCookie(refreshTokenCookie);//resposta vai adicionar o cookie
		
	}
}
