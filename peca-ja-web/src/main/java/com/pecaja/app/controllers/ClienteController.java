package com.pecaja.app.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pecaja.app.enums.Status;
import com.pecaja.app.models.Cliente;
import com.pecaja.app.services.ClienteService;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@GetMapping("/list")
	public ModelAndView list() {
		List<Cliente> clientes = new ArrayList<>();
		clientes = clienteService.findByStatus(Status.ATIVO);
		
		ModelAndView mv = new ModelAndView("/clientes/list");
		mv.addObject(clientes);
		return mv;
	}
	
	@GetMapping("/ajax/countClientes")
	@ResponseBody
	public int ajaxCountRevendedoresByAguardando() {
		return clienteService.countClientes();
	}
}
