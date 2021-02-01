package com.example.algamoney.repository.Pessoa;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Predicate;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.example.algamoney.model.Lancamento;
import com.example.algamoney.model.Lancamento_;
import com.example.algamoney.model.Pessoa;
import com.example.algamoney.model.Pessoa_;
import com.example.algamoney.repository.filter.PessoaFilter;

public class PessoaRepositoryImpl implements PessoaRepositoryQuery{

	@Autowired
	private EntityManager manager;
	
	/**
	 * Filtrar pelo nome da pessoa.
	 * 
	 */
	@Override
	public Page<Pessoa> filtrar(PessoaFilter pessoaFilter, Pageable pageable) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Pessoa> criteria = builder.createQuery(Pessoa.class);
		Root<Pessoa> root = criteria.from(Pessoa.class);
		
		Predicate[] predicates = criarRestricoes(pessoaFilter, builder, root);
		
		criteria.where(predicates);
		TypedQuery<Pessoa> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query,pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(pessoaFilter));
	}


	private Predicate[] criarRestricoes(PessoaFilter pessoaFilter, CriteriaBuilder builder, Root<Pessoa> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		if(!StringUtils.isEmpty(pessoaFilter.getNome())) {
			//Where nome like '% nome %'
			predicates.add(builder.like(
					builder.lower(root.get(Pessoa_.nome)),"%" + pessoaFilter.getNome().toLowerCase() + "%"));
		}
		return predicates.toArray(new Predicate[predicates.size()]);
	}

	private long total(PessoaFilter pessoaFilter) {
		
		//builder que vai permitir a ultilizao do COUNT, ou outros metodos de Consulta dados
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		//onde vai estruturar a base do Select ou Where
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		//onde esta minha tabela, e seus campos.
		Root<Pessoa> root = criteria.from(Pessoa.class);
		//onde vai pegar meu filtro
		Predicate[] predicates = criarRestricoes(pessoaFilter, builder, root);

		criteria.where(predicates);
		//segundo o filtro, faço um count a cada linha encontrada
		criteria.select(builder.count(root));
		//retorno apenas o numero de elementos encontrados 
		return manager.createQuery(criteria).getSingleResult();
	}
	
	private void adicionarRestricoesDePaginacao(TypedQuery<?> query,Pageable pageable) {
		
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina= paginaAtual * totalRegistrosPorPagina;
		
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);
	}
}
