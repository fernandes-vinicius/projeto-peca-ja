package com.pecaja.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pecaja.app.models.Estado;
import com.pecaja.app.repositories.EstadoRepository;

@Service
public class EstadoService {
	@Autowired
	private EstadoRepository repository;

	public List<Estado> findAll() {
		return repository.findAll();
	}

	public Estado findOne(Long id) {
		return repository.getOne(id);
	}

	public Estado save(Estado estado) {
		return repository.saveAndFlush(estado);
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	public Estado findByUf(String uf) {
		return repository.findByuf(uf);
	}
}
