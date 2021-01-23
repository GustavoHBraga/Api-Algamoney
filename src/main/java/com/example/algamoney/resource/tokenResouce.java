package com.example.algamoney.resource;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoney.Config.Property.AlgamoneyapiProperty;

@RestController
@RequestMapping("/tokens")
public class tokenResouce {
	
	@Autowired
	private AlgamoneyapiProperty algamoneyapiProperty;
	
	/**
	 * Logout, sendo invalidado o refreshToken, e removendo ele do Cookie.
	 * 
	 */
	@DeleteMapping("/revoke")
	public void revoke(HttpServletRequest req, HttpServletResponse resp) {
		Cookie cookie = new Cookie("refreshToken",null);
		cookie.setHttpOnly(true);												//assim esta sendo mais protegido 
		cookie.setSecure(algamoneyapiProperty.getSeguranca().isEnableHttps());
		cookie.setPath(req.getContextPath() + "/oauth/token");					//onde a gente vai estar tirando.
		cookie.setMaxAge(0);													//zerar o cookie no exato momento
		
		resp.addCookie(cookie);													//no response add novo cookie só que zerado
		resp.setStatus(HttpStatus.NO_CONTENT.value());							// delete sera sempre 204 no content
	}

}
