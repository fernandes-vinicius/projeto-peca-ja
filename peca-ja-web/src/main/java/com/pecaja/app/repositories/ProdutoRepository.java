package com.pecaja.app.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pecaja.app.enums.Status;
import com.pecaja.app.models.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	@Query("SELECT p FROM Produto p WHERE categoria_id = ?1 AND marca_id = ?2 AND peso = ?3 AND revendedor_id = ?4")
	public Produto findByCategoriaByMarcaByPesoByRevendedor(Long categoria_id, Long marca_id, double peso, Long revendedor_id);

	@Query("SELECT p FROM Produto p WHERE revendedor_id = ?1 AND status = ?2")
	public List<Produto> findAllByRevendedor(Long revendedor_id, Status status);
	
	@Query("SELECT sum(quantidade) FROM Produto p WHERE  revendedor_id = ?1 AND status = ?2")
	public int sumProdutosbyRevendedorwhereStatus(Long revendedor_id, Status status);

}
