package com.pecaja.app.controllers;

import org.springframework.http.MediaType;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pecaja.app.enums.StatusPedido;
import com.pecaja.app.models.User;
import com.pecaja.app.services.PedidoService;
import com.pecaja.app.services.UserService;

@Controller
@RequestMapping("/perfil")
public class PerfilController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private PedidoService pedidoService;

	@Autowired
	private BCryptPasswordEncoder passwordEncode;

	@GetMapping
	private ModelAndView perfil() {

		User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		
		int qtdPedidoAguardan = pedidoService.countPedidosbyRevendedorwhereStatus(user.getId(), StatusPedido.AGUARDANDO);
		int qtdPedidoAcaminho = pedidoService.countPedidosbyRevendedorwhereStatus(user.getId(), StatusPedido.ACAMINHO);
 		int qtdPedidoEntregue = pedidoService.countPedidosbyRevendedorwhereStatus(user.getId(), StatusPedido.ENTREGUE);
		int qtdPedidoCancelad = pedidoService.countPedidosbyRevendedorwhereStatus(user.getId(), StatusPedido.CANCELADO);
		int qtdPedido = (qtdPedidoAcaminho + qtdPedidoAguardan + qtdPedidoCancelad + qtdPedidoEntregue);
		
		
		ModelAndView mv = new ModelAndView("perfil/perfil");
		mv.addObject("user", user);
		mv.addObject("qtdPedido", qtdPedido);
		mv.addObject("qtdPedidoEntregue", qtdPedidoEntregue);
		mv.addObject("qtdPedidoCancelado", qtdPedidoCancelad);

		return mv;
	}

	@PostMapping("/save")
	public ModelAndView save(User user, @RequestParam("uploadfile") MultipartFile file,
			@RequestParam("novaSenha") String novaSenha, @RequestParam("confirmeSenha") String confirmeSenha,
			RedirectAttributes ra) throws IOException {

		User userBanco = userService.findById(user.getId());

		System.out.println("\n\n\nNova senha: " + novaSenha + "\n\n\n");

		if (!file.isEmpty()) {
			user.setImagem(file.getBytes());
		} else {
			user.setImagem(userBanco.getImagem());
		}

		if (user.getPassword() != null && !novaSenha.isEmpty() && !confirmeSenha.isEmpty()) {

			if (userBanco.getPassword().equals(passwordEncode.encode(novaSenha))) {
				if (novaSenha.equals(confirmeSenha)) {
					user.setPassword(passwordEncode.encode(novaSenha));
				}
			} else {
				ra.addFlashAttribute("mensagemErro", "Você tentou mudar a senha, porém as senhas não coincidem");
				return new ModelAndView("redirect:/perfil");
			}
		}

		user.setPassword(userBanco.getPassword());
		user.setDataCriacao(userBanco.getDataCriacao());
		userService.save(user);
		ra.addFlashAttribute("mensagem", "Perfil editado com sucesso");

		return new ModelAndView("redirect:/perfil");
	}

	@RequestMapping(value = "/image/{image_id}", produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<byte[]> getImage(@PathVariable("image_id") Long imageId) throws IOException {

		User user = userService.findOne(imageId);

		byte[] imageContent = user.getImagem();
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_PNG);
		return new ResponseEntity<byte[]>(imageContent, headers, HttpStatus.OK);
	}

}
