package com.example.algamoney.Token;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.util.ParameterMap;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
/**
 * Vou adicionar o Json e automatizar o processo de cookie de renovação
 * de Cookie
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)//alta prioridade para que ser for refresh token para adicionar na requesição
public class RefreshTokenCookiePreProcessorFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		if("/oauth/token".equalsIgnoreCase(req.getRequestURI())
				&& "refresh_token".equals(req.getParameter("grant_type"))
				&& req.getCookies() != null){

			for(Cookie cookie : req.getCookies()) {
				
				if(cookie.getName().equalsIgnoreCase("refreshToken")) {
					String refreshToken = cookie.getValue();
					req = new MyServletResquestWrapper(req, refreshToken);
				}
			}	
		}	
		chain.doFilter(req, response);
	}
	
	static class MyServletResquestWrapper extends HttpServletRequestWrapper{
		
		private String refreshToken;
		
		public MyServletResquestWrapper(HttpServletRequest request, String refreshToken) {
			super(request);
			this.refreshToken = refreshToken;
		}
		
		@Override
		public Map<String, String[]> getParameterMap() {
			ParameterMap<String, String[]> map = new ParameterMap<>(getRequest().getParameterMap());
			map.put("refresh_token", new String[] {refreshToken});
			map.setLocked(true);
			return map;
		}
		
	}
}
