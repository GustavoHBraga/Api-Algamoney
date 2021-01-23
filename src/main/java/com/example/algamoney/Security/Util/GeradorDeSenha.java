package com.example.algamoney.Security.Util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class GeradorDeSenha {

		
	public static void main(String[] args) {
	
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String senha = encoder.encode("admin");
		
		System.out.println("Senha Encodada : { " + senha + " }");
		
	}
}
