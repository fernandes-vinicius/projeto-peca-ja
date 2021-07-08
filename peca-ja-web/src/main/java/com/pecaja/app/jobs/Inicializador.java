package com.pecaja.app.jobs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.pecaja.app.enums.Situacao;
import com.pecaja.app.enums.Status;
import com.pecaja.app.enums.StatusPedido;
import com.pecaja.app.models.Categoria;
import com.pecaja.app.models.Cidade;
import com.pecaja.app.models.Cliente;
import com.pecaja.app.models.Endereco;
import com.pecaja.app.models.Estado;
import com.pecaja.app.models.Marca;
import com.pecaja.app.models.Pedido;
import com.pecaja.app.models.Produto;
import com.pecaja.app.models.Role;
import com.pecaja.app.models.Sugestao;
import com.pecaja.app.models.User;
import com.pecaja.app.services.CategoriaService;
import com.pecaja.app.services.CidadeService;
import com.pecaja.app.services.ClienteService;
import com.pecaja.app.services.EnderecoService;
import com.pecaja.app.services.EstadoService;
import com.pecaja.app.services.MarcaService;
import com.pecaja.app.services.PedidoService;
import com.pecaja.app.services.ProdutoService;
import com.pecaja.app.services.RoleService;
import com.pecaja.app.services.SugestaoService;
import com.pecaja.app.services.UserService;

