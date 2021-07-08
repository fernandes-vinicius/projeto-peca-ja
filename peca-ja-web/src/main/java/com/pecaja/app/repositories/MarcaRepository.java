package com.pecaja.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pecaja.app.models.Marca;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Long> {

	@Query("SELECT m FROM Marca m WHERE categoria_id = ?1 AND revendedor_id = ?2")
	public List<Marca> findAllByCategoria(Long categoria_id, Long revendedor_id);

	@Query("SELECT m FROM Marca m WHERE LOWER(m.nome) = LOWER(?1) AND revendedor_id = ?2")
	public Marca findMarcaByNome(String nome, Long revendedor_id);
}
