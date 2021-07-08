package com.pecaja.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pecaja.app.models.Categoria;
import com.pecaja.app.repositories.CategoriaRepository;

@Service
public class PerfilService {

	@Autowired
	private CategoriaRepository repository;

	public List<Categoria> findAll() {
		return repository.findAll();
	}

	public Categoria findById(Long id) {
		return repository.getOne(id);
	}

	public Categoria save(Categoria categoria) {
		return repository.saveAndFlush(categoria);
	}

	public void deleteById(Long id) {
		repository.deleteById(id);
	}

}
