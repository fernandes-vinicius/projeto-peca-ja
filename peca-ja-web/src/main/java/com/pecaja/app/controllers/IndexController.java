package com.pecaja.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	@RequestMapping("/")
	public String index() {
		return "index/index";
	}

	@RequestMapping("/indexRevendedores")
	public String indexRevendedores() {
		return "index/indexRevendedores";
	}

}
