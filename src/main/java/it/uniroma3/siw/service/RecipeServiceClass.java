package it.uniroma3.siw.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.repository.RecipeRepository;

@Service
public class RecipeServiceClass {
	
	@Autowired
	RecipeRepository recipeRepository;
	
	public boolean hasReviewFromAuthor(Long recipeId, String username){
        Recipe recipe = this.recipeRepository.findById(recipeId).get();
        Set<Review> reviews = recipe.getReviews();
        for (Review review: reviews) {
            if(review.getAuthor().equals(username)) {
                return true;
            }
        }
        return false;
    }
	
	
	public void edit(Recipe recipe, Long id) {
		Recipe myRecipe = this.recipeRepository.findById(id).orElse(null);
		myRecipe.setName(recipe.getName());
		myRecipe.setDescription(recipe.getDescription());
		myRecipe.setDifficulty(recipe.getDifficulty());
		myRecipe.setImage(recipe.getImage());
		myRecipe.setIngredients(recipe.getIngredients());
		//myRecipe.setReviews(recipe.getReviews());
		myRecipe.setChef(recipe.getChef());
		
		this.recipeRepository.save(myRecipe);
	}

}
