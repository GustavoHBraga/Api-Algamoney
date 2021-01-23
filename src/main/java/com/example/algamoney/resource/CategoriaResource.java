package com.example.algamoney.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.algamoney.Event.RecursoCriadoEvent;
import com.example.algamoney.model.Categoria;
import com.example.algamoney.repository.CategoriaRepository;
/*Onde vai ficar a parte de comunicacao Http Rest*/
@RestController	//<----- estou dizendo que ele é um controlador Rest, podendo retornar .Json	
@RequestMapping("/categorias")
public class CategoriaResource {
	
	@Autowired//retorne a implementação CategoriaRepository, construtor de classe
	private CategoriaRepository categoriaRepository;
	
	@Autowired	
	private ApplicationEventPublisher publisher;
	
	@GetMapping   //Get buscando todas categorias cadastradas.
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")//<-- permissao que o usuario tem que ter para acessar esse metodo,
	public List<Categoria> listarTodos(){												  //permissao da aplicacao,tipo de scopo que o que cliente teve ter 
		return categoriaRepository.findAll();											  //para usar o metodo..
	}
	
	@PostMapping  //post, enviando dados para o servidor
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA') and #oauth2.hasScope('write')")
	public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {//RequestBody faz que .JSON seja lido e entendido com a Class categoria
		
		Categoria categoriaSalva = categoriaRepository.save(categoria);//Salvo o atributo, no Banco de dados.
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, categoria.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);//retorno 201 created e envio no Http location e o Body contendo (codigo e nome).JSON
	}
	
	
	 
	/*Usando IsPresent da Classe Optional*/
	@GetMapping("/{codigo}")//Implemento a Mapping da classe, ou seja /categorias/codigo
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
	public ResponseEntity<Categoria> FindByCodigo(@PathVariable long codigo){//PathVariable faz o identificador do recurso ser uma variavel
		
		Optional<Categoria> buscado = this.categoriaRepository.findById(codigo);
		
		return buscado.isPresent() ? 
				ResponseEntity.ok(buscado.get()) : ResponseEntity.notFound().build();//IsPresent = se o objeto é diferente que null
																					//? = então  faça X 
																					// : = se nao faça X  
	}
}
