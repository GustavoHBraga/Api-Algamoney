package com.example.algamoney.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.algamoney.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
	
}
