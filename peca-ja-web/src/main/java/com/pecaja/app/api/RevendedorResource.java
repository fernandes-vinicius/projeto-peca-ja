package com.pecaja.app.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.pecaja.app.dtos.DistanceMatrix;
import com.pecaja.app.dtos.Element;
import com.pecaja.app.dtos.UserDto;
import com.pecaja.app.enums.Situacao;
import com.pecaja.app.enums.Status;
import com.pecaja.app.models.Produto;
import com.pecaja.app.models.User;
import com.pecaja.app.services.ProdutoService;
import com.pecaja.app.services.UserService;

@RestController
@RequestMapping("/api/revendedor")
public class RevendedorResource {
	
	@Autowired
	UserService revendedorService;
	
	@Autowired
	ProdutoService produtoService;

	@GetMapping(value = "/listProximos/{origem}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<UserDto>> getRevendedoresPoximos(@PathVariable("origem") String origem) {

		List<User> revendedoresdb = revendedorService.findAllBySituacaoAndStatus(Situacao.ACEITO, Status.ATIVO);

		RestTemplate rest = new RestTemplate();

		String destinations = "";
		for (int i = 0; i < revendedoresdb.size(); i++) {
			destinations += revendedoresdb.get(i).getEndereco().getShortEndereco();
			if (i < (revendedoresdb.size() - 1)) {
				destinations += "|";
			}
		}

		System.out.println(destinations);

		String json = rest.getForObject(
				"https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&" + "origins=" + origem 
						+ "&destinations=" + destinations + "&key=AIzaSyC0kMOF-XXuZ-eyDe5xNSqHpN9nxHQGFYo",
				String.class);
		
		Gson gson = new Gson();
		DistanceMatrix distancematrix = gson.fromJson(json, DistanceMatrix.class); 

		List<String> revendedores = distancematrix.getDestinationAddresses();
		List<Element> elements = distancematrix.getRows().get(0).getElements();
		List<UserDto> proximos = new ArrayList<>();
		
		for (int i = 0; i < revendedores.size(); i++) {
			int tempo = (elements.get(i).getDuration().getValue())/60;
			if (tempo < 20) {
				List<Produto> produtos = produtoService.findAllByRevendedor(revendedoresdb.get(i).getId(),Status.ATIVO);
				if(!produtos.isEmpty()) {
					proximos.add(
							new UserDto(revendedoresdb.get(i).getId(),revendedoresdb.get(i).getNome(),revendedoresdb.get(i).getTelefone(),revendedoresdb.get(i).getEmail(), revendedoresdb.get(i).getFantasia(), revendedoresdb.get(i).getCnpj(),produtos, tempo)
							);
				}
			}
		}

		if(proximos.isEmpty()){
			return new ResponseEntity<List<UserDto>>(proximos, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<UserDto>>(proximos, HttpStatus.OK);
	}
	
}
