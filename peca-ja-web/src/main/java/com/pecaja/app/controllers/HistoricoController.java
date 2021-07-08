package com.pecaja.app.controllers;

import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pecaja.app.models.User;
import com.pecaja.app.enums.Status;
import com.pecaja.app.enums.StatusPedido;
import com.pecaja.app.models.Cliente;
import com.pecaja.app.models.Historico;
import com.pecaja.app.models.Pedido;
import com.pecaja.app.models.Produto;
import com.pecaja.app.services.ClienteService;
import com.pecaja.app.services.HistoricoService;
import com.pecaja.app.services.PedidoService;
import com.pecaja.app.services.ProdutoService;
import com.pecaja.app.services.UserService;

@Controller
@RequestMapping("/revendedor")
public class HistoricoController {

	@Autowired
	private UserService userService;

	@Autowired
	private PedidoService pedidoService;

	@Autowired
	private ProdutoService produtoService;

	@GetMapping("/historico")
	public ModelAndView historico() {

		// Pegar o mes e ano atual
		Date date = new Date();
		GregorianCalendar dataCal = new GregorianCalendar();
		GregorianCalendar dataPedido = new GregorianCalendar();
		dataCal.setTime(date);
		int mesAtual = (dataCal.get(Calendar.MONTH) + 1);
		int anoAtual = (dataCal.get(Calendar.YEAR));
		int mesPedido;
		int anoPedido;

		// Revendedor Logado
		User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

		List<Pedido> pedidosBanco = pedidoService.findAllByRevendedorAndStatusPedido(user.getId(),
				StatusPedido.ENTREGUE);
		List<Pedido> pedidos = new ArrayList<>();
		int quantidadeVenda = pedidoService.countPedidosbyRevendedorwhereStatus(user.getId(), StatusPedido.ENTREGUE);
		int estoqueProduto = produtoService.sumQuantidadeProdutosbyRevendedorwhereStatus(user.getId(),Status.ATIVO);
		double valorTotalVenda = 0;

		for (Pedido p : pedidosBanco) {
			if (p != null) {
				// Pega os valores da data do pedido, e passa para o objeto GregoriaCalendar
				dataPedido.setTime(p.getData());
				mesPedido = (dataPedido.get(Calendar.MONTH) + 1);
				anoPedido = (dataPedido.get(Calendar.YEAR));

				if (mesAtual == mesPedido && anoAtual == anoPedido)  {
					if (p.getRevendedor().getId() == user.getId()) {
						pedidos.add(p);
						valorTotalVenda += (p.getProduto().getPreco() * p.getQuantidade());
					}
				}
			}
		}

		String mesesTexto = mesEano(mesAtual,anoAtual);

		ModelAndView mv = new ModelAndView("historico/historico");
		mv.addObject("pedidos", pedidos);
		mv.addObject("valorTotalVendas", valorTotalVenda);
		mv.addObject("quantidadeVendas", quantidadeVenda);
		mv.addObject("estoqueProdutos", estoqueProduto);
		mv.addObject("msgMesEano", mesesTexto);

		System.out.println("\n\n\n\n\n\nAnooo: " + anoAtual + " Mes atual:  " + mesAtual + "\n\n\n\n");
		
		return mv;
	}

	@GetMapping("/ajaxHistorico/{mes}/{ano}")
	public ModelAndView getHistoricoByMesAndAno(@PathVariable("mes") int mes,@PathVariable("ano") int ano) {

		GregorianCalendar dataPedido = new GregorianCalendar();
		int mesPedido;
		int anoPedido;
			
		User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		List<Pedido> pedidosBanco = pedidoService.findAllByRevendedorAndStatusPedido(user.getId(),
				StatusPedido.ENTREGUE);
		List<Pedido> pedidos = new ArrayList<>();

		for (Pedido p : pedidosBanco) {
			if (p != null) {
					
				dataPedido.setTime(p.getData());
				mesPedido = (dataPedido.get(Calendar.MONTH) + 1);
				anoPedido = (dataPedido.get(Calendar.YEAR));
				
				if (mes == mesPedido && ano == anoPedido)  {
					if (p.getRevendedor().getId() == user.getId()) {
						pedidos.add(p);
					}
				}
			}
		}
		
		String msgMesEano = mesEano(mes,ano);
			
		ModelAndView mv = new ModelAndView("historico/ajaxHistorico");
		mv.addObject("pedidos", pedidos);
		mv.addObject("msgMesEano", msgMesEano);

		return mv;
	}

	public String mesEano(int mesNumero, int anoNumero) {

		Date date = new Date();
		GregorianCalendar dataCal = new GregorianCalendar();
		dataCal.setTime(date);
			
		
		if(anoNumero == 0) {	
			anoNumero = (dataCal.get(Calendar.YEAR));
		}
		if(mesNumero == 0) {
			mesNumero = (dataCal.get(Calendar.MONTH)+1);
		}
		
		System.out.println("\n\n\n\nMes: " + mesNumero + "\n\n");
		
		String mesTexto = null;

		switch (mesNumero) {
		
		case 1:
			mesTexto = "Janeiro ";
			break;
		case 2:
			mesTexto = "Fevereiro ";
			break;
		case 3:
			mesTexto = "Março ";
			break;
		case 4:
			mesTexto = "Abril ";
			break;
		case 5:
			mesTexto = "Maio ";
			break;
		case 6:
			mesTexto = "Junho ";
			break;
		case 7:
			mesTexto = "Julho ";
			break;
		case 8:
			mesTexto = "Agosto ";
			break;
		case 9:
			mesTexto = "Setembro ";
			break;
		case 10:
			mesTexto = "Outubro ";
			break;
		case 11:
			mesTexto = "Novembro ";
			break;
		case 12:
			mesTexto = "Dezembro ";
			break;

		default:
			break;
		}
		return mesTexto + "de " + anoNumero;
	}

}
