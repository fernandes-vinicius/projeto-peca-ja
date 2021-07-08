package com.pecaja.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.pecaja.app.enums.StatusPedido;
import com.pecaja.app.models.User;
import com.pecaja.app.services.ClienteService;
import com.pecaja.app.services.PedidoService;
import com.pecaja.app.services.UserService;

@Controller
public class HomeController {

	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ClienteService clienteSerivice;
	
	@RequestMapping("/home")
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView("home/home");
		
		User revendedor = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		
		mv.addObject("nClientesDoRevendedor", pedidoService.countPedidosbyRevendedorwhereStatus(revendedor.getId(),StatusPedido.ENTREGUE));
		mv.addObject("nClientesNoApp", clienteSerivice.countClientes());
		mv.addObject("nPedidosAguardando", pedidoService.countPedidosbyRevendedorwhereStatus(revendedor.getId(),StatusPedido.AGUARDANDO));
		mv.addObject("nPedidosCaminho", pedidoService.countPedidosbyRevendedorwhereStatus(revendedor.getId(),StatusPedido.ACAMINHO));
		mv.addObject("nRevendedores", userService.countRevendedoresAtivosAceitos());
		mv.addObject("nSolicitacoes", userService.countSolicitacoes());
		return mv;
	}
}
