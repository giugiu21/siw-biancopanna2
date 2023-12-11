package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.UserService;
import it.uniroma3.siw.validators.CredentialsValidator;
import it.uniroma3.siw.validators.UserValidator;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import jakarta.validation.Valid;

@Controller
public class ViewController {
	
	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserValidator userValidator;
	
	@Autowired
	private CredentialsValidator credentialsValidator;
	
	@GetMapping("/")
	public String index(Model model) {
		return "index.html";
	}
	
	@GetMapping("/register")
	public String registration(Model model){
		model.addAttribute("user", new User());
		model.addAttribute("credentials", new Credentials());
		return "formRegistration.html";
		
	}
	
	@PostMapping("/register")
	public String registerUser(@Valid @ModelAttribute("user") User user,
			BindingResult userBindingResult, @Valid
			@ModelAttribute("credentials") Credentials credentials,
			BindingResult credentialsBindingResult,
			Model model) {
		this.userValidator.validate(user,userBindingResult);
		this.credentialsValidator.validate(credentials, credentialsBindingResult);                        
		if(!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
			credentials.setUser(user);
			credentialsService.saveCredentials(credentials);
			userService.saveUser(user);
			model.addAttribute("user", user);
			return "formLogin.html";
		}
		return "formRegistration.html";
	}
	
	@GetMapping("/login")
	public String loginPage(Model model) {
		return "formLogin.html";
	}
	

}
