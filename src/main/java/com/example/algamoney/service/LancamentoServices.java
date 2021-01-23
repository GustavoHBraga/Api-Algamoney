package com.example.algamoney.service;

import java.util.Optional;

import javax.validation.Valid;

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
		/*Optional é para não precisar do orElse do FindById*/
		Optional<Pessoa> pessoa = pessoaRepository.findById(lancamento.getPessoa().getCodigo());
		
		if (pessoa == null || pessoa.get().isInativo()) {
			 throw new PessoaInexistenteOuInativaException();
		 }
		
		return lancamentoRepository.save(lancamento);
	}
}
