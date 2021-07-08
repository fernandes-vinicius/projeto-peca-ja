package com.pecaja.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pecaja.app.models.Endereco;
import com.pecaja.app.repositories.EnderecoRepository;
import com.pecaja.app.repositories.EstadoRepository;

@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository repository;

	public List<Endereco> findAll() {
		return repository.findAll();
	}

	public Endereco findOne(Long id) {
		return repository.getOne(id);
	}

	public Endereco save(Endereco endereco) {
		return repository.saveAndFlush(endereco);
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}
}
