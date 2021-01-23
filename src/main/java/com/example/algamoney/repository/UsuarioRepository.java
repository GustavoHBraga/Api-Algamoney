package com.example.algamoney.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.algamoney.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario,Long>{

	//se caso não encontrar não preciso ficar colocando se é diferente de null
	Optional<Usuario> findByEmail(String email);
}
