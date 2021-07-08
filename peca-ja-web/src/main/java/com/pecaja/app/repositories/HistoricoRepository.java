package com.pecaja.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.pecaja.app.models.Historico;


@Repository
public interface HistoricoRepository extends JpaRepository<Historico, Long> {
	
}

