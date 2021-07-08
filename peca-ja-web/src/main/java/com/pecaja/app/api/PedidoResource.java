package com.pecaja.app.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pecaja.app.enums.Status;
import com.pecaja.app.enums.StatusPedido;
import com.pecaja.app.models.Cliente;
import com.pecaja.app.models.Historico;
import com.pecaja.app.models.Pedido;
import com.pecaja.app.models.Produto;
import com.pecaja.app.services.ClienteService;
import com.pecaja.app.services.HistoricoService;
import com.pecaja.app.services.PedidoService;
import com.pecaja.app.services.ProdutoService;

@RestController
@RequestMapping("/api/pedido")
@CrossOrigin(origins = { "http://localhost:8080" })
public class PedidoResource {
	
	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private HistoricoService historicoService;
	
	@Autowired
	ProdutoService produtoService;
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Pedido> findById(@PathVariable Long id) {

		Pedido pedido = pedidoService.findById(id);
		if (pedido != null)
			return ResponseEntity.ok(pedido);
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Pedido> save(@Valid @RequestBody Pedido pedido) {	
		
		pedido.setData(new Date());
		pedido.setStatus(Status.ATIVO);
		pedido.setStatusPedido(StatusPedido.AGUARDANDO);
		pedido.setEstimativa_entrega(pedido.getEstimativa_entrega());
		pedido.setCliente(clienteService.findByUsername(pedido.getCliente().getUsername()));
		//pedido.setRevendedor();

		List<Pedido> pedidos = new ArrayList<>();
		pedidos.add(pedido);
		
		Historico historico = new Historico();
		historico.setData(pedido.getData());
		historico.setStatus(Status.ATIVO);
		historico.setPedidos(pedidos);
		//historico.setUser();
		
		Produto produto = produtoService.findById(pedido.getProduto().getId());
		produto.setQuantidade(produto.getQuantidade() - pedido.getQuantidade());
		produtoService.save(produto);
		
		pedidoService.save(pedido);
		historicoService.save(historico);
		
		return new ResponseEntity<Pedido>(pedido, HttpStatus.CREATED);
	}
	
	@DeleteMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<Void> remove(@PathVariable Long id) {

		Pedido pedido = pedidoService.findById(id);
		if (pedido != null) {
			pedido.setStatus(Status.INATIVO);
			pedido.setStatusPedido(StatusPedido.CANCELADO);
			pedidoService.save(pedido);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.noContent().build();
	}

}
