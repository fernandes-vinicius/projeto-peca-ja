package com.pecaja.app.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.pecaja.app.models.Categoria;
import com.pecaja.app.models.Marca;
import com.pecaja.app.models.Produto;
import com.pecaja.app.services.CategoriaService;
import com.pecaja.app.services.MarcaService;
import com.pecaja.app.services.ProdutoService;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest
public class ProdutoEndpointControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private MarcaService marcaService;

	@Autowired
	private CategoriaService CategoriaService;
	
	@Autowired
    private WebApplicationContext wac;
 
    @Autowired
    private FilterChainProxy springSecurityFilterChain;
 
	
	@Before
	public void setUp() {
		System.out.println("|*************************************|");
		System.out.println("	Jobs - Initializing Data		");
		System.out.println("|*************************************|");

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
		marca1.setNome("Indaía");
		marca1.setCategoria(categoria1);
		marcaService.save(marca1);

		Marca marca2 = new Marca();
		marca2.setNome("Cristalina");
		marca2.setCategoria(categoria1);
		marcaService.save(marca2);

		Marca marca3 = new Marca();
		marca3.setNome("Liquigás");
		marca3.setCategoria(categoria2);
		marcaService.save(marca3);

		/**
		 * Inicializador - Produtos
		 */
		Produto produto1 = new Produto();
		produto1.setCategoria(categoria1);
		produto1.setMarca(marca1);
		produto1.setPeso(5);
		produto1.setPreco(90.00);
		produto1.setQuantidade(100);
		produtoService.save(produto1);

		Produto produto2 = new Produto();
		produto2.setCategoria(categoria1);
		produto2.setMarca(marca2);
		produto2.setPeso(5);
		produto2.setPreco(90.00);
		produto2.setQuantidade(200);
		produtoService.save(produto2);
			
	    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
	          .addFilter(springSecurityFilterChain).build();
	}
	
	@Test
    public void list_produtos_when_user_not_Autentication_Result_302() throws Exception {
//		mockMvc = MockMvcBuilders
//				.webAppContextSetup(context)
//				.defaultRequest(get("/").with(user("josedasilva").roles("ROLE_REVENDEDOR")))
//				.apply(springSecurity())
//				.build();
		
		this.mockMvc.perform(get("/produtos/list"))
        	.andDo(print()).andExpect(status().is3xxRedirection())
        	.andExpect(redirectedUrl("http://localhost/login"));
    }
	
//	@Test
//    public void list_produtos_when_user_Autentication_Result_200() throws Exception {
//        this.mockMvc.perform(get("/produtos/list"))
//        	.andDo(print()).andExpect(status().is3xxRedirection())
//        	.andExpect(redirectedUrl("http://localhost/login"));
//    }
//	@Test
//	public void listarUsuarios__deve_trazer_200_ok() throws Exception {
//		mockMvc().perform(get("/home"))
//            	 .andDo(print())
//            	 .andExpect(status().isOk());
//	}
//	
//	private MockMvc mockMvc() {
//		return MockMvcBuilders.standaloneSetup(new ProdutoController()).build();
//	}
//
//	@Test
//	public void novoUsuario__deve_trazer_200_ok() throws Exception {
//		mockMvc().perform(get("/usuario/novo"))
//    			 .andExpect(status().isOk())
//            	 .andExpect(view().name("usuario/novo"));
//	}
//
//	@Test
//	public void criarUsuario__deve_trazer_fazer_redirect() throws Exception {
//		mockMvc().perform(post("/usuario").param("nome", "bob"))
//        		 .andDo(print())
//        		 .andExpect(status().isMovedTemporarily())
//        		 .andExpect(redirectedUrl("/usuario"));
//	}
//	
//	@Test
//	public void variavelString__deve_trazer_200_ok() throws Exception {
//		mockMvc().perform(get("/usuario/string"))
//		         .andDo(print())
//		    	 .andExpect(status().isOk())
//		    	 .andExpect(view().name("usuario/string"))
//		    	 .andExpect(model().attributeExists("variavelString"));
//	}
//	
//	@Test
//	public void variavelMap__deve_trazer_200_ok() throws Exception {
//		mockMvc().perform(get("/usuario/map"))
//			     .andDo(print())
//			     .andExpect(status().isOk())
//			     .andExpect(view().name("usuario/map"))
//			     .andExpect(model().attribute("variavelMap", hasKey("um")))
//			     .andExpect(model().attribute("variavelMap", hasKey("dois")));
//	}

}