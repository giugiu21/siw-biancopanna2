package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.repository.RecipeRepository;

@Controller
public class AdminController {

	@Autowired
	RecipeRepository recipeRepository;

	@GetMapping("admin/indexAdmin")
	public String indexAdmin(Model model) {
		return "admin/indexAdmin.html";
	}

	@GetMapping("/formNewRecipe")
	public String FormNewRecipe(Model model) {
		model.addAttribute("recipe", new Recipe());
		return "formNewRecipe.html";
	}

	@PostMapping("/newRecipe")
	public String newRecipe(@ModelAttribute("recipe") Recipe recipe, Model model) {
		if (!recipeRepository.existsByName(recipe.getName())) {
			this.recipeRepository.save(recipe);
			model.addAttribute("recipe", recipe);
			return "recipe.html";
		} else {
			model.addAttribute("messaggioErrore", "Questa ricetta già esiste");
			return "formNewRecipe.html";
		}
	}

}
