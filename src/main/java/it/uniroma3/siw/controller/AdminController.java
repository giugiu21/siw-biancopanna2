package it.uniroma3.siw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
	
	@GetMapping("admin/indexAdmin")
	public String indexAdmin(Model model) {
		return "admin/indexAdmin.html";
	}

}
