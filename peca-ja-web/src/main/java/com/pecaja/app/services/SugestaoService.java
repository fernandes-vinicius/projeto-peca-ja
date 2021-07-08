package com.pecaja.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pecaja.app.models.Sugestao;
import com.pecaja.app.repositories.SugestaoRepository;

@Service
public class SugestaoService {

	@Autowired
	private SugestaoRepository repository;

	public List<Sugestao> findAll() {
		return repository.findAll();
	}

	public Sugestao findById(Long id) {
		return repository.getOne(id);
	}

	public Sugestao save(Sugestao sugestao) {
		return repository.saveAndFlush(sugestao);
	}

	public void deleteById(Long id) {
		repository.deleteById(id);
	}

}
