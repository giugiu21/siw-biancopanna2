package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.UserService;
import it.uniroma3.siw.validators.CredentialsValidator;
import it.uniroma3.siw.validators.UserValidator;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.RecipeRepository;
import jakarta.validation.Valid;

@Controller
public class ViewController {
	
	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RecipeRepository recipeRepository;
	
	@Autowired
	private UserValidator userValidator;
	
	@Autowired
	private CredentialsValidator credentialsValidator;
	
	@GetMapping("/")
	public String index(Model model) {
		return "index.html";
	}
	
	@GetMapping("/indexLogged")
	public String indexLogged(Model model) {
		//model.addAttribute("recipes", this.recipeRepository.findAll());
		model.addAttribute("recipes", this.recipeRepository.findTopN(4));
		return "indexLogged.html";
	}
	
	@GetMapping("/success")
	public String loginSuccess(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = null;
		Credentials credentials = null;
		if(!(authentication instanceof AnonymousAuthenticationToken)){
			userDetails = (UserDetails)authentication.getPrincipal();
			credentials = this.credentialsService.getCredentials(userDetails.getUsername());
		}
		if(credentials != null && credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
			return "admin/indexAdmin.html";
		}

		/*model.addAttribute("userDetails", userDetails);*/
		//model.addAttribute("recipes", this.recipeRepository.findAll());
		model.addAttribute("recipes", this.recipeRepository.findTopN(4));
		return "indexLogged.html";
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
