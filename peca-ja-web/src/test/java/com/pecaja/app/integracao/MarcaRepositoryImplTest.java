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
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.pecaja.app.models.Categoria;
import com.pecaja.app.models.Marca;
import com.pecaja.app.models.User;
import com.pecaja.app.repositories.CategoriaRepository;
import com.pecaja.app.repositories.MarcaRepository;
import com.pecaja.app.repositories.UserRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MarcaRepositoryImplTest {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private MarcaRepository marcaRepository;
	@Autowired
	private UserRepository userRepository;

	private Categoria categoria = new Categoria();
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
	}

	@Test
	public void save_deve_salvar_uma_nova_marca() {
		//given
		Marca marca = new Marca();
		marca.setNome("Indaía");
		marca.setCategoria(categoria);
		marca.setRevendedor(revendedor);
		
		//when
		marcaRepository.save(marca);
	
		//then
		Assertions.assertThat(marca.getId()).isNotNull();
		Assertions.assertThat(marca.getCategoria()).isNotNull();
	}
	
	@Test
	public void save_editar_uma_marca_pre_existente() {
		//given
		Marca marca = new Marca();
		marca.setNome("Indaía");
		marca.setCategoria(categoria);
		marca.setRevendedor(revendedor);
		marcaRepository.save(marca);
		
		//when
		marca.setNome("Do Céu");
		marcaRepository.save(marca);
	}

	@Test
	public void findMarcaByNome_deve_buscar_uma_marca_pelo_nome() {
		//given
		Marca marca = new Marca();
		marca.setNome("Indaía");
		marca.setCategoria(categoria);
		marca.setRevendedor(revendedor);
		marcaRepository.save(marca);
		
		//when
		Marca marcadb = marcaRepository.findMarcaByNome(marca.getNome(), revendedor.getId());
		
		//then
		Assertions.assertThat(marcadb.getId()).isEqualTo(marcadb.getId());
	}
	
	@Test
	public void findAllByCategoria_deve_buscar_uma_lista_de_marcas_pela_categoria_e_pelo_revendedor() {
		//given
		User revendedor1 = new User();
		revendedor1.setNome("José Viana Neto");
		revendedor1.setCnpj("216.227.934-50");
		revendedor1.setDataCriacao(new Date());
		revendedor1.setEmail("joseviana@gmail.com");
		revendedor1.setUsername("joseviana");
		revendedor1.setPassword(new BCryptPasswordEncoder().encode("123456789"));
		userRepository.save(revendedor1);
		
		Categoria categoria1 = new Categoria();
		categoria1.setNome("Gás");
		categoriaRepository.save(categoria1);
		
		Marca marca = new Marca();
		marca.setNome("Indaía");
		marca.setCategoria(categoria);
		marca.setRevendedor(revendedor);
		marcaRepository.save(marca);
		
		Marca marca1 = new Marca();
		marca1.setNome("Do Céu");
		marca1.setCategoria(categoria);
		marca1.setRevendedor(revendedor);
		marcaRepository.save(marca1);
		
		Marca marca2 = new Marca();
		marca2.setNome("NovoGás");
		marca2.setCategoria(categoria1);
		marca2.setRevendedor(revendedor1);
		marcaRepository.save(marca2);
		
		//when
		List<Marca> marcas = marcaRepository.findAllByCategoria(categoria.getId(), revendedor.getId());
	
		//then
		Assertions.assertThat(marcas.isEmpty()).isFalse();
		Assertions.assertThat(marcas.contains(marca)).isTrue();
		Assertions.assertThat(marcas.contains(marca2)).isFalse();
		
	}

}
