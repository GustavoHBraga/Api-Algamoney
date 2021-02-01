package com.example.algamoney.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.algamoney.model.Pessoa;
import com.example.algamoney.repository.Pessoa.PessoaRepositoryQuery;

public interface PessoaRepository extends JpaRepository<Pessoa, Long >, PessoaRepositoryQuery{

}
