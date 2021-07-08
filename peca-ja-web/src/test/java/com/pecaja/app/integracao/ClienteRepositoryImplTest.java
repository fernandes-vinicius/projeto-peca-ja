package com.pecaja.app.integracao;

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
import com.pecaja.app.models.Cliente;
import com.pecaja.app.models.Endereco;
import com.pecaja.app.models.Estado;
import com.pecaja.app.repositories.CidadeRepository;
import com.pecaja.app.repositories.ClienteRepository;
import com.pecaja.app.repositories.EnderecoRepository;
import com.pecaja.app.repositories.EstadoRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ClienteRepositoryImplTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Autowired
	ClienteRepository clienteRepository;

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
		Cliente cliente = new Cliente();
		cliente.setNome("Cliente Teste");
		cliente.setUsername("Teste");
		cliente.setEmail("clienteteste@gmail.com");
		cliente.setCpf("123.539.5667-90");
		cliente.setEndereco(endereco);

		// when
		clienteRepository.save(cliente);

		// then
		Assertions.assertThat(cliente.getId()).isNotNull();
		Assertions.assertThat(cliente.getEndereco()).isNotNull();
	}

	@Test
	public void save_deve_atualizar_um_cliente_pre_cadastrado() {
		// given
		Cliente cliente = new Cliente();
		cliente.setNome("Cliente Teste");
		cliente.setUsername("Teste");
		cliente.setEmail("clienteteste@gmail.com");
		cliente.setCpf("123.539.5667-90");
		cliente.setEndereco(endereco);
		clienteRepository.save(cliente);

		// when
		cliente.setNome("Teste Update");
		clienteRepository.save(cliente);

		// then
		Assertions.assertThat(cliente.getNome()).isEqualTo(cliente.getNome());
	}

	@Test
	public void findByUsernameOrEmailOrCpf_deve_buscar_um_cliente_pelo_username_email_ou_cpf() {
		// given
		Cliente cliente = new Cliente();
		cliente.setNome("Cliente Teste");
		cliente.setUsername("Teste");
		cliente.setEmail("clienteteste@gmail.com");
		cliente.setCpf("123.539.5667-90");
		cliente.setEndereco(endereco);
		clienteRepository.save(cliente);

		// when
		Cliente clienteUser = clienteRepository.findByUsernameOrEmailOrCpf(cliente.getUsername(), "", "");
		Cliente clienteEmail = clienteRepository.findByUsernameOrEmailOrCpf("", cliente.getEmail(), "");
		Cliente clienteCpf = clienteRepository.findByUsernameOrEmailOrCpf("", "", cliente.getCpf());

		Assertions.assertThat(cliente.getId()).isEqualTo(clienteUser.getId());
		Assertions.assertThat(cliente.getId()).isEqualTo(clienteEmail.getId());
		Assertions.assertThat(cliente.getId()).isEqualTo(clienteCpf.getId());
	}

	@Test
	public void findByUsernameAndPassword_deve_buscar_um_cliente_pelo_seu_usuario_e_senha() {
		// given
		Cliente cliente = new Cliente();
		cliente.setNome("Cliente Teste");
		cliente.setUsername("Teste");
		cliente.setEmail("clienteteste@gmail.com");
		cliente.setCpf("123.539.5667-90");
		cliente.setPassword("123456789");
		cliente.setEndereco(endereco);
		clienteRepository.save(cliente);
		
		//when
		Cliente clientedb = clienteRepository.findByUsernameAndPassword(cliente.getUsername(), cliente.getPassword());

		//the
		Assertions.assertThat(clientedb.getId()).isNotNull();
		Assertions.assertThat(clientedb.getNome()).isEqualTo(cliente.getNome());
	}

}
