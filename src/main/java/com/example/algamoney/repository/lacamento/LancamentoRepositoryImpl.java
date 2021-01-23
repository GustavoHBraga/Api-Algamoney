package com.example.algamoney.repository.lacamento;


import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import  org.springframework.data.domain.Page ;
import  org.springframework.data.domain.PageImpl ;
import  org.springframework.data.domain.Pageable ;
import org.springframework.util.StringUtils;

import com.example.algamoney.model.Categoria_;
import com.example.algamoney.model.Lancamento;
import com.example.algamoney.model.Lancamento_;
import com.example.algamoney.model.Pessoa_;
import com.example.algamoney.repository.filter.LancamentoFilter;
import com.example.algamoney.repository.projection.ResumoLancamento;


public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter,Pageable pageable){
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<Lancamento> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query,pageable);
		
		
		return new PageImpl<>(query.getResultList(), pageable, total(lancamentoFilter));
		
		
	}
	
	/*Metodo que vai estar resumindo a minha pesquisa, onde vai retornar apenas o que eu quero
	  e que é relevante para o usuario*/
	
	@Override
	public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable) {
	
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<ResumoLancamento> criteria = builder.createQuery(ResumoLancamento.class);
		Root <Lancamento> root  = criteria.from(Lancamento.class);
		
		criteria.select(builder.construct(ResumoLancamento.class, 
						root.get(Lancamento_.codigo),  			
						root.get(Lancamento_.descricao),
						root.get(Lancamento_.dataVencimento), 
						root.get(Lancamento_.dataPagamento),
						root.get(Lancamento_.valor),
						root.get(Lancamento_.tipo),
						root.get(Lancamento_.categoria).get(Categoria_.nome),
						root.get(Lancamento_.pessoa).get(Pessoa_.nome)));
						
		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<ResumoLancamento> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query,pageable);
		
		
		return new PageImpl<>(query.getResultList(), pageable, total(lancamentoFilter));
		
	}

	private Predicate[] criarRestricoes(LancamentoFilter lancamentoFilter, CriteriaBuilder builder,
			Root<Lancamento> root) {
		List<Predicate> predicates = new ArrayList<>();
		
		if (!StringUtils.isEmpty(lancamentoFilter.getDescricao())) {
			//WHERE descricao LIKE 'Nome da descricao'
			predicates.add(builder.like(
					builder.lower(root.get(Lancamento_.descricao)),"%" + lancamentoFilter.getDescricao().toLowerCase() + "%"));
		}
		
		if (lancamentoFilter.getDataVencimentoDe() != null) {
			//WHERE dataVencimento >= data
			predicates.add(
					builder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento), lancamentoFilter.getDataVencimentoDe()));
		}
		
		if (lancamentoFilter.getDataVencimentoAte() != null) {
			//WHERE dataVencimento <= data
			predicates.add(
					builder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento), lancamentoFilter.getDataVencimentoAte()));
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
	private void adicionarRestricoesDePaginacao(TypedQuery<?> query,Pageable pageable) {
		
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina= paginaAtual * totalRegistrosPorPagina;
		
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);
	}
	
	private long total(LancamentoFilter lancamentoFilter) {
		//builder que vai permitir a ultilizao do COUNT, ou outros metodos de Consulta dados
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		//onde vai estruturar a base do Select ou Where
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		//onde esta minha tabela, e seus campos.
		Root<Lancamento> root = criteria.from(Lancamento.class);
		//onde vai pegar meu filtro
		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
		
		criteria.where(predicates);
		//segundo o filtro, faço um count a cada linha encontrada
		criteria.select(builder.count(root));
		//retorno apenas o numero de elementos encontrados 
		return manager.createQuery(criteria).getSingleResult();
	}

	

	
	

}
