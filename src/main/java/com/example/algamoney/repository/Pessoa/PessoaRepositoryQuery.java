package com.example.algamoney.repository.Pessoa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.algamoney.model.Pessoa;
import com.example.algamoney.repository.filter.PessoaFilter;

public interface PessoaRepositoryQuery {
	
	public Page<Pessoa> filtrar(PessoaFilter pessoaFilter, Pageable pageable);

}
