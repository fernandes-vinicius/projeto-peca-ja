package com.pecaja.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pecaja.app.enums.Situacao;
import com.pecaja.app.enums.Status;
import com.pecaja.app.models.Role;
import com.pecaja.app.models.User;
import com.pecaja.app.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	public List<User> findAll() {
		return repository.findAll();
	}

	public User findById(Long id) {
		return repository.getOne(id);
	}

	public void save(User user) {
		repository.saveAndFlush(user);
	}

	public void deleteById(User user) {
		repository.delete(user);
	}

	public User findByUsername(String username) {
		return repository.findByUsername(username);
	}
	
	public User findByUserCnpj(String cnpj) {
		return repository.findBycnpj(cnpj);
	}
	
	public User findOne(Long id) {
        return repository.getOne(id);
    }
	
	public List<User> findAllBySituacaoAndStatus(Situacao situacao,Status status) {
		return repository.findAllBySituacaoAndStatus(situacao,status);
	}

	public List<User> findAllSituacaoAndEmail(Situacao situacao, String email){
		return repository.findAllBySituacaoAndEmail(situacao, email);
	}

	public int countRevendedoresAtivosAceitos() {
		return repository.countRevendedoresAtivosAceitos(Situacao.ACEITO,Status.ATIVO);
	}

	public int countSolicitacoes() {
		return repository.countSolicitacoes(Situacao.AGUARDANDO);
	}

	public User findByEmail(String email) {
		return repository.findByEmail(email);
	}

}
