package com.example.algamoney.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.algamoney.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long >{

}
