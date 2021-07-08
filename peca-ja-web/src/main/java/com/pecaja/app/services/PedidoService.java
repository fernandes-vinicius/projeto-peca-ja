package com.pecaja.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pecaja.app.enums.StatusPedido;
import com.pecaja.app.models.Pedido;
import com.pecaja.app.models.User;
import com.pecaja.app.repositories.PedidoRepository;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repository;

	public List<Pedido> findAll() {
		return repository.findAll();
	}

	public List<Pedido> findAllByCliente(Long cliente_id) {
		return repository.findAllByCliente(cliente_id);
	}

	public Pedido findById(Long id) {
		return repository.getOne(id);
	}

	public Pedido save(Pedido pedido) {
		return repository.saveAndFlush(pedido);
	}

	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	public Pedido findByProduto(Long id) {
		return repository.findByProduto(id);
	}

	public List<Pedido> findAllByStatusPedido(StatusPedido statusPedido) {
		return repository.findAllByStatusPedido(statusPedido);
	}

	public List<Pedido> findAllByRevendedorAndStatusPedido(Long id, StatusPedido statusPedido) {
		return repository.findAllByRevendedorIdAndStatusPedido(id, statusPedido);
	}

	public int countPedidosbyRevendedorwhereStatus(Long revendedor_id,StatusPedido statusPedido) {
		return repository.countPedidosbyRevendedorwhereStatus(revendedor_id, statusPedido);
	}
}
