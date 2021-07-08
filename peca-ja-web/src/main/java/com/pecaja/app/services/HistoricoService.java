package com.pecaja.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pecaja.app.models.Endereco;
import com.pecaja.app.models.Historico;
import com.pecaja.app.repositories.EnderecoRepository;
import com.pecaja.app.repositories.EstadoRepository;
import com.pecaja.app.repositories.HistoricoRepository;

@Service
public class HistoricoService {

	@Autowired
	private HistoricoRepository repository;

	public List<Historico> findAll() {
		return repository.findAll();
	}

	public Historico findOne(Long id) {
		return repository.getOne(id);
	}

	public Historico save(Historico historico) {
		return repository.saveAndFlush(historico);
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}
}
