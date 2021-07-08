package com.pecaja.app.integracao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.pecaja.app.enums.Status;
import com.pecaja.app.models.Categoria;
import com.pecaja.app.models.Cliente;
import com.pecaja.app.models.Marca;
import com.pecaja.app.models.Pedido;
import com.pecaja.app.models.Produto;
import com.pecaja.app.models.User;
import com.pecaja.app.repositories.CategoriaRepository;
import com.pecaja.app.repositories.ClienteRepository;
import com.pecaja.app.repositories.MarcaRepository;
import com.pecaja.app.repositories.PedidoRepository;
import com.pecaja.app.repositories.ProdutoRepository;
import com.pecaja.app.repositories.UserRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PedidoRepositoryImplTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Autowired
	PedidoRepository pedidoRepository;

	@Autowired
	ClienteRepository clienteRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private MarcaRepository marcaRepository;

	@Autowired
	private UserRepository userRepository;

	private Cliente cliente = new Cliente();
	private List<Produto> produtos = new ArrayList<>();
	private Categoria categoria = new Categoria();
	private Marca marca = new Marca();
	private User revendedor = new User();
	Produto produto = new Produto();
	Produto produto1 = new Produto();


	@Before
	public void setUp() {
		// given
		categoria.setNome("Água");
		categoriaRepository.save(categoria);

		revendedor.setNome("José da Silva Neto");
		revendedor.setCnpj("016.777.904-50");
		revendedor.setDataCriacao(new Date());
		revendedor.setEmail("josedasilva@gmail.com");
		revendedor.setUsername("josedasilva");
		revendedor.setPassword(new BCryptPasswordEncoder().encode("123456789"));
		userRepository.save(revendedor);

		marca.setNome("Indaía");
		marca.setCategoria(categoria);
		marca.setRevendedor(revendedor);
		marcaRepository.save(marca);

		cliente.setNome("Cliente Teste");
		cliente.setUsername("Teste");
		cliente.setEmail("clienteteste@gmail.com");
		cliente.setCpf("123.539.5667-90");
		clienteRepository.save(cliente);

		produto.setCategoria(categoria);
		produto.setMarca(marca);
		produto.setPeso(20);
		produto.setPreco(2.50);
		produto.setQuantidade(200);
		produto.setUser(revendedor);
		produtoRepository.save(produto);

		produto1.setCategoria(categoria);
		produto1.setMarca(marca);
		produto1.setPeso(20);
		produto1.setPreco(2.50);
		produto1.setQuantidade(200);
		produto1.setUser(revendedor);
		produtoRepository.save(produto1);

		produtos.add(produto1);
		produtos.add(produto);
	}

	@Test
	public void save_deve_salvar_um_pedido_de_um_cliente() {
		// given
		Pedido pedido = new Pedido();
		pedido.setCliente(cliente);
		pedido.setData(new Date());
		pedido.setValor(200);
		pedido.setStatus(Status.ATIVO);
		pedido.setProduto(produto);

		// when
		pedidoRepository.save(pedido);

		// then
		Assertions.assertThat(pedido.getId()).isNotNull();
	}

	@Test
	public void findAllByCliente_deve_retornar_os_pedidos_de_um_cliente() {
		// given
		Cliente cliente1 = new Cliente();
		cliente1.setNome("Cliente Teste1");
		cliente1.setUsername("Teste1");
		cliente1.setEmail("clienteteste1@gmail.com");
		cliente1.setCpf("123.519.5667-90");
		clienteRepository.save(cliente1);
		
		Pedido pedido = new Pedido();
		pedido.setCliente(cliente);
		pedido.setData(new Date());
		pedido.setValor(200);
		pedido.setStatus(Status.ATIVO);
		pedido.setProduto(produto);
		pedidoRepository.save(pedido);

		Pedido pedido1 = new Pedido();
		pedido1.setCliente(cliente);
		pedido1.setData(new Date());
		pedido1.setValor(230);
		pedido1.setStatus(Status.ATIVO);
		pedido1.setProduto(produto);
		pedidoRepository.save(pedido1);
		
		Pedido pedido2 = new Pedido();
		pedido2.setCliente(cliente1);
		pedido2.setData(new Date());
		pedido2.setValor(200);
		pedido2.setStatus(Status.ATIVO);
		pedido2.setProduto(produto1);
		pedidoRepository.save(pedido2);

		//when
		List<Pedido> pedidos = pedidoRepository.findAllByCliente(cliente.getId());
		
		//then
		Assertions.assertThat(pedidos.isEmpty()).isFalse();
		Assertions.assertThat(pedidos.contains(pedido)).isTrue();
		Assertions.assertThat(pedidos.contains(pedido2)).isFalse();
	}
}
