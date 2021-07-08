package com.pecaja.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pecaja.app.enums.Situacao;
import com.pecaja.app.enums.Status;
import com.pecaja.app.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);
	
	User findBycnpj(String cnpj);
	
	List<User> findAllBySituacaoAndStatus(Situacao situacao,Status status);
	
	//List<User> findAllUsers(Situacao situacao, Status status);
		

	List<User> findAllBySituacaoAndEmail(Situacao situacao, String email);

	@Query("SELECT COUNT(id) FROM User u WHERE  situacao = ?1 AND status = ?2")
	int countRevendedoresAtivosAceitos(Situacao situacao, Status status);
	
	@Query("SELECT COUNT(id) FROM User u WHERE  situacao = ?1")
	int countSolicitacoes(Situacao situacao);

	User findByEmail(String email);
	
}
