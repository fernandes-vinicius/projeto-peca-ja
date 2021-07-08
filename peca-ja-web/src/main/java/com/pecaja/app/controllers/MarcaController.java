package com.pecaja.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pecaja.app.models.Categoria;
import com.pecaja.app.models.Marca;
import com.pecaja.app.models.User;
import com.pecaja.app.services.CategoriaService;
import com.pecaja.app.services.MarcaService;
import com.pecaja.app.services.UserService;

@Controller
@RequestMapping("/marcas")
public class MarcaController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private MarcaService marcaService;

	@Autowired
	private CategoriaService categoriaService;

	@PostMapping("/save")
	@ResponseBody
	private String save(@RequestBody Marca marca) {

		User revendedor = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

		if (marca.getCategoria_id().equals("-1"))
			return "401";
		else {

			Marca marcadb = marcaService.findMarcaByNome(marca.getNome(),revendedor.getId());
			if (marcadb != null) {

				if (marca.getNome().equals(marcadb.getNome()))
					return "402";

			} else {
				System.out.println(marca.getNome() + " " + marca.getCategoria_id());
				Categoria categoria = categoriaService.findById(new Long(marca.getCategoria_id()));
				marca.setRevendedor(revendedor);
				marca.setCategoria(categoria);
				marcaService.save(marca);
				return "200";
			}
		}

		return "400";
	}

	@GetMapping("/ajaxbymarca/{id}")
	public ModelAndView getMarcas(@PathVariable("id") Long id) {

		User revendedor = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

		ModelAndView mv = new ModelAndView("marcas/ajaxbymarca");
		mv.addObject("marcas", marcaService.findAllByCategoria(id, revendedor.getId()));
		return mv;
	}
}
