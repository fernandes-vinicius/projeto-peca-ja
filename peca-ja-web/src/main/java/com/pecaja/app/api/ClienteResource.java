package com.pecaja.app.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pecaja.app.models.Cidade;
import com.pecaja.app.models.Cliente;
import com.pecaja.app.models.Endereco;
import com.pecaja.app.models.Estado;
import com.pecaja.app.models.Pedido;
import com.pecaja.app.services.CidadeService;
import com.pecaja.app.services.ClienteService;
import com.pecaja.app.services.EnderecoService;
import com.pecaja.app.services.EstadoService;
import com.pecaja.app.services.PedidoService;

@RestController
@RequestMapping("/api/cliente")
@CrossOrigin(origins = { "http://localhost:8080" })
public class ClienteResource {

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private EstadoService estadoService;

	@Autowired
	private CidadeService cidadeService;

	@Autowired
	private EnderecoService enderecoService;

	@Autowired
	private PedidoService pedidoService;

	@PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Cliente> login(@RequestParam("username") String username,
			@RequestParam("password") String password) {
		
		Cliente cliente = clienteService.findByUsername(username);
		if (cliente != null) {
			
			if (new BCryptPasswordEncoder().matches(password, cliente.getPassword()))
				return ResponseEntity.ok(cliente);
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Cliente> save(@RequestBody Cliente cliente) {

		Estado estado = estadoService.findByUf(cliente.getEndereco().getEstado().getUf());
		Cidade cidade = cidadeService.findByNome(cliente.getEndereco().getCidade().getNome());

		Endereco endereco = new Endereco(null, cliente.getEndereco().getRua(), cliente.getEndereco().getBairro(),
				cliente.getEndereco().getNumero(), estado, cidade);
		endereco.setLatitude(cliente.getEndereco().getLatitude());
		endereco.setLongitude(cliente.getEndereco().getLongitude());
		enderecoService.save(endereco);

		Cliente c = clienteService.findByUsernameOrEmailOrCpf(cliente.getUsername(), cliente.getEmail(),
				cliente.getCpf());

		if (c == null) {
			cliente.setEndereco(endereco);
			cliente.setPassword(new BCryptPasswordEncoder().encode(cliente.getPassword()));
			clienteService.save(cliente);
			return new ResponseEntity<Cliente>(cliente, HttpStatus.CREATED);
		}
		return new ResponseEntity<Cliente>(cliente, HttpStatus.CONFLICT);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<Cliente>> findAll() {

		List<Cliente> clientes = clienteService.findAll();
		if (clientes.isEmpty())
			return new ResponseEntity<List<Cliente>>(HttpStatus.NO_CONTENT);

		return new ResponseEntity<List<Cliente>>(clientes, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Cliente> findById(@PathVariable Long id) {

		Cliente cliente = clienteService.findById(id);
		if (cliente != null)
			return ResponseEntity.ok(cliente);

		return ResponseEntity.notFound().build();
	}

	@PutMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<Cliente> update(@PathVariable Long id, @Valid @RequestBody Cliente cliente) {
		
		Cliente clientedb = clienteService.findById(id);
		if (clientedb != null) {

			Endereco enderecodb = enderecoService.findOne(cliente.getEndereco().getId());
			BeanUtils.copyProperties(cliente.getEndereco(), enderecodb);
			enderecodb = enderecoService.save(enderecodb);
			
			BeanUtils.copyProperties(cliente, clientedb, "id");
			clientedb = clienteService.save(clientedb);
		
			return ResponseEntity.ok(clientedb);
		}
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<Void> remove(@PathVariable Long id) {

		Cliente cliente = clienteService.findById(id);
		if (cliente != null) {

			clienteService.deleteById(id);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.noContent().build();
	}

	@GetMapping(value = "pedidos/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	private ResponseEntity<List<Pedido>> findAllPedidos(@PathVariable Long id) {

		List<Pedido> pedidos = null;
		Cliente cliente = clienteService.findById(id);

		if (cliente != null) {
			pedidos = pedidoService.findAllByCliente(id);

			if (!pedidos.isEmpty())
				return new ResponseEntity<List<Pedido>>(pedidos, HttpStatus.OK);

			return new ResponseEntity<List<Pedido>>(pedidos, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Pedido>>(pedidos, HttpStatus.NOT_FOUND);
	}

}
