package com.pecaja.app.integracao;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pecaja.app.enums.Situacao;
import com.pecaja.app.enums.Status;
import com.pecaja.app.models.Cidade;
import com.pecaja.app.models.Cliente;
import com.pecaja.app.models.Endereco;
import com.pecaja.app.models.Estado;
import com.pecaja.app.models.User;
import com.pecaja.app.repositories.CidadeRepository;
import com.pecaja.app.repositories.ClienteRepository;
import com.pecaja.app.repositories.EnderecoRepository;
import com.pecaja.app.repositories.EstadoRepository;
import com.pecaja.app.repositories.UserRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryImplTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Autowired
	UserRepository userRepository;

	@Autowired
	EnderecoRepository enderecoRepository;

	@Autowired
	EstadoRepository estadoRepository;

	@Autowired
	CidadeRepository cidadeRepository;

	private Estado estado = new Estado();
	private Cidade cidade = new Cidade();
	private Endereco endereco = new Endereco();

	@Before
	public void setUp() {
		estado.setUf("RN");
		estadoRepository.save(estado);

		cidade.setNome("Pau dos Ferros");
		cidadeRepository.save(cidade);

		endereco.setRua("R sitio carrapicho");
		endereco.setBairro("Centro");
		endereco.setNumero(222);
		endereco.setCidade(cidade);
		endereco.setEstado(estado);
		enderecoRepository.save(endereco);
	}

	@Test
	public void save_deve_salvar_um_novo_cliente() {
		// given
		User user = new User();
		user.setNome("Cliente Teste");
		user.setUsername("Teste");
		user.setEmail("clienteteste@gmail.com");
		user.setCpf("123.539.5667-90");
		user.setEndereco(endereco);

		// when
		userRepository.save(user);

		// then
		Assertions.assertThat(user.getId()).isNotNull();
		Assertions.assertThat(user.getEndereco()).isNotNull();
	}

	@Test
	public void save_deve_atualizar_um_cliente_pre_cadastrado() {
		// given
		User user = new User();
		user.setNome("Cliente Teste");
		user.setUsername("Teste");
		user.setEmail("clienteteste@gmail.com");
		user.setCpf("123.539.5667-90");
		user.setEndereco(endereco);
		userRepository.save(user);

		// when
		user.setNome("Teste Update");
		userRepository.save(user);

		// then
		Assertions.assertThat(user.getNome()).isEqualTo(user.getNome());
	}

	@Test
	public void save_deve_fazer_exclusao_logica_de_um_user_pre_cadastrado() {
		// given
		User user = new User();
		user.setNome("Cliente Teste");
		user.setUsername("Teste");
		user.setEmail("clienteteste@gmail.com");
		user.setCpf("123.539.5667-90");
		user.setEndereco(endereco);
		user.setStatus(Status.ATIVO);
		userRepository.save(user);
		
		//when
		user.setStatus(Status.INATIVO);
		userRepository.save(user);
		
		//then
		Assertions.assertThat(user.getStatus()).isEqualTo(Status.INATIVO);
	}

	@Test
	public void findByUsername_deve_buscar_um_usuario_pelo_username() {
		// given
		User user = new User();
		user.setNome("Cliente Teste");
		user.setUsername("Teste");
		user.setEmail("clienteteste@gmail.com");
		user.setCpf("123.539.5667-90");
		user.setEndereco(endereco);
		userRepository.save(user);

		// when
		User userdb = userRepository.findByUsername(user.getUsername());

		// then
		Assertions.assertThat(user.getId()).isEqualTo(userdb.getId());
		Assertions.assertThat(user.getUsername()).isEqualTo(userdb.getUsername());
	}

	@Test
	public void findBycnpj_deve_buscar_um_cliente_pelo_cnpj() {
		// given
		User user = new User();
		user.setNome("Cliente Teste");
		user.setUsername("Teste");
		user.setEmail("clienteteste@gmail.com");
		user.setCpf("123.539.5667-90");
		user.setPassword("123456789");
		user.setEndereco(endereco);
		userRepository.save(user);

		// when
		User userdb = userRepository.findBycnpj(user.getCnpj());

		// the
		Assertions.assertThat(userdb.getId()).isNotNull();
		Assertions.assertThat(userdb.getCnpj()).isEqualTo(user.getCnpj());
	}

	@Test
	public void findAllBySituacaoAndStatus_deve_retornar_todos_os_usuarios_ATIVOS_e_aceitos() {
		// given
		User user1 = new User();
		user1.setNome("Cliente Teste");
		user1.setUsername("Teste");
		user1.setEmail("clienteteste@gmail.com");
		user1.setCpf("123.539.517-90");
		user1.setPassword("113456789");
		user1.setEndereco(endereco);
		user1.setSituacao(Situacao.ACEITO);
		user1.setStatus(Status.ATIVO);
		userRepository.save(user1);

		User user2 = new User();
		user2.setNome("Cliente Teste1");
		user2.setUsername("Teste1");
		user2.setEmail("clienteteste1@gmail.com");
		user2.setCpf("123.532.661-80");
		user2.setPassword("123456789");
		user2.setEndereco(endereco);
		user2.setSituacao(Situacao.ACEITO);
		user2.setStatus(Status.ATIVO);
		userRepository.save(user2);

		User user3 = new User();
		user3.setNome("Cliente Teste2");
		user3.setUsername("Teste2");
		user3.setEmail("clienteteste2@gmail.com");
		user3.setCpf("123.559.567-10");
		user3.setPassword("133456789");
		user3.setEndereco(endereco);
		user3.setSituacao(Situacao.AGUARDANDO);
		user3.setStatus(Status.ATIVO);
		userRepository.save(user3);

		User user4 = new User();
		user4.setNome("Cliente Teste3");
		user4.setUsername("Teste3");
		user4.setEmail("clienteteste3@gmail.com");
		user4.setCpf("123.559.667-10");
		user4.setPassword("143456789");
		user4.setEndereco(endereco);
		user4.setSituacao(Situacao.ACEITO);
		user4.setStatus(Status.INATIVO);
		userRepository.save(user4);

		// when
		List<User> users = userRepository.findAllBySituacaoAndStatus(Situacao.ACEITO, Status.ATIVO);

		// then
		Assertions.assertThat(users.isEmpty()).isFalse();
		Assertions.assertThat(users.contains(user1)).isTrue();
		Assertions.assertThat(users.contains(user2)).isTrue();
		Assertions.assertThat(users.contains(user3)).isFalse();
		Assertions.assertThat(users.contains(user4)).isFalse();

	}

	@Test
	public void findAllBySituacaoAndEmail_deve_retornar_um_user_ACEITO_pelo_email() {
		// given
		User user1 = new User();
		user1.setNome("Cliente Teste");
		user1.setUsername("Teste");
		user1.setEmail("clienteteste@gmail.com");
		user1.setCpf("123.539.5667-90");
		user1.setPassword("123456789");
		user1.setEndereco(endereco);
		user1.setSituacao(Situacao.ACEITO);
		user1.setStatus(Status.ATIVO);
		userRepository.save(user1);

		// when
		List<User> userdb = userRepository.findAllBySituacaoAndEmail(Situacao.ACEITO, user1.getEmail());

		// then
		Assertions.assertThat(userdb.size()).isEqualTo(1);
		Assertions.assertThat(userdb.get(0).getNome()).isEqualTo(user1.getNome());
	}
}
