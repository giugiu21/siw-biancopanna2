package it.uniroma3.siw.controller;


import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
public class RecipeController {
    @Autowired
    RecipeRepository recipeRepository;

    @GetMapping("/formNewRecipe")
    public String FormNewRecipe(Model model){
        model.addAttribute("recipe", new Recipe());
        return "formNewRecipe.html";
    }
    

    @PostMapping("/newRecipe")
    public String newRecipe(@ModelAttribute("recipe") Recipe recipe, Model model){
        if(!recipeRepository.existsByName(recipe.getName())){
            this.recipeRepository.save(recipe);
            model.addAttribute("recipe", recipe);
            return "recipe.html";
        }
        else{
            model.addAttribute("messaggioErrore", "Questa ricetta già esiste");
            return "formNewRecipe.html";
        }
    }

    @GetMapping("/recipes/{id}")
    public String getRecipe(@PathVariable("id")Long id, Model model){
        model.addAttribute("recipe", this.recipeRepository.findById(id).get());
        return "recipe.html";
    }

    @GetMapping("/recipes")
    public String allRecipes(Model model){
        model.addAttribute("recipes", this.recipeRepository.findAll());
        return "recipes.html";
    }

   @PostMapping("/searchRecipe")
    public String searchRecipe(Model model, @RequestParam String name){
        model.addAttribute("recipes", this.recipeRepository.findByName(name));
        return "foundRecipe.html";
   }
}
