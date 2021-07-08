package com.pecaja.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pecaja.app.models.Role;
import com.pecaja.app.repositories.RoleRepository;

@Service
public class RoleService {

	@Autowired
	private RoleRepository repository;

	public List<Role> findAll() {
		return repository.findAll();
	}

	public Role findById(Long id) {
		return repository.getOne(id);
	}
	
	public Role findByNome(String nome) {
		return repository.findByNome(nome);
	}

	public void save(Role role) {
		repository.saveAndFlush(role);
	}

	public void deleteById(Long id) {
		repository.deleteById(id);
	}

}
