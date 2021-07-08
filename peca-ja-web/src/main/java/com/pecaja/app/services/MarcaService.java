package com.pecaja.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pecaja.app.models.Marca;
import com.pecaja.app.repositories.MarcaRepository;

@Service
public class MarcaService {

	@Autowired
	private MarcaRepository repository;

	public List<Marca> findAll() {
		return repository.findAll();
	}

	public Marca findById(Long id) {
		return repository.getOne(id);
	}

	public Marca save(Marca marca) {
		return repository.saveAndFlush(marca);
	}

	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	public List<Marca> findAllByCategoria(Long categoria_id,Long revendedor_id) {
		return repository.findAllByCategoria(categoria_id, revendedor_id);
	}

	public Marca findMarcaByNome(String nome, Long revendedor_id) {
		return repository.findMarcaByNome(nome,revendedor_id);
	}

}
