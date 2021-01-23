package com.example.algamoney.Event;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;

/*Esse é o meu Evento*/
public class RecursoCriadoEvent extends ApplicationEvent {

	private HttpServletResponse response;
	private long codigo;
	
	public RecursoCriadoEvent(Object source, HttpServletResponse response, long codigo) {
		super(source);
		
		this.response = response;
		this.codigo = codigo;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public long getCodigo() {
		return codigo;
	}

	

	
}
