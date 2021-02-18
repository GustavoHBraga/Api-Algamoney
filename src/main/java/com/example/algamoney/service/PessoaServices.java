package com.example.algamoney.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.algamoney.model.Endereco;
import com.example.algamoney.model.Pessoa;
import com.example.algamoney.repository.PessoaRepository;
import com.example.algamoney.service.util.CepAutomate;

@Service
public class PessoaServices {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	/*Atualizar tudo*/
	public Pessoa atualizar (long codigo, Pessoa pessoa) {
		
		Pessoa pessoaSalva = buscarPeloCodigo(codigo);
		BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");
		return pessoaRepository.save(pessoaSalva);
	}
	/*Atualizar apenas Ativo*/
	public Pessoa atualizarPropriedadeAtivo (long codigo, boolean ativo) {
		
		Pessoa pessoaSalva = buscarPeloCodigo(codigo);
		pessoaSalva.setAtivo(ativo);
		return pessoaRepository.save(pessoaSalva);
	}
	/*atualizar apenas o Endereco*/
	public Pessoa atualizarPropriedadeEndereco(long codigo, Endereco endereco) {
		
		Pessoa pessoaSalva = buscarPeloCodigo(codigo);
		pessoaSalva.setEndereço(endereco);
		return pessoaRepository.save(pessoaSalva);
	}

	public Pessoa buscarPeloCodigo(long codigo) {
		Pessoa pessoaSalva = this.pessoaRepository.findById(codigo)
			      .orElseThrow(() -> new EmptyResultDataAccessException(1));
		
		return pessoaSalva;
	}
}
