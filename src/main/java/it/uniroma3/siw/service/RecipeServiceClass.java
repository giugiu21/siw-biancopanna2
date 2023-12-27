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
	
	public boolean hasReviewFromAuthor(Long productId, String username){
        Recipe recipe = this.recipeRepository.findById(productId).get();
        Set<Review> reviews = recipe.getReviews();
        for (Review review: reviews) {
            if(review.getAuthor().equals(username)) {
                return true;
            }
        }
        return false;
    }

}