@Component
public class Inicializador implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserService userService;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private MarcaService marcaService;

	@Autowired
	private CategoriaService CategoriaService;

	@Autowired
	private SugestaoService sugestaoService;

	@Autowired
	private EnderecoService enderecoService;

	@Autowired
	private EstadoService estadoService;

	@Autowired
	private CidadeService cidadeService;

	@Autowired
	private PedidoService pedidoService;

	@Autowired
	private ClienteService clienteService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		System.out.println("|*************************************|");
		System.out.println("	Jobs - Initializing Data		");
		System.out.println("|*************************************|");

		/**
		 * Inicializador - Estados
		 */
		Estado estado1 = new Estado();
		estado1.setUf("RN");
		estadoService.save(estado1);

		/**
		 * Inicialziador - Cidades
		 */
		Cidade cidade1 = new Cidade();
		cidade1.setNome("Pau dos Ferros");
		cidadeService.save(cidade1);

		/**
		 * Inicializador - Roles
		 */
		Role roleAdmin = new Role();
		roleAdmin.setNome("ROLE_ADM");
		roleService.save(roleAdmin);

		Role roleRevendedor = new Role();
		roleRevendedor.setNome("ROLE_REVENDEDOR");
		roleService.save(roleRevendedor);

		/**
		 * Inicializador - Users
		 */
		List<Role> roles = new ArrayList<>();
		roles.add(roleAdmin);

		List<Role> roles2 = new ArrayList<>();
		roles2.add(roleRevendedor);

		/**
		 * Inicializador - Admin
		 */
		User admin = new User();
		admin.setNome("Administrador");
		admin.setCpf("016.777.901-40");
		admin.setDataCriacao(new Date());
		admin.setEmail("admin@gmail.com");
		admin.setUsername("administrador");
		admin.setPassword(new BCryptPasswordEncoder().encode("123456789"));
		admin.setRoles(roles);
		userService.save(admin);

		/**
		 * Inicializador - Endereços
		 */
		Endereco endereco1 = new Endereco();
		endereco1.setBairro("Centro");
		endereco1.setCidade(cidade1);
		endereco1.setEstado(estado1);
		endereco1.setNumero(691);
		endereco1.setRua("R Da Independencia");
		enderecoService.save(endereco1);

		Endereco endereco2 = new Endereco();
		endereco2.setBairro("Princesinha do Oeste");
		endereco2.setCidade(cidade1);
		endereco2.setEstado(estado1);
		endereco2.setNumero(520);
		endereco2.setRua("R Manoel Alexandre");
		enderecoService.save(endereco2);

		Endereco endereco3 = new Endereco();
		endereco3.setBairro("Alto do Acude");
		endereco3.setCidade(cidade1);
		endereco3.setEstado(estado1);
		endereco3.setNumero(58);
		endereco3.setRua("R Jose Romualdo de paiva");
		enderecoService.save(endereco3);

		Endereco endereco4 = new Endereco();
		endereco4.setBairro("Centro");
		endereco4.setCidade(cidade1);
		endereco4.setEstado(estado1);
		endereco4.setNumero(328);
		endereco4.setRua("R Antônio Franco Oliveira");
		enderecoService.save(endereco4);

		Endereco endereco5 = new Endereco();
		endereco5.setBairro("Centro");
		endereco5.setCidade(cidade1);
		endereco5.setEstado(estado1);
		endereco5.setNumero(140);
		endereco5.setRua("R 13 de Maio");
		enderecoService.save(endereco5);

		Endereco endereco6 = new Endereco();
		endereco6.setBairro("Alto do Açude");
		endereco6.setCidade(cidade1);
		endereco6.setEstado(estado1);
		endereco6.setNumero(300);
		endereco6.setRua("Rua 25 de Outubro");
		enderecoService.save(endereco6);
		
		Endereco endereco7 = new Endereco();
		endereco7.setBairro("Centro");
		endereco7.setCidade(cidade1);
		endereco7.setEstado(estado1);
		endereco7.setNumero(10);
		endereco7.setRua("Avenida 3 da Independência");
		enderecoService.save(endereco7);

		/**
		 * Inicializador - Revendedores
		 */

		User revendedor1 = new User();
		revendedor1.setNome("Posto Segundo Melo LTDA");
		revendedor1.setCnpj("09.117.268/0002-63");
		revendedor1.setDataCriacao(new Date());
		revendedor1.setFantasia("Posto Segundo Melo");
		revendedor1.setEmail("melo2pdf@gmail.com");
		revendedor1.setUsername("SegundoPDF");
		revendedor1.setPassword(new BCryptPasswordEncoder().encode("123456789"));
		revendedor1.setRoles(roles2);
		revendedor1.setEndereco(endereco1);
		revendedor1.setSituacao(Situacao.ACEITO);
		revendedor1.setStatus(Status.ATIVO);
		userService.save(revendedor1);

		User revendedor2 = new User();
		revendedor2.setNome("Alriberto de Souza Maia");
		revendedor2.setCnpj("24.954.292/0001-60");
		revendedor2.setDataCriacao(new Date());
		revendedor2.setEmail("alrbertoSouza@gmail.com");
		revendedor2.setFantasia("Novo Gas Pau Ferrense");
		revendedor2.setUsername("AlribertoSouza");
		revendedor2.setEndereco(endereco2);
		revendedor2.setPassword(new BCryptPasswordEncoder().encode("123456789"));
		revendedor2.setRoles(roles2);
		revendedor2.setSituacao(Situacao.ACEITO);
		revendedor2.setStatus(Status.ATIVO);
		userService.save(revendedor2);

		User revendedor3 = new User();
		revendedor3.setNome("Raimundo Nonato Do Rego");
		revendedor3.setFantasia("N L Distribuidora De Aguas");
		revendedor3.setCnpj("08.716.456/0001-64");
		revendedor3.setDataCriacao(new Date());
		revendedor3.setEmail("raimunndorego@gmail.com");
		revendedor3.setUsername("RaimundoRego");
		revendedor3.setSituacao(Situacao.ACEITO);
		revendedor3.setStatus(Status.ATIVO);
		revendedor3.setEndereco(endereco3);
		revendedor3.setPassword(new BCryptPasswordEncoder().encode("123456789"));
		revendedor3.setRoles(roles2);
		userService.save(revendedor3);

		User revendedor4 = new User();
		revendedor4.setNome("Cristalina do Oeste");
		revendedor4.setFantasia("Cristalina do Oeste PDF-ME");
		revendedor4.setCnpj("03.276.727/0001-94");
		revendedor4.setDataCriacao(new Date());
		revendedor4.setEmail("cristalinaPDF@gmail.com");
		revendedor4.setUsername("cristalinaPDF");
		revendedor4.setSituacao(Situacao.ACEITO);
		revendedor4.setStatus(Status.ATIVO);
		revendedor4.setEndereco(endereco4);
		revendedor4.setPassword(new BCryptPasswordEncoder().encode("123456789"));
		revendedor4.setRoles(roles2);
		userService.save(revendedor4);

		User revendedor5 = new User();
		revendedor5.setNome("Maria Josefa Leite");
		revendedor5.setFantasia("Distribuidora Pauferrense");
		revendedor5.setCnpj("05.215.722/0002-95");
		revendedor5.setDataCriacao(new Date());
		revendedor5.setEmail("distpauferrense@gmail.com");
		revendedor5.setUsername("distpauferrense");
		revendedor5.setSituacao(Situacao.ACEITO);
		revendedor5.setStatus(Status.ATIVO);
		revendedor5.setEndereco(endereco5);
		revendedor5.setPassword(new BCryptPasswordEncoder().encode("123456789"));
		revendedor5.setRoles(roles2);
		userService.save(revendedor5);

		User revendedor6 = new User();
		revendedor6.setNome("Francisco das Chagas");
		revendedor6.setFantasia("Aqua Vida");
		revendedor6.setCnpj("06.115.702/0001-99");
		revendedor6.setDataCriacao(new Date());
		revendedor6.setEmail("aquavida@gmail.com");
		revendedor6.setUsername("aquavida");
		revendedor6.setSituacao(Situacao.ACEITO);
		revendedor6.setStatus(Status.ATIVO);
		revendedor6.setEndereco(endereco6);
		revendedor6.setPassword(new BCryptPasswordEncoder().encode("123456789"));
		revendedor6.setRoles(roles2);
		userService.save(revendedor6);

		User revendedor7 = new User();
		revendedor7.setNome("José da Silva Gomes");
		revendedor7.setFantasia("Extra Gás Central II");
		revendedor7.setCnpj("06.105.712/0001-80");
		revendedor7.setDataCriacao(new Date());
		revendedor7.setEmail("extragas@gmail.com");
		revendedor7.setUsername("extragas");
		revendedor7.setSituacao(Situacao.ACEITO);
		revendedor7.setStatus(Status.ATIVO);
		revendedor7.setEndereco(endereco7);
		revendedor7.setPassword(new BCryptPasswordEncoder().encode("123456789"));
		revendedor7.setRoles(roles2);
		userService.save(revendedor7);
		
		User revendedor8 = new User();
		revendedor8.setNome("Manoel da Rocha");
		revendedor8.setFantasia("Água Cristalina PDF");
		revendedor8.setCnpj("00.106.713/0001-85");
		revendedor8.setDataCriacao(new Date());
		revendedor8.setEmail("aguacristalinapdf@gmail.com");
		revendedor8.setUsername("cristalinapdf");
		revendedor8.setSituacao(Situacao.ACEITO);
		revendedor8.setStatus(Status.ATIVO);
		revendedor8.setEndereco(endereco1);
		revendedor8.setPassword(new BCryptPasswordEncoder().encode("123456789"));
		revendedor8.setRoles(roles2);
		userService.save(revendedor8);

		/**
		 * Inicilizador - Categorias
		 */
		Categoria categoria1 = new Categoria();
		categoria1.setNome("Água");
		CategoriaService.save(categoria1);

		Categoria categoria2 = new Categoria();
		categoria2.setNome("Gás");
		CategoriaService.save(categoria2);

		/**
		 * Inicilizador - Marcas
		 */
		Marca marca1 = new Marca();
		marca1.setNome("Liquigás");
		marca1.setCategoria(categoria2);
		marca1.setRevendedor(revendedor1);
		marcaService.save(marca1);

		Marca marca2 = new Marca();
		marca2.setNome("Novo Gás");
		marca2.setCategoria(categoria2);
		marca2.setRevendedor(revendedor2);
		marcaService.save(marca2);

		Marca marca3 = new Marca();
		marca3.setNome("Indaiá");
		marca3.setRevendedor(revendedor3);
		marca3.setCategoria(categoria1);
		marcaService.save(marca3);

		Marca marca4 = new Marca();
		marca4.setNome("Cristalina do Oeste");
		marca4.setRevendedor(revendedor4);
		marca4.setCategoria(categoria1);
		marcaService.save(marca4);

		Marca marca5 = new Marca();
		marca5.setNome("Indaiá");
		marca5.setRevendedor(revendedor5);
		marca5.setCategoria(categoria1);
		marcaService.save(marca5);

		Marca marca6 = new Marca();
		marca6.setNome("Aqua Vida");
		marca6.setRevendedor(revendedor6);
		marca6.setCategoria(categoria1);
		marcaService.save(marca6);

		Marca marca7 = new Marca();
		marca7.setNome("Liquigás");
		marca7.setRevendedor(revendedor7);
		marca7.setCategoria(categoria2);
		marcaService.save(marca7);
		
		Marca marca8 = new Marca();
		marca8.setNome("Cristalina");
		marca8.setRevendedor(revendedor8);
		marca8.setCategoria(categoria1);
		marcaService.save(marca8);
		
		Marca marca9 = new Marca();
		marca9.setNome("Da fonte");
		marca9.setRevendedor(revendedor8);
		marca9.setCategoria(categoria1);
		marcaService.save(marca9);
		
		Marca marca10 = new Marca();
		marca10.setNome("Do Rio");
		marca10.setRevendedor(revendedor8);
		marca10.setCategoria(categoria1);
		marcaService.save(marca10);

		/**
		 * Inicializador - Produtos
		 */

		Produto produto1 = new Produto();
		produto1.setCategoria(categoria2);
		produto1.setMarca(marca1);
		produto1.setPeso(15);
		produto1.setPreco(60.00);
		produto1.setUser(revendedor1);
		produto1.setQuantidade(100);
		produto1.setStatus(Status.ATIVO);
		produtoService.save(produto1);

		Produto produto2 = new Produto();
		produto2.setCategoria(categoria1);
		produto2.setMarca(marca3);
		produto2.setPeso(20);
		produto2.setPreco(5.00);
		produto2.setUser(revendedor3);
		produto2.setQuantidade(200);
		produto2.setStatus(Status.ATIVO);
		produtoService.save(produto2);

		Produto produto3 = new Produto();
		produto3.setCategoria(categoria1);
		produto3.setMarca(marca3);
		produto3.setPeso(20);
		produto3.setPreco(2.50);
		produto3.setUser(revendedor3);
		produto3.setQuantidade(200);
		produto3.setStatus(Status.ATIVO);
		produtoService.save(produto3);

		Produto produto4 = new Produto();
		produto4.setCategoria(categoria1);
		produto4.setMarca(marca4);
		produto4.setPeso(20);
		produto4.setPreco(3.50);
		produto4.setUser(revendedor4);
		produto4.setQuantidade(200);
		produto4.setStatus(Status.ATIVO);
		produtoService.save(produto4);

		Produto produto5 = new Produto();
		produto5.setCategoria(categoria1);
		produto5.setMarca(marca5);
		produto5.setPeso(20);
		produto5.setPreco(4.00);
		produto5.setUser(revendedor5);
		produto5.setQuantidade(200);
		produto5.setStatus(Status.ATIVO);
		produtoService.save(produto5);

		Produto produto6 = new Produto();
		produto6.setCategoria(categoria1);
		produto6.setMarca(marca6);
		produto6.setPeso(20);
		produto6.setPreco(4.50);
		produto6.setUser(revendedor6);
		produto6.setQuantidade(150);
		produto6.setStatus(Status.ATIVO);
		produtoService.save(produto6);

		Produto produto7 = new Produto();
		produto7.setCategoria(categoria2);
		produto7.setMarca(marca7);
		produto7.setPeso(15);
		produto7.setPreco(65.00);
		produto7.setUser(revendedor7);
		produto7.setQuantidade(50);
		produto7.setStatus(Status.ATIVO);
		produtoService.save(produto7);
		
		Produto produto8 = new Produto();
		produto8.setCategoria(categoria1);
		produto8.setMarca(marca8);
		produto8.setPeso(20);
		produto8.setPreco(3.20);
		produto8.setUser(revendedor8);
		produto8.setQuantidade(25);
		produto8.setStatus(Status.ATIVO);
		produtoService.save(produto8);
		
		Produto produto9 = new Produto();
		produto9.setCategoria(categoria1);
		produto9.setMarca(marca9);
		produto9.setPeso(20);
		produto9.setPreco(3.99);
		produto9.setUser(revendedor8);
		produto9.setQuantidade(35);
		produto9.setStatus(Status.ATIVO);
		produtoService.save(produto9);
		
		Produto produto10 = new Produto();
		produto10.setCategoria(categoria1);
		produto10.setMarca(marca10);
		produto10.setPeso(20);
		produto10.setPreco(2.80);
		produto10.setUser(revendedor8);
		produto10.setQuantidade(15);
		produto10.setStatus(Status.ATIVO);
		produtoService.save(produto10);

		/**
		 * Inicializador - Sugestão
		 */
		Sugestao sugestao1 = new Sugestao();
		sugestao1.setSugestao("Sitezinho fuleragi");
		sugestao1.setUser(revendedor1);
		sugestao1.setData(new Date());
		sugestaoService.save(sugestao1);

		/**
		 * Inicialziador - Pedidos
		 */
		Pedido pedido1 = new Pedido();
		pedido1.setRevendedor(revendedor1);
		pedido1.setData(new Date());
		pedido1.setProduto(produto1);
		pedido1.setStatusPedido(StatusPedido.AGUARDANDO);
		pedido1.setValor(100.00);
		pedido1.setQuantidade(5);
		pedido1.setEstimativa_entrega(9);
		pedido1.setStatus(Status.ATIVO);

		Pedido pedido2 = new Pedido();
		pedido2.setData(new Date());
		pedido2.setRevendedor(revendedor2);
		pedido2.setProduto(produto2);
		pedido2.setStatusPedido(StatusPedido.AGUARDANDO);
		pedido2.setValor(100.00);
		pedido2.setQuantidade(3);
		pedido2.setEstimativa_entrega(25);
		pedido2.setStatus(Status.ATIVO);
		
		Pedido pedido3 = new Pedido();
		pedido3.setData(new Date());
		pedido3.setRevendedor(revendedor2);
		pedido3.setProduto(produto2);
		pedido3.setStatusPedido(StatusPedido.ENTREGUE);
		pedido3.setValor(100.00);
		pedido3.setEstimativa_entrega(14);
		pedido3.setStatus(Status.ATIVO);

		List<Pedido> pedidos = new ArrayList<>();
		pedidos.add(pedido1);
		pedidos.add(pedido2);

		/**
		 * Inicializador - Clientes
		 */
		Cliente cliente1 = new Cliente();
		cliente1.setCpf("016.777.904-40");
		cliente1.setTelefone("84999954300");
		cliente1.setEmail("viniciusfernandes6991@gmail.com");
		cliente1.setEndereco(endereco1);
		cliente1.setNome("Vinicius Fernandes");
		cliente1.setPassword(new BCryptPasswordEncoder().encode("123456789"));
		cliente1.setUsername("VinyFernandes");
		cliente1.setStatus(Status.ATIVO);
		clienteService.save(cliente1);

		Cliente cliente2 = new Cliente();
		cliente2.setCpf("016.741.894-78");
		cliente2.setTelefone("84999934601");
		cliente2.setEmail("vianagerson2011@gmail.com");
		cliente2.setEndereco(endereco2);
		cliente2.setNome("Gerson Viana");
		cliente2.setPassword(new BCryptPasswordEncoder().encode("123456789"));
		cliente2.setUsername("vianagerson");
		cliente2.setStatus(Status.ATIVO);
		clienteService.save(cliente2);
		
		Cliente cliente3 = new Cliente();
		cliente3.setCpf("101.019.604-92");
		cliente3.setTelefone("84989434501");
		cliente3.setEmail("claudiorodrigo@gmail.com");
		cliente3.setEndereco(endereco3);
		cliente3.setNome("Claudio Rodrigo");
		cliente3.setPassword(new BCryptPasswordEncoder().encode("123456789"));
		cliente3.setUsername("claudiorodrigo");
		cliente3.setStatus(Status.ATIVO);
		clienteService.save(cliente3);

		pedido1.setCliente(cliente1);
		pedido2.setCliente(cliente1);
		pedido3.setCliente(cliente1);

		pedidoService.save(pedido1);
		pedidoService.save(pedido2);
		pedidoService.save(pedido3);

	}

}
