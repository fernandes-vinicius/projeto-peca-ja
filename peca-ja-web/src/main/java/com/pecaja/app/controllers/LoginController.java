package com.pecaja.app.controllers;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.context.Context;

import com.pecaja.app.models.Mail;
import com.pecaja.app.models.User;
import com.pecaja.app.services.UserService;

@Controller
public class LoginController {
	
	@Autowired
	private EmailControllerr emailController;
	@Autowired
	private UserService userService;
	
	@RequestMapping(path = "/login")
	public String form() {
		return "login";
	}

	@RequestMapping(path = "/forgotPassword")
	public String formForgotPassword() {
		return "forgotPassword";
	}
	
	@RequestMapping(path = "/esqueciSenha")
	public String recuperarSenha(String email, RedirectAttributes ra) throws MessagingException {
		User user = userService.findByEmail(email);
		
		if(user != null) {
			Mail mail = new Mail("equipepecaja@gmail.com", user.getEmail(), "Esqueceu sua Senha?",
					"Equipe de Atendimento Peca-Já");
			Context context = new Context();
			context.setVariable("nomerevendedor", user.getNome());
			
			//senha temporaria
			String[] carct ={"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
		    String novaSenha="";
	
		    for (int x=0; x<8; x++){
		        int j = (int) (Math.random()*carct.length);
		        novaSenha += carct[j];
		    }
		    
		    context.setVariable("novaSenha", novaSenha); 
		    user.setPassword(new BCryptPasswordEncoder().encode(novaSenha)); 
		
		    emailController.sendHtmlMail(mail, context, "newPassword", "Olá "+user.getNome()+".");
		    userService.save(user);
		    
		    ra.addFlashAttribute("mensagem", "Sua nova senha foi enviada para o email");
		    System.out.println("\n\n\n\n\n\n\n\n\n"+ user.getPassword()+ "\n\n\n\n\n\n\n\n\n\n");
		    return "redirect:/login";
		}
		
		ra.addFlashAttribute("mensagemErro", "Email incorreto");
		return "redirect:/forgotPassword";
	}
}
