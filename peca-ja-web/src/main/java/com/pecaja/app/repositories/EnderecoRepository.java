package com.pecaja.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.pecaja.app.models.Endereco;
import com.pecaja.app.models.Estado;


@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
	
}

