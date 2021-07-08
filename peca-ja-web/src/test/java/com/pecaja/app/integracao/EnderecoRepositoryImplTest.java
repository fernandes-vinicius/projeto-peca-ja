package com.pecaja.app.integracao;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pecaja.app.models.Cidade;
import com.pecaja.app.models.Endereco;
import com.pecaja.app.models.Estado;
import com.pecaja.app.repositories.CidadeRepository;
import com.pecaja.app.repositories.EnderecoRepository;
import com.pecaja.app.repositories.EstadoRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EnderecoRepositoryImplTest {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Autowired
	EnderecoRepository enderecoRepository;

	@Autowired
	EstadoRepository estadoRepository;

	@Autowired
	CidadeRepository cidadeRepository;

	private Estado estado = new Estado();
	private Cidade cidade = new Cidade();

	@Before
	public void setUp() {
		estado.setUf("RN");
		estadoRepository.save(estado);

		cidade.setNome("Pau dos Ferros");
		cidadeRepository.save(cidade);
	}

	@Test
	public void save_deve_salvar_um_novo_endereco() {
		// given
		Endereco endereco = new Endereco();
		endereco.setRua("R sitio carrapicho");
		endereco.setBairro("Centro");
		endereco.setNumero(222);
		endereco.setCidade(cidade);
		endereco.setEstado(estado);

		// when
		enderecoRepository.save(endereco);

		// then
		Assertions.assertThat(endereco.getId()).isNotNull();
	}

	@Test
	public void findOne_deve_retornar_um_endereco() {
		// given
		Endereco endereco = new Endereco();
		endereco.setRua("R sitio carrapicho");
		endereco.setBairro("Centro");
		endereco.setNumero(222);
		endereco.setCidade(cidade);
		endereco.setEstado(estado);
		enderecoRepository.save(endereco);

		// when
		Optional<Endereco> endereco1 = enderecoRepository.findById(endereco.getId());

		// then
		Assertions.assertThat(endereco1.get().getId()).isNotNull();
	}

	@Test
	public void findAll_deve_retornar_uma_lista_de_Enderecos() {
		// given
		Endereco endereco = new Endereco();
		endereco.setRua("R sitio carrapicho");
		endereco.setBairro("Centro");
		endereco.setNumero(222);
		endereco.setCidade(cidade);
		endereco.setEstado(estado);
		enderecoRepository.save(endereco);

		Endereco endereco1 = new Endereco();
		endereco1.setRua("R sitio carrapicho");
		endereco1.setBairro("Centro");
		endereco1.setNumero(222);
		endereco1.setCidade(cidade);
		endereco1.setEstado(estado);
		enderecoRepository.save(endereco);

		//when
		List<Endereco> enderecos = enderecoRepository.findAll();
		
		//then
		Assertions.assertThat(enderecos.isEmpty()).isFalse();
	}
	
//	@Test
//	public void delete_deve_remover_um_endereco() {
//		
//	}
}
