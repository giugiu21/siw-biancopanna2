package it.uniroma3.siw.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Chef;
import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.repository.ChefRepository;
import it.uniroma3.siw.repository.RecipeRepository;

@Service
public class RecipeServiceClass {
	
	@Autowired
	RecipeRepository recipeRepository;
	
	@Autowired
	ChefRepository chefRepository;

	@Transactional
    public void editDetailsToRecipe(Recipe recipe) {
        // Recupera il prodotto esistente dal repository utilizzando l'ID
        Recipe existingRecipe = recipeRepository.findById(recipe.getId()).orElse(null);

        if (existingRecipe != null) {
            // Aggiorna i dettagli del prodotto con i nuovi valori
            existingRecipe.setName(recipe.getName());
            existingRecipe.setImage(recipe.getImage());
            existingRecipe.setIngredients(recipe.getIngredients());
            existingRecipe.setDescription(recipe.getDescription());
            existingRecipe.setDifficulty(recipe.getDifficulty());
            
            //setta lo chef
            Chef chef = this.chefRepository.findById(recipe.getId()).get();

            chef.getRecipes().add(recipe);
            recipe.setChef(chef);

            //this.chefRepository.save(chef);
           

            // Salva le modifiche nel repository
            recipeRepository.save(recipe);
        }
    }

	
}
