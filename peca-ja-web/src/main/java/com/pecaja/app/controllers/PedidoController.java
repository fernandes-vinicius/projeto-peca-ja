package com.pecaja.app.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.context.Context;

import com.pecaja.app.enums.Status;
import com.pecaja.app.enums.StatusPedido;
import com.pecaja.app.models.Mail;
import com.pecaja.app.models.Pedido;
import com.pecaja.app.models.Produto;
import com.pecaja.app.models.User;

import com.pecaja.app.services.ClienteService;
import com.pecaja.app.services.PedidoService;
import com.pecaja.app.services.ProdutoService;
import com.pecaja.app.services.UserService;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	private PedidoService pedidoService;

	@Autowired
	ClienteService clienteService;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailControllerr email;

	@GetMapping("/list")
	public ModelAndView list() throws MessagingException, IOException {
		return verificacaoDePedidos(StatusPedido.AGUARDANDO, "list");
	}

	@GetMapping("/encaminhar/{id}")
	public String encaminhar(@PathVariable("id") Long id, RedirectAttributes ra) {

		Pedido pedido = pedidoService.findById(id);
		pedido.setStatusPedido(StatusPedido.ACAMINHO);
		pedidoService.save(pedido);

		ra.addFlashAttribute("mensagem", "Agora é só aguardar, o pedido foi encaminhado");

		return "redirect:/pedidos/list";
	}

	@GetMapping("list/caminho")
	public ModelAndView listCaminho() {
		return verificacaoDePedidos(StatusPedido.ACAMINHO, "listAcaminho");
	}

	@GetMapping("/detalhes/{id}")
	public ModelAndView detalhes(@PathVariable("id") Long id) {

		Pedido pedido = pedidoService.findById(id);

		ModelAndView mv = new ModelAndView("pedidos/detalhes");
		mv.addObject("pedido", pedido);	
		
		return mv;
	}

	@GetMapping("/entregar/{id}")
	public String entregar(@PathVariable Long id, RedirectAttributes ra) {

		Pedido pedido = pedidoService.findById(id);
		pedido.setStatusPedido(StatusPedido.ENTREGUE);
		pedidoService.save(pedido);

		ra.addFlashAttribute("mensagem", "O pedido foi entregue com sucesso");	

		return "redirect:/pedidos/list/caminho";
	}

	@GetMapping("/ajax/atualizarPedidos")
	public ModelAndView atualizarList() {
		return verificacaoDePedidos(StatusPedido.AGUARDANDO, "ajaxList");
	}

	@GetMapping("/formRejeitarPedido/{id}")
	public ModelAndView formRejeitarPedido(@PathVariable Long id) {

		Pedido pedido = pedidoService.findById(id);

		ModelAndView mv = new ModelAndView("pedidos/formRejeitarPedido");
		mv.addObject("pedido", pedido);
		return mv;
	}

	@PostMapping("/rejeitarPedido")
	public String rejeitarPedido(Long id,String motivacao, RedirectAttributes ra) throws MessagingException{
		
		Pedido pedido = pedidoService.findById(id); 
 		pedido.setStatusPedido(StatusPedido.CANCELADO);
		pedidoService.save(pedido);
		
		Mail mail = new Mail("equipepecaja@gmail.com", pedido.getCliente().getEmail(), "Compra cancelada pelo Revendedor",
				"A distribuidora "+pedido.getRevendedor().getFantasia()+ " não aceitou seu pedido");
		
		Context context = new Context();
		context.setVariable("mensagem", mail.getContent());
		context.setVariable("nomeCliente", pedido.getCliente().getNome());
		context.setVariable("nomeRevendedor", pedido.getRevendedor().getNome());
		context.setVariable("motivacao", motivacao);
		context.setVariable("emailRevendedor", pedido.getRevendedor().getEmail());
		
		email.sendHtmlMail(mail, context, "pedidoRejeitado", "");
		ra.addFlashAttribute("mensagem", "Pedido rejeitado com sucesso");
		return "redirect:/pedidos/list";
	}
	
	@GetMapping("/cancelarEntrega/{id}")
	public String cancelarEntrega(@PathVariable Long id, RedirectAttributes ra) {

		Pedido pedido = pedidoService.findById(id);
		
		pedido.setStatusPedido(StatusPedido.AGUARDANDO);
		pedidoService.save(pedido);
		
		ra.addFlashAttribute("mensagem", "Pedido inserido novamente na lista de aguardando");
		return "redirect:/pedidos/list/caminho";
	}
	//regeitarPedido
	public ModelAndView verificacaoDePedidos(StatusPedido statusPedido, String view) {

		// Revendedor Logado
		User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

		List<Produto> produtosDB = produtoService.findAllByRevendedor(user.getId(), Status.ATIVO);
		List<Pedido> pedidosDB = pedidoService.findAll();
		List<Pedido> pedidos = new ArrayList<>();

		for (Pedido ped : pedidosDB) {

			for (Produto pro : produtosDB) {

				if (pro.getId() == ped.getProduto().getId() && ped.getStatusPedido().equals(statusPedido)) {
					pedidos.add(ped);
				}
			}
		}

		ModelAndView mv = new ModelAndView("pedidos/" + view);
		mv.addObject("pedidos", pedidos);
		return mv;
	}

	@GetMapping("/ajax/countPedidosAguardando")
	@ResponseBody
	public int ajaxCountPedidosByAguardando() {
		
		int countPedidos = 0;
		
		// Revendedor Logado
		User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		
		List<Pedido> pedidosAguardando = pedidoService.findAllByStatusPedido(StatusPedido.AGUARDANDO);
		
		for (Pedido pedido : pedidosAguardando) {
			if(pedido.getProduto().getUser().getId() == user.getId()) {
				countPedidos++;
			}
		}
		
		return countPedidos;//pedidoService.countPedidosbyRevendedorwhereStatus(user.getId(), StatusPedido.AGUARDANDO);
	}
}
