package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Chef;
import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.repository.ChefRepository;
import it.uniroma3.siw.repository.RecipeRepository;

@Controller
public class AdminController {

	@Autowired
	RecipeRepository recipeRepository;
	
	@Autowired
	ChefRepository chefRepository;

	@GetMapping("/admin/indexAdmin")
	public String indexAdmin(Model model) {
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
			return "recipe.html";
		} else {
			model.addAttribute("messaggioErrore", "Questa ricetta già esiste");
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
			return "recipes.html";
		} else {
			model.addAttribute("messaggioErrore", "Questo chef già esiste");
			return "admin/formNewChef.html";
		}
	}
	

    @GetMapping("/admin/recipesAdmin")
    public String allRecipes(Model model){
        model.addAttribute("recipes", this.recipeRepository.findAll());
        return "admin/recipesAdmin.html";
    }

}
