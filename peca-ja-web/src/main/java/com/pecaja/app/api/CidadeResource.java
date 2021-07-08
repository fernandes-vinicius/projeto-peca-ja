package com.pecaja.app.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pecaja.app.models.Cidade;
import com.pecaja.app.services.CidadeService;

@RestController
@RequestMapping("/api/cidades")
@CrossOrigin(origins = { "http://localhost:8080" })
public class CidadeResource {

	@Autowired
	private CidadeService cidadeService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<Cidade>> findAll() {

		List<Cidade> cidades = cidadeService.findAll();
		if (cidades.isEmpty())
			return new ResponseEntity<List<Cidade>>(HttpStatus.NO_CONTENT);

		return new ResponseEntity<List<Cidade>>(cidades, HttpStatus.OK);
	}

}
