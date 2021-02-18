package com.example.algamoney.service;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.algamoney.model.Lancamento;
import com.example.algamoney.model.Pessoa;
import com.example.algamoney.repository.LancamentoRepository;
import com.example.algamoney.repository.PessoaRepository;
import com.example.algamoney.service.exception.PessoaInexistenteOuInativaException;

@Service
public class LancamentoServices {
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	
	public Lancamento salvar(@Valid Lancamento lancamento) {
		
		validarPessoa(lancamento);
		return lancamentoRepository.save(lancamento);
	}
	
	public Lancamento atualizar (Long codigo, Lancamento lancamento) {
		Lancamento lancamentoSalvo = buscarLancamentoExistente(codigo);
		
		/*se caso for atualizar pessoa tenho que validar*/
		if(!lancamento.getPessoa().equals(lancamentoSalvo.getPessoa())) {
			validarPessoa(lancamentoSalvo);
		}
		
		/* Lancamento onde as propriedades vão ser modificadas e LancamentoSalvo onde cujas propriedades
		 * são recuperadas ignorando o codigo que é o id */
		BeanUtils.copyProperties(lancamento, lancamentoSalvo, "codigo");
		return lancamentoRepository.save(lancamentoSalvo);
	}
	
	public void validarPessoa(Lancamento lancamento) {
		
		Pessoa pessoa = null;
		
		if(lancamento.getPessoa() != null) {
			 pessoa = pessoaRepository.findById(lancamento.getPessoa().getCodigo()).orElse(null);
		}
		
		if(pessoa == null || pessoa.isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		}
	}
	
	public Lancamento buscarLancamentoExistente(Long codigo) {
		
		Lancamento lancamentoSalvo = lancamentoRepository.findById(codigo).orElse(null);
		
		if(lancamentoSalvo == null) {
			throw new IllegalArgumentException();	
		}
		
		return lancamentoSalvo;
	}

}
