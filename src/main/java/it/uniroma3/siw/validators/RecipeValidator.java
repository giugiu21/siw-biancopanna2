package it.uniroma3.siw.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.repository.RecipeRepository;

@Component
public class RecipeValidator implements Validator{
	
	@Autowired
    private RecipeRepository recipeRepository;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Recipe.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		 Recipe recipe = (Recipe) target;
		 if(recipe.getName() != null && recipe.getIngredients() != null){
		            errors.reject("recipe.duplicate");
		        }
		 
	}

}
