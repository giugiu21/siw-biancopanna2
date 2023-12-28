package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.RecipeServiceClass;
import it.uniroma3.siw.service.UserService;
import it.uniroma3.siw.validators.CredentialsValidator;
import it.uniroma3.siw.validators.ReviewValidator;
import it.uniroma3.siw.validators.UserValidator;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.RecipeRepository;
import it.uniroma3.siw.repository.ReviewRepository;
import jakarta.validation.Valid;

@Controller
public class ViewController {
	
	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RecipeServiceClass recipeService;
	
	@Autowired
	private RecipeRepository recipeRepository;
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private ReviewValidator reviewValidator;
	
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
			model.addAttribute("recipes", this.recipeRepository.findTopN(4));
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
	
	@GetMapping("/recipe/{id}")
	public String product(@PathVariable("id") Long id, Model model) {

		Recipe recipe = this.recipeRepository.findById(id).get();
		model.addAttribute("recipe", recipe);
		
		
		UserDetails userDetails = this.userService.getUserDetails();
        
        if(userDetails != null && this.credentialsService.getCredentials(userDetails.getUsername()).getRole().equals(Credentials.ADMIN_ROLE)){
			model.addAttribute("admin", true);
		}
        
        if(userDetails != null && this.credentialsService.getCredentials(userDetails.getUsername()).getRole().equals(Credentials.DEFAULT_ROLE)){
        	/* Gestione della review */
    		model.addAttribute("review", new Review());
		}

		
		return "recipe.html";
	}
	
	@PostMapping("/user/review/{productId}")
	public String addReview(Model model, @Valid @ModelAttribute("review") Review review, BindingResult bindingResult, @PathVariable("productId") Long id){
		this.reviewValidator.validate(review,bindingResult);
		Recipe recipe = this.recipeRepository.findById(id).get();
		String username = this.userService.getUserDetails().getUsername();

		if(!bindingResult.hasErrors() && !this.recipeService.hasReviewFromAuthor(id, username)){
			if(this.userService.getUserDetails() != null && !recipe.getReviews().contains(review)){
				review.setAuthor(username);
				this.reviewRepository.save(review);
				recipe.getReviews().add(review);
			}
		}
		this.recipeRepository.save(recipe);

		if(this.userService.getUserDetails() != null && !recipe.getReviews().contains(review)){
			if(!this.recipeService.hasReviewFromAuthor(id, username)){
				this.reviewRepository.save(review);
				recipe.getReviews().add(review);
			}
			else{
				model.addAttribute("reviewError", "Already Reviewed!");
			}

		}
		this.recipeRepository.save(recipe);

		model.addAttribute("recipe", recipe);

		
		return "recipe.html";
	}

	
	
	

}
