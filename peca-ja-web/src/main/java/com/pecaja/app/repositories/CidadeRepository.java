package com.pecaja.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pecaja.app.models.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long>{
	public Cidade findBynome(String nome);
}
