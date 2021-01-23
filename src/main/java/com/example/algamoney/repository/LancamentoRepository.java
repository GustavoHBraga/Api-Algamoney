package com.example.algamoney.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.algamoney.model.Lancamento;
import com.example.algamoney.repository.lacamento.LancamentoRepositoryQuery;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>,LancamentoRepositoryQuery{

}
