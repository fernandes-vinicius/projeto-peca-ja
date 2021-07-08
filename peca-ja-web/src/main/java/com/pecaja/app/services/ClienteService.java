package com.pecaja.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pecaja.app.enums.Status;
import com.pecaja.app.models.Cliente;
import com.pecaja.app.repositories.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;

	public List<Cliente> findAll() {
		return repository.findAll();
	}

	public Cliente findById(Long id) {
		return repository.getOne(id);
	}

	public Cliente save(Cliente cliente) {
		return repository.saveAndFlush(cliente);
	}

	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	public Cliente findByUsername(String username) {
		return repository.findByUsername(username);
	}
	

	public Cliente findByUsernameAndPassword(String username, String password) {
		return repository.findByUsernameAndPassword(username, password);
	}

	public Cliente findByUsernameOrEmailOrCpf(String username, String email, String cpf) {
		return repository.findByUsernameOrEmailOrCpf(username, email, cpf);
	}

	public int countClientes() {
		return repository.countClientes();
	}
	
	public List<Cliente> findByStatus(Status status){
		return repository.findByStatus(status);
	}
}
