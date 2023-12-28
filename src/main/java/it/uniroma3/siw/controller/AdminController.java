package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.model.Chef;
import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.repository.ChefRepository;
import it.uniroma3.siw.repository.RecipeRepository;
import it.uniroma3.siw.repository.ReviewRepository;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.UserService;
import it.uniroma3.siw.validators.RecipeValidator;

@Controller
public class AdminController {

	@Autowired
	RecipeRepository recipeRepository;
	
	@Autowired
	ChefRepository chefRepository;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CredentialsService credentialsService;
	
	@Autowired
	private ReviewRepository reviewRepository;


	
	@Autowired
	RecipeValidator recipeValidator;

	
	@GetMapping("/admin/indexAdmin")
	public String indexAdmin(Model model) {
		model.addAttribute("recipes", this.recipeRepository.findTopN(4));
		return "admin/indexAdmin.html";
	}
	

	@GetMapping("/admin/formNewRecipe")
	public String FormNewRecipe(Model model) {
		model.addAttribute("recipe", new Recipe());
		return "admin/formNewRecipe.html";
	}

	@PostMapping("/admin/newRecipe")
	public String newRecipe(@ModelAttribute("recipe") Recipe recipe, Model model) {
		if (!recipeRepository.existsByName(recipe.getName())) {
			this.recipeRepository.save(recipe);
			model.addAttribute("recipe", recipe);
			
			model.addAttribute("admin", true);
			
			return "recipe.html";
		} else {
			model.addAttribute("recipeError", "Questa ricetta già esiste!");
			return "admin/formNewRecipe.html";
		}
	}
	

	
	@GetMapping("/admin/formNewChef")
	public String FormNewChef(Model model) {
		model.addAttribute("chef", new Chef());
		return "admin/formNewChef.html";
	}
	
	@PostMapping("/admin/newChef")
	public String newChef(@ModelAttribute("chef") Chef chef, Model model) {
		if (!chefRepository.existsByName(chef.getName())) {
			this.chefRepository.save(chef);
			model.addAttribute("chef", chef);
			model.addAttribute("recipes", this.recipeRepository.findAll());
			model.addAttribute("admin", true);
		    return "recipes.html";
		} else {
			model.addAttribute("chefError", "Questo chef già esiste");
			return "admin/formNewChef.html";
		}
	}
	
	@GetMapping(value="/admin/setChefToRecipe/{chefId}/{recipeId}")
	public String setChefToRecipe(@PathVariable("chefId") Long chefId, @PathVariable("recipeId") Long recipeId, Model model) {
		
		Chef chef = this.chefRepository.findById(chefId).get();
		Recipe recipe = this.recipeRepository.findById(recipeId).get();
		recipe.setChef(chef);
		this.recipeRepository.save(recipe);
		
		model.addAttribute("recipe", recipe);
		model.addAttribute("recipes", this.recipeRepository.findAll());
		model.addAttribute("admin", true);
		return "recipes.html";
	}
	
	@GetMapping("/admin/deleteReview/{recipeId}/{reviewId}")
	public String removeReview(Model model, @PathVariable("recipeId") Long recipeId,@PathVariable("reviewId") Long reviewId){
		Recipe recipe = this.recipeRepository.findById(recipeId).get();
		Review review = this.reviewRepository.findById(reviewId).get();
		//UserDetails userDetails = this.userService.getUserDetails();

		recipe.getReviews().remove(review);
		this.reviewRepository.delete(review);
		this.recipeRepository.save(recipe);

		model.addAttribute("recipe", this.recipeRepository.findById(recipeId).get());
		model.addAttribute("admin", true);
		
		return "recipe.html";
	}
	
	

	@RequestMapping(value = "/admin/delete/{id}", method = RequestMethod.GET)
	public String deleteRecipe(@PathVariable("id") Long id, Model model) {
		this.recipeRepository.deleteById(id);
		model.addAttribute("messaggioCancellazione", "Ricetta cancellata");
		model.addAttribute("recipes", this.recipeRepository.findAll());
		model.addAttribute("admin", true);
		return "recipes.html";
	}

	
	@GetMapping("/admin/formUpdateRecipe/{id}")  
    public String formUpdateRecipe(@PathVariable("id") Long id, Model model){
        model.addAttribute("recipe", this.recipeRepository.findById(id).get());
        model.addAttribute("chefs", this.chefRepository.findAll());
        return "admin/formUpdateRecipe.html";
    }
	

    
    
}
