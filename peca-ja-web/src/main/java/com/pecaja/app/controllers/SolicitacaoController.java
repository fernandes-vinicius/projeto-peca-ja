package com.pecaja.app.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.context.Context;

import com.google.gson.Gson;
import com.pecaja.app.dtos.DistanceMatrix;
import com.pecaja.app.dtos.RevendedorDto;
import com.pecaja.app.enums.Situacao;
import com.pecaja.app.enums.Status;
import com.pecaja.app.models.Cidade;
import com.pecaja.app.models.Endereco;
import com.pecaja.app.models.Estado;
import com.pecaja.app.models.Mail;
import com.pecaja.app.models.Role;
import com.pecaja.app.models.User;
import com.pecaja.app.services.CidadeService;
import com.pecaja.app.services.EnderecoService;
import com.pecaja.app.services.EstadoService;
import com.pecaja.app.services.RoleService;
import com.pecaja.app.services.UserService;

@Controller
@RequestMapping("/solicitacoes")
public class SolicitacaoController {

	@Autowired
	private UserService userService;
	@Autowired
	private EstadoService estadoService;
	@Autowired
	private CidadeService cidadeSerive;
	@Autowired
	private EnderecoService enderecoService;
	@Autowired
	private EmailControllerr email;
	@Autowired
	private RoleService roleService;

	@GetMapping("/list")
	public ModelAndView list() {
		ModelAndView mv = new ModelAndView("solicitacoes/list");
		mv.addObject("users", userService.findAllBySituacaoAndStatus(Situacao.AGUARDANDO, Status.ATIVO));
		return mv;
	}

	@GetMapping("/ajaxbydetalhes/{id}")
	public ModelAndView ajaxbydetalhes(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView("solicitacoes/ajaxbydetalhes");
		mv.addObject("u", userService.findById(id));
		return mv;
	}
	
	@PostMapping("/save")
	@ResponseBody
	public String save(@RequestBody User user, BindingResult result) throws MessagingException {

		Mail mail = new Mail("equipepecaja@gmail.com", user.getEmail(), "Solicitação recebida",
				"Nossos analistas vão avaliar seus dados e logo entraremos em contato");
		Context context = new Context();
		context.setVariable("mensagem", mail.getContent());
		context.setVariable("nomerevendedor", user.getNome());

		User userBanco = userService.findByUserCnpj(user.getCnpj());
		User userBancoEmail = userService.findByEmail(user.getEmail());
		
		if(userBancoEmail != null) {
			return "408";
		}
		else {
			if (result.hasErrors()) {
				return "erro";
			}

			if (userBanco != null) {
				return "401";
			}

			if (consultaCNPJ( user )) {
				userService.save(user);
				System.out.println("\n\n\nRevendedor salvo com sucesso\n\n");
				email.sendHtmlMail(mail, context, "solicitacaoRecebida", "Olá " + user.getNome() + ".");
				return "200";
			} else {
				System.out.println("\n\n\nCNPJ inválido\n\n");
				return "402";
			}
		}
		
	}

