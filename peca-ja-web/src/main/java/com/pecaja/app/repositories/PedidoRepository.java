package com.pecaja.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pecaja.app.enums.StatusPedido;
import com.pecaja.app.models.Pedido;
import com.pecaja.app.models.Produto;
import com.pecaja.app.models.User;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

	@Query("SELECT p FROM Pedido p WHERE cliente_id = ?1")
	public List<Pedido> findAllByCliente(Long cliente_id);

	public Pedido findByProduto(Long id);
	
	public List<Pedido> findAllByRevendedorIdAndStatusPedido(Long id, StatusPedido statusPedido);
	
	public List<Pedido> findAllByStatusPedido(StatusPedido statusPedido);

	@Query("SELECT COUNT(id) FROM Pedido p WHERE  revendedor_id = ?1 AND statusPedido = ?2")
	public int countPedidosbyRevendedorwhereStatus(Long revendedor_id, StatusPedido statusPedido);

}
