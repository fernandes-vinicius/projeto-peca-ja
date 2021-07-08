package com.pecaja.app.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.pecaja.app.enums.Status;
import com.pecaja.app.models.Categoria;
import com.pecaja.app.models.Marca;
import com.pecaja.app.models.Produto;
import com.pecaja.app.models.User;
import com.pecaja.app.services.CategoriaService;
import com.pecaja.app.services.MarcaService;
import com.pecaja.app.services.ProdutoService;
import com.pecaja.app.services.UserService;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

	@Autowired
	private UserService userService;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private CategoriaService categoriaService;

	@Autowired
	private MarcaService marcaService;

	@GetMapping("/form")
	public ModelAndView form(Produto produto) {

		ModelAndView mv = new ModelAndView("produtos/form");
		mv.addObject("produto", produto);
		mv.addObject("marca", new Marca());
		mv.addObject("marcas", marcaService.findAll());
		mv.addObject("categorias", categoriaService.findAll());
		return mv;
	}

	@PostMapping("/save")
	public ModelAndView save(@Valid Produto produto, BindingResult result) {

		User revendedor = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

		if (result.hasErrors())
			return form(produto);

		Categoria categoria = categoriaService.findById(produto.getCategoria_id());
		Marca marca = marcaService.findById(produto.getMarca_id());

		// verificar no banco de ja existe este produto no banco
		Produto produtodb = produtoService.findByCategoriaByMarcaByPesoByRevendedor(produto.getCategoria_id(), produto.getMarca_id(), produto.getPeso(), revendedor.getId());
		if(produtodb == null) {
			produto.setCategoria(categoria);
			produto.setMarca(marca);
			produto.setUser(revendedor);
			produto.setStatus(Status.ATIVO);
			produtoService.save(produto);
		}else if(produtodb != null && produtodb.getStatus() == Status.INATIVO ) {
			produtodb.setStatus(Status.ATIVO);
			produto.setPreco(produto.getPreco());
			produtoService.save(produtodb);
		}
		return new ModelAndView("redirect:/produtos/list");
	}

	@GetMapping("/list")
	private ModelAndView list() {
		User revendedor = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		
		ModelAndView mv = new ModelAndView("produtos/list");

		mv.addObject("produtos", produtoService.findAllByRevendedor(revendedor.getId(),Status.ATIVO));

		return mv;
	}

	@GetMapping("/edit/{id}")
	private ModelAndView edit(@PathVariable("id") Long id) {
		Produto produto = produtoService.findById(id);
		return form(produto);
	}

	@GetMapping("/delete/{id}")
	public ModelAndView delete(@PathVariable("id") Long id) {
		Produto produto = produtoService.findById(id);
		produto.setStatus(Status.INATIVO);
		produtoService.save(produto);
		return new ModelAndView("redirect:/produtos/list");
	}

}
