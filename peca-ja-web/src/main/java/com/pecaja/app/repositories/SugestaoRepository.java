package com.pecaja.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pecaja.app.models.Sugestao;

@Repository
public interface SugestaoRepository extends JpaRepository<Sugestao, Long> {

}
