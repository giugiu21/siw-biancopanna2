package it.uniroma3.siw.service;

import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Recipe;

import java.util.List;

@Service
public interface RecipeService {
	
	
	public List<Recipe> findTopN(int n);
	


}
