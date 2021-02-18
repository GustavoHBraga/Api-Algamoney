package com.example.algamoney.service.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

public class CepAutomate {
	
	@Autowired
	JSONObject json;

	public CepAutomate() {}
	
	
	public void GetJsonCep(String cep) {
		
		try {
			
			URL url = new URL("https://api.postmon.com.br/v1/cep/"+cep);
			
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			BufferedReader in = new BufferedReader(
		             new InputStreamReader(con.getInputStream()));
			
			String inputLine;
		    StringBuffer response = new StringBuffer();
		    
		    while ((inputLine = in.readLine()) != null) {
		     	response.append(inputLine);
		    }
		    
		    in.close();
		    
		    JSONObject GetJsonText = json.getJSONObject(response.toString());
		    
		    
		
		//exception de URL mal formada	
		} catch (MalformedURLException e) {
			
			
			
		//exception de conexão falou;	
		} catch (IOException e) {
			
			
			
		//exception de leitura de Json	
		} catch (JSONException e) {
			
			
		}
		
	}
	// TODO: falta fazer a logica :)
	
	
}
