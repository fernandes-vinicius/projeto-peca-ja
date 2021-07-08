package com.pecaja.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pecaja.app.enums.Status;
import com.pecaja.app.models.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	Cliente findByUsername(String username);
	
	Cliente findByUsernameAndPassword(String username, String password);
		
	@Query("SELECT c FROM Cliente c WHERE username = ?1 OR email = ?2 OR cpf = ?3")
	Cliente findByUsernameOrEmailOrCpf(String username, String email, String cpf);

	@Query("SELECT COUNT(id) FROM Cliente")
	int countClientes();
	
	List<Cliente> findByStatus(Status status);
}
