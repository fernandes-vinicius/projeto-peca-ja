package com.pecaja.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pecaja.app.models.Cidade;
import com.pecaja.app.models.Marca;
import com.pecaja.app.repositories.CidadeRepository;

@Service
public class CidadeService {
	@Autowired
	private CidadeRepository repository;

	public List<Cidade> findAll() {
		return repository.findAll();
	}

	public Cidade findOne(Long id) {
		return repository.getOne(id);
	}

	public Cidade save(Cidade cidade) {
		return repository.saveAndFlush(cidade);
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	public Cidade findByNome(String nome) {
		return repository.findBynome(nome);
	}
}
