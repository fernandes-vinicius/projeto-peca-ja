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

import com.pecaja.app.models.Estado;
import com.pecaja.app.services.EstadoService;

@RestController
@RequestMapping("/api/estados")
@CrossOrigin(origins = { "http://localhost:8080" })
public class EstadoResource {

	@Autowired
	private EstadoService estadoService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<Estado>> findAll() {

		List<Estado> estados = estadoService.findAll();
		if (estados.isEmpty())
			return new ResponseEntity<List<Estado>>(HttpStatus.NO_CONTENT);

		return new ResponseEntity<List<Estado>>(estados, HttpStatus.OK);
	}

}