	@GetMapping("/aceitar/{id}")
	public String aceitar(@PathVariable("id") Long id, RedirectAttributes ra) throws MessagingException {

		User user = userService.findById(id);

		if (user != null) {
			Role roleRevendedor = roleService.findByNome("ROLE_REVENDEDOR");
			List<Role> roles = new ArrayList<>();
			roles.add(roleRevendedor);
			
			Mail mail = new Mail("equipepecaja@gmail.com", user.getEmail(), "Solicitação Aceita",
					"Bem Vindo ao Peca-Já");
			Context context = new Context();
			context.setVariable("mensagem", mail.getContent());
			context.setVariable("nomerevendedor", user.getNome());
			//senha temporaria
			String[] carct ={"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
		    String novaSenha="";

		    for (int x=0; x<8; x++){
		        int j = (int) (Math.random()*carct.length);
		        novaSenha += carct[j];
		    }
		
		    //usuario temporario
		    String usuarioarray[] = user.getNome().split(" "); 
		    String usuario = usuarioarray[0];
		    
		    String[] carctn ={"0","1","2","3","4","5","6","7","8","9"};
		    for (int x=0; x<2; x++){
		        int j = (int) (Math.random()*carctn.length);
		        usuario += carctn[j];
		    }
		    
		    user.setRoles(roles);
		    user.setUsername(usuario.toLowerCase());
            user.setPassword(new BCryptPasswordEncoder().encode(novaSenha));            
			user.setSituacao(Situacao.ACEITO);
			userService.save(user);
			
			System.out.println(user.getPassword()+"\n\n\n\n\n\n\n\n");
			
            context.setVariable("senha", novaSenha);
            context.setVariable("usuario", user.getUsername());
            email.sendHtmlMail(mail, context, "solicitacaoAceita", "Olá "+user.getNome()+".");
			ra.addFlashAttribute("mensagem", "Solicitação aceita com sucesso");

		} else
			ra.addFlashAttribute("mensagemErro", "Aguarde!!, Infelizmente houve algum erro");

		return "redirect:/solicitacoes/list";
	}

	@GetMapping("/negar/{id}")
	public String negar(@PathVariable Long id, RedirectAttributes ra) throws MessagingException {

		User user = userService.findById(id);
		user.setStatus(Status.INATIVO);

		userService.save(user);

		Mail mail = new Mail("equipepecaja@gmail.com", user.getEmail(), "Solicitação Negada",
				"Infelismente no momento não podemos liberar acesso ao nosso sistema para você");
		Context context = new Context();
		context.setVariable("nomerevendedor", user.getNome());

		user.setSituacao(Situacao.ACEITO);
		userService.save(user);
//		email.sendHtmlMail(mail, context, "solicitacaoNegada", "Olá " + user.getNome() + ".");
		ra.addFlashAttribute("mensagem", "Solicitação aceita com sucesso");

		ra.addFlashAttribute("mensagem", "Solicitação negada com sucesso");

		return "redirect:/solicitacoes/list";
	}

	public boolean consultaCNPJ(User userRevendedor) {

		RestTemplate restTemplate = new RestTemplate();

		String reveDtoString = restTemplate.getForObject(
				"https://www.receitaws.com.br/v1/cnpj/"
						+ userRevendedor.getCnpj().replace(".", "").replace("/", "").replace("-", ""),
				String.class);
		
		Gson gson = new Gson();
		RevendedorDto reveDto = gson.fromJson(reveDtoString, RevendedorDto.class);
		System.out.println("\n\n\nCnpj apos verificar " + reveDto.getCnpj() + "\n\n");

		if (reveDto.getStatus().equals("OK")) {
			setRevendedorByRevendedorDto(reveDto, userRevendedor);
			return true;
		}

		return false;
	}

	public void setRevendedorByRevendedorDto(RevendedorDto reveDto, User userRevendedor) {

		Estado estado = new Estado();
		estado.setUf(reveDto.getUf());
		estadoService.save(estado);
		estadoService.save(estado);

		Cidade cidade = new Cidade();
		cidade.setNome(reveDto.getMunicipio());
		cidadeSerive.save(cidade);
		cidadeSerive.save(cidade);

		Endereco endereco = new Endereco();
		endereco.setBairro(reveDto.getBairro());
		endereco.setCidade(cidade);
		endereco.setEstado(estado);
		endereco.setRua(reveDto.getLogradouro());
		enderecoService.save(endereco);

		userRevendedor.setNome(reveDto.getNome());
		userRevendedor.setTelefone(reveDto.getTelefone());
		userRevendedor.setFantasia(reveDto.getFantasia());
		userRevendedor.setAtividade_principal(reveDto.getAtividadePrincipal().get(0).getText());
		userRevendedor.setCnpj(reveDto.getCnpj());
		userRevendedor.setAbertura(reveDto.getAbertura());
		userRevendedor.setSituacao(Situacao.AGUARDANDO);
		userRevendedor.setNatureza_juridica(reveDto.getNaturezaJuridica());
		userRevendedor.setEndereco(endereco);
		userRevendedor.setStatus(Status.ATIVO);

	}

	@GetMapping("/ajax/countSolicitacoes")
	@ResponseBody
	public int ajaxCountRevendedoresByAguardando() {
		return userService.countSolicitacoes();
	}
	
}
