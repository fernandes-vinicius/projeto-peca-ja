  package com.pecaja.app.integracao;

import java.util.Date;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.pecaja.app.enums.Status;
import com.pecaja.app.models.Categoria;
import com.pecaja.app.models.Marca;
import com.pecaja.app.models.Produto;
import com.pecaja.app.models.User;
import com.pecaja.app.repositories.CategoriaRepository;
import com.pecaja.app.repositories.MarcaRepository;
import com.pecaja.app.repositories.ProdutoRepository;
import com.pecaja.app.repositories.UserRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
// para usar o proprio banco
// @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProdutoReposytoryImplTest {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private MarcaRepository marcaRepository;
	@Autowired
	private UserRepository userRepository;

	private Categoria categoria = new Categoria();
	private Marca marca = new Marca();
	private User revendedor = new User();
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setUp() {
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
	}
	
	@Test
	public void save_deve_salvar_um_novo_produto() {
		// given
		Produto produto = new Produto();
		produto.setCategoria(categoria);
		produto.setMarca(marca);
		produto.setPeso(20);
		produto.setPreco(2.50);
		produto.setQuantidade(200);
		produto.setUser(revendedor);

		// when
		this.produtoRepository.save(produto);

		// then
		Assertions.assertThat(produto.getId()).isNotNull();
		// Assertions.assertThat(prs.getId()).isNull();;
		Assertions.assertThat(produto.getCategoria().getId()).isNotNull();
		Assertions.assertThat(produto.getMarca().getId()).isNotNull();
		Assertions.assertThat(produto.getUser().getId()).isNotNull();
		Assertions.assertThat(produto.getCategoria().getNome()).isEqualTo(categoria.getNome());
	}

	@Test
	public void save_deve_atualizar_um_produto_pre_existente() {
		// given
		long produto_antes_de_atualizar;
		long produto_depois_de_atualizar;

		Produto produto = new Produto();
		produto.setId(30L);
		produto.setPeso(20);
		produto.setPreco(2.50);
		produto.setCategoria(categoria);
		produto.setMarca(marca);
		produto.setUser(revendedor);
		produto.setQuantidade(200);
		this.produtoRepository.save(produto);
		produto_antes_de_atualizar = produto.getId();

		produto.setPeso(30);
		produto.setPreco(3.50);
		produto.setQuantidade(100);

		// when
		this.produtoRepository.save(produto);
		produto_depois_de_atualizar = produto.getId();

		// then
		Assertions.assertThat(produto_antes_de_atualizar).isEqualTo(produto_depois_de_atualizar);
		Assertions.assertThat(produto.getPeso()).isEqualTo(30);
		Assertions.assertThat(produto.getPreco()).isEqualTo(3.50);
		Assertions.assertThat(produto.getQuantidade()).isEqualTo(100);
	}

	@Test
	public void save_deve_fazer_exclusao_logica_de_um_produto_pre_existente() {
		// given
		Produto produto = new Produto();
		produto.setId(30L);
		produto.setPeso(20);
		produto.setPreco(2.50);
		produto.setQuantidade(200);
		produto.setCategoria(categoria);
		produto.setMarca(marca);
		produto.setUser(revendedor);
		produto.setStatus(Status.ATIVO);
		this.produtoRepository.save(produto);

		// when
		produto.setStatus(Status.INATIVO);
		this.produtoRepository.save(produto);

		// then
		Assertions.assertThat(produto.getStatus()).isEqualTo(Status.INATIVO);
	}

	@Test
	public void findById_deve_encontrar_um_produto_pelo_id() {
		// given
		Produto produto = new Produto();
		produto.setId(30L);
		produto.setPeso(20);
		produto.setPreco(2.50);
		produto.setQuantidade(200);
		produto.setCategoria(categoria);
		produto.setMarca(marca);
		produto.setUser(revendedor);
		produto.setStatus(Status.ATIVO);
		this.produtoRepository.save(produto);

		// when
		Produto produtodb = produtoRepository.getOne(produto.getId());

		// then
		Assertions.assertThat(produtodb.getId()).isEqualTo(produto.getId());
	}

	@Test
	public void findByCategoriaByMarcaByPesoByRevendedor_deve_retornar_se_o_produto_ja_existente() {
		// given
		Produto produto = new Produto();
		produto.setCategoria(categoria);
		produto.setMarca(marca);
		produto.setPeso(20);
		produto.setPreco(2.50);
		produto.setQuantidade(200);
		produto.setUser(revendedor);
		this.produtoRepository.save(produto);

		// when
		Produto produto2 = new Produto();
		produto2.setCategoria(categoria);
		produto2.setMarca(marca);
		produto2.setPeso(20);
		produto2.setPreco(2.50);
		produto2.setQuantidade(200);
		produto2.setUser(revendedor);

		Produto produtodb = produtoRepository.findByCategoriaByMarcaByPesoByRevendedor(categoria.getId(), marca.getId(),
				produto2.getPeso(), revendedor.getId());

		// then
		Assertions.assertThat(produtodb).isNotNull();
	}

	@Test
	public void findByCategoriaByMarcaByPesoByRevendedor_deve_retornar_se_o_produto_nao_existente() {
		// given
		Produto produto2 = new Produto();
		produto2.setCategoria(categoria);
		produto2.setMarca(marca);
		produto2.setPeso(20);
		produto2.setPreco(2.50);
		produto2.setQuantidade(200);
		produto2.setUser(revendedor);
		
		// when
		Produto produtodb = produtoRepository.findByCategoriaByMarcaByPesoByRevendedor(categoria.getId(), marca.getId(),
				produto2.getPeso(), revendedor.getId());

		// then
		Assertions.assertThat(produtodb).isNull();
	}

	public void findAllByRevendedorAndStatus_deve_retornar_apenas_os_produtos_ATIVOS_de_um_revendedor() {
		// given
		User revendedor2 = new User();
		revendedor2.setNome("José viana teste");
		revendedor2.setCnpj("050.409.656-50");
		revendedor2.setDataCriacao(new Date());
		revendedor2.setEmail("joseviana@gmail.com");
		revendedor2.setUsername("joseviana");
		revendedor2.setPassword(new BCryptPasswordEncoder().encode("123456789"));
		userRepository.save(revendedor2);

		Produto produto = new Produto();
		produto.setCategoria(categoria);
		produto.setMarca(marca);
		produto.setPeso(20);
		produto.setPreco(2.50);
		produto.setQuantidade(200);
		produto.setUser(revendedor);
		this.produtoRepository.save(produto);

		Produto produto2 = new Produto();
		produto2.setCategoria(categoria);
		produto2.setMarca(marca);
		produto2.setPeso(30);
		produto2.setPreco(2.80);
		produto2.setQuantidade(272);
		produto2.setUser(revendedor);
		this.produtoRepository.save(produto2);

		Produto produto3 = new Produto();
		produto3.setCategoria(categoria);
		produto3.setMarca(marca);
		produto3.setPeso(20);
		produto3.setPreco(2.50);
		produto3.setQuantidade(200);
		produto3.setUser(revendedor2);
		this.produtoRepository.save(produto3);

		Produto produto4 = new Produto();
		produto4.setCategoria(categoria);
		produto4.setMarca(marca);
		produto4.setPeso(30);
		produto4.setPreco(2.80);
		produto4.setQuantidade(272);
		produto4.setUser(revendedor2);
		this.produtoRepository.save(produto4);

		// when
		List<Produto> produtos = produtoRepository.findAllByRevendedor(revendedor.getId(),Status.ATIVO);

		Assertions.assertThat(produtos.contains(produto3)).isFalse();
		Assertions.assertThat(produtos.contains(produto4)).isFalse();
		Assertions.assertThat(produtos.contains(produto2)).isTrue();
		Assertions.assertThat(produtos.contains(produto)).isTrue();

	}
}
