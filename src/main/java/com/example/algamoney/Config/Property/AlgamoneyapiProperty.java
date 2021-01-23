package com.example.algamoney.Config.Property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("algamoney")
public class AlgamoneyapiProperty {

	private final Seguranca seguranca = new Seguranca(); 
	
	private final OriginPermitida originPermitida = new OriginPermitida();

	
	public Seguranca getSeguranca() {
		return seguranca;
	}
	
	
	public OriginPermitida getOriginPermitida() {
		return originPermitida;
	}


	/**
     * classe profile de seguranca 
     * podemos estar habilitando o
     * https pelo application-prod
     * deixando para true;
     */
	public static class Seguranca{
		
		private boolean enableHttps;

		public boolean isEnableHttps() {
			return enableHttps;
		}

		public void setEnableHttps(boolean enableHttps) {
			this.enableHttps = enableHttps;
		}
		
	}
	
	/**
	 * origin permitida como padrão está a do 8080
	 * e como pré setada a 8000, posso estar alterando
	 * no application-prod
	 */
	public static class OriginPermitida{
		
		private String originPermitida = "http://localhost:8000";

		public String getOriginPermitida() {
			return originPermitida;
		}

		public void setOriginPermitida(String originPermitida) {
			this.originPermitida = originPermitida;
		}
		
	}
	
}
