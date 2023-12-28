package it.uniroma3.siw.validators;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Recipe;


@Component
public class RecipeValidator implements Validator{
	

	
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
