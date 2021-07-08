package com.pecaja.app.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pecaja.app.enums.Situacao;
import com.pecaja.app.enums.Status;
import com.pecaja.app.enums.StatusPedido;
import com.pecaja.app.models.Pedido;
import com.pecaja.app.models.Role;
import com.pecaja.app.models.User;
import com.pecaja.app.services.ClienteService;
import com.pecaja.app.services.PedidoService;
import com.pecaja.app.services.UserService;

@Controller
@RequestMapping("/revendedores")
public class RevendedorController {

	@Autowired
	UserService userService;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private PedidoService pedidoService;

	@GetMapping("/list")
	public ModelAndView list() {
		ModelAndView mv = new ModelAndView("revendedores/list");
		mv.addObject("users", userService.findAllBySituacaoAndStatus(Situacao.ACEITO, Status.ATIVO));
		return mv;
	}

	@GetMapping("/ajaxbydetalhes/{id}")
	public ModelAndView ajaxbydetalhes(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView("revendedores/ajaxbydetalhes");
		mv.addObject("u", userService.findById(id));
		return mv;
	}
	
	@GetMapping("delete/{id}")
	public String delete(@PathVariable Long id, RedirectAttributes ra) {

		User user = userService.findById(id);
		user.setStatus(Status.INATIVO);

		userService.save(user);

		ra.addFlashAttribute("mensagem", "Revendedor removido com sucesso");

		return "redirect:/revendedores/list";
	}

	@GetMapping("/resultados/mes")
	@ResponseBody
	public double[] resultadosMes() {
		User revendedor = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

		Date data = new Date();
		GregorianCalendar dataCal = new GregorianCalendar();
		dataCal.setTime(data);
		int mes = (dataCal.get(Calendar.MONTH) + 1);
		int ano = (dataCal.get(Calendar.YEAR));

		List<Pedido> pedidosdb = new ArrayList<>();

		List<Role> roles = revendedor.getRoles();

		boolean isAdm = false;
		
		for(Role r : roles) {
			if(r.getNome().equals("ROLE_ADM")) {
				isAdm = true;
				break;
			}
		}
		
		if (isAdm) {
			pedidosdb = pedidoService.findAllByStatusPedido(StatusPedido.ENTREGUE);
		} else {
			pedidosdb = pedidoService.findAllByRevendedorAndStatusPedido(revendedor.getId(), StatusPedido.ENTREGUE);
		}
		
		GregorianCalendar dataPedido = new GregorianCalendar();
		int mesPedido;
		int anoPedido;

		int pedidosGas = 0;
		int pedidosAgua = 0;
		double apuradoDoMes = 0;

		if (!pedidosdb.isEmpty()) {
			for (Pedido pedido : pedidosdb) {
				dataPedido.setTime(pedido.getData());
				mesPedido = (dataPedido.get(Calendar.MONTH) + 1);
				anoPedido = (dataPedido.get(Calendar.YEAR));

				if (mesPedido == mes && anoPedido == ano) {
					if (pedido.getProduto().getCategoria().getNome().equals("Água")) {
						pedidosAgua += pedido.getQuantidade();
					} else if (pedido.getProduto().getCategoria().getNome().equals("Gás")) {
						pedidosGas += pedido.getQuantidade();
					}
					apuradoDoMes += pedido.getValor();
				}
			}
		}

		double[] array = new double[4];
		array[0] = pedidosGas;
		array[1] = pedidosAgua;
		array[2] = apuradoDoMes;

		return array;
	}
}
